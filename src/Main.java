public class Main {
    public static void main(String[] args) {
        // Crear un nuevo pozo
        Pozo pozo = new Pozo("Pozo Dominguero");

        // Crear algunos jugadores
        Jugador jugador1 = new Jugador("J001", "Juan", "Intermedio");
        Jugador jugador2 = new Jugador("J002", "Pedro", "Avanzado");
        Jugador jugador3 = new Jugador("J003", "Ana", "Intermedio");
        Jugador jugador4 = new Jugador("J004", "María", "Principiante");

        // Agregar jugadores al pozo
        pozo.agregarJugador(jugador1);
        pozo.agregarJugador(jugador2);
        pozo.agregarJugador(jugador3);
        pozo.agregarJugador(jugador4);

        // Formar parejas
        pozo.formarParejas();
        pozo.mostrarParejas();

        // Crear y mostrar partidos
        pozo.crearPartidos();
        pozo.mostrarPartidos();
    }
}