package src;

import java.util.concurrent.atomic.AtomicBoolean;

public class Arbitro {
    private final int numJugadores;
    private int turno;
    private final int numeroSecreto;
    private final AtomicBoolean juegoTerminado = new AtomicBoolean(false);

    public Arbitro(int numJugadores) {
        this.numJugadores = numJugadores;
        this.turno = 1; // Comienza con el jugador 1
        this.numeroSecreto = 1 + (int) (10 * Math.random());
        System.out.println("Número secreto generado: " + numeroSecreto);
    }

    public synchronized int getTurno() {
        return turno;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado.get();
    }

    public synchronized void realizarJugada(int idJugador, int numeroJugado) {
        if (juegoTerminado.get()) return;

        if (idJugador == turno) {
            System.out.println("Jugador " + idJugador + " juega con el número: " + numeroJugado);

            if (numeroJugado == numeroSecreto) {
                System.out.println("¡Jugador " + idJugador + " ha ganado! Adivinó el número secreto: " + numeroSecreto);
                juegoTerminado.set(true);
            } else {
                System.out.println("Jugador " + idJugador + " no acertó. Siguiente turno.");
                turno = (turno % numJugadores) + 1; // Ciclo entre los jugadores
            }
            notifyAll(); // Notifica a otros jugadores
        } else {
            System.out.println("No es el turno del jugador " + idJugador + ". Espera.");
        }
    }
}
