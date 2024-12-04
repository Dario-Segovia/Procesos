package src;

public class Main {
    public static void main(String[] args) {
        int numJugadores = 3; // Número de jugadores
        Arbitro arbitro = new Arbitro(numJugadores);

        // Crear y lanzar los jugadores
        Thread[] jugadores = new Thread[numJugadores];
        for (int i = 0; i < numJugadores; i++) {
            jugadores[i] = new Jugador(i + 1, arbitro);
            jugadores[i].start();
        }

        // Esperar a que los jugadores terminen
        for (Thread jugador : jugadores) {
            try {
                jugador.join();
            } catch (InterruptedException e) {
                System.out.println("Hilo principal interrumpido.");
            }
        }

        System.out.println("El juego ha terminado.");
    }
}
