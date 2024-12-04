package src;

public class Jugador extends Thread {
    private final int idJugador;
    private final Arbitro arbitro;

    public Jugador(int idJugador, Arbitro arbitro) {
        this.idJugador = idJugador;
        this.arbitro = arbitro;
    }

    @Override
    public void run() {
        while (!arbitro.isJuegoTerminado()) {
            synchronized (arbitro) {
                if (arbitro.getTurno() == idJugador) {
                    int numeroJugado = 1 + (int) (10 * Math.random());
                    arbitro.realizarJugada(idJugador, numeroJugado);
                } else {
                    try {
                        arbitro.wait(); // Espera su turno
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Jugador " + idJugador + " interrumpido.");
                    }
                }
            }
            // Breve pausa para evitar consumo innecesario de CPU
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
