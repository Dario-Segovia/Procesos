package src;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    // Definimos el número de hilos que vamos a sincronizar
    private static final int NUM_THREADS = 30;

    public static void main(String[] args) {
        // Creamos la barrera, con una acción opcional que se ejecutará al completarse
        CyclicBarrier barrera = new CyclicBarrier(NUM_THREADS, () -> {
            System.out.println("¡Todos los hilos han alcanzado la barrera y ahora continúan!");
        });

        // Lanzamos los hilos
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread hilo = new Thread(new HiloConTareas(i + 1, barrera));
            hilo.start();
        }
    }

    static class HiloConTareas implements Runnable {
        private final int id;
        private final CyclicBarrier barrera;

        public HiloConTareas(int id, CyclicBarrier barrera) {
            this.id = id;
            this.barrera = barrera;
        }

        @Override
        public void run() {
            try {
                // Generamos un número aleatorio de subtareas (entre 5 y 20)
                int numSubtareas = ThreadLocalRandom.current().nextInt(5, 21);

                for (int i = 1; i <= numSubtareas; i++) {
                    // Tiempo aleatorio de cada subtarea (entre 500 y 2500 ms)
                    int tiempoTarea = ThreadLocalRandom.current().nextInt(500, 2501);
                    System.out.printf("Hilo %d está realizando la subtarea %d/%d (tiempo: %d ms)%n", 
                            id, i, numSubtareas, tiempoTarea);
                    Thread.sleep(tiempoTarea); // Simula el trabajo de la subtarea
                }

                System.out.printf("Hilo %d ha terminado sus subtareas y espera en la barrera.%n", id);
                barrera.await(); // Espera en la barrera hasta que todos los hilos lleguen

                System.out.printf("Hilo %d continúa después de la barrera.%n", id);
            } catch (Exception e) {
                System.err.printf("Hilo %d encontró un problema: %s%n", id, e.getMessage());
            }
        }
    }
}
