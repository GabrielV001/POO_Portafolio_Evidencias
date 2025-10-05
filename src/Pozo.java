import java.util.ArrayList;
import java.util.List;

public class Pozo {
    private String nombre;
    private List<Jugador> jugadores;
    private List<Pareja> parejas;
    private List<Partido> partidos;

    public Pozo(String nombre) {
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();
        this.parejas = new ArrayList<>();
        this.partidos = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void formarParejas() {
        parejas.clear();
        for (int i = 0; i < jugadores.size() - 1; i += 2) {
            Pareja pareja = new Pareja(jugadores.get(i), jugadores.get(i + 1));
            parejas.add(pareja);
        }
    }

    // crear partidos
    public void crearPartidos() {
        partidos.clear();
        for (int i = 0; i < parejas.size() - 1; i += 2) {
            Partido partido = new Partido(parejas.get(i), parejas.get(i + 1));
            partidos.add(partido);
        }
    }

    // mostrar partidos
    public void mostrarPartidos() {
        System.out.println("\nPartidos del pozo " + nombre);
        System.out.println("----------------------------------------");
        for (Partido partido : partidos) {
            System.out.println(partido);
        }
        System.out.println("----------------------------------------");
    }

    public void mostrarParejas() {
        System.out.println("\nParejas formadas para el pozo " + nombre);
        System.out.println("----------------------------------------");
        for (Pareja pareja : parejas) {
            System.out.println(pareja);
        }
        System.out.println("----------------------------------------");
    }
}