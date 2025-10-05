public class Pareja {
    private Jugador jugador1;
    private Jugador jugador2;

    public Pareja(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    @Override
    public String toString() {
        return "Pareja: " + jugador1.getNombre() + " (ID: " + jugador1.getId() + ") - "
                + jugador2.getNombre() + " (ID: " + jugador2.getId() + ")";
    }
}