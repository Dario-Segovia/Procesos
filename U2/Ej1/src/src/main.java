package src;

import java.util.Random;

public class main {
    private static final int META = 70;

    public static void main(String[] args) {
        System.out.println("¡Comienza la carrera entre la liebre y la tortuga!");
        Thread liebre = new Thread(new Liebre());
        Thread tortuga = new Thread(new Tortuga());
        liebre.start();
        tortuga.start();
    }

    static class Tortuga implements Runnable {
        private int posicion = 1;

        @Override
        public void run() {
            Random random = new Random();
            while (posicion < META) {
                int probabilidad = random.nextInt(100) + 1;
                if (probabilidad <= 50) {
                    // Avance rápido
                    posicion += 3;
                } else if (probabilidad <= 70) {
                    // Resbaló
                    posicion -= 6;
                } else {
                    // Avance lento
                    posicion += 1;
                }

                if (posicion < 1) posicion = 1; // No puede ir por debajo de 1
                imprimirPosicion("T", posicion);

                if (posicion >= META) {
                    System.out.println("¡La tortuga ha ganado la carrera!");
                    System.exit(0);
                }

                esperarUnSegundo();
            }
        }
    }

    static class Liebre implements Runnable {
        private int posicion = 1;

        @Override
        public void run() {
            Random random = new Random();
            while (posicion < META) {
                int probabilidad = random.nextInt(100) + 1;
                if (probabilidad <= 20) {
                    // Duerme
                    // No cambia de posición
                } else if (probabilidad <= 40) {
                    // Gran salto
                    posicion += 9;
                } else if (probabilidad <= 50) {
                    // Resbalón grande
                    posicion -= 12;
                } else if (probabilidad <= 80) {
                    // Pequeño salto
                    posicion += 1;
                } else {
                    // Resbalón pequeño
                    posicion -= 2;
                }

                if (posicion < 1) posicion = 1; // No puede ir por debajo de 1
                imprimirPosicion("L", posicion);

                if (posicion >= META) {
                    System.out.println("¡La liebre ha ganado la carrera!");
                    System.exit(0);
                }

                esperarUnSegundo();
            }
        }
    }

    private static synchronized void imprimirPosicion(String animal, int posicion) {
        for (int i = 1; i < posicion; i++) {
            System.out.print(" ");
        }
        System.out.println(animal);
    }

    private static void esperarUnSegundo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
