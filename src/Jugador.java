public class Jugador {
    private String id;
    private String nombre;
    private String nivel;

    public Jugador(String id, String nombre, String nivel) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre + " (ID: " + id + ", Nivel: " + nivel + ")";
    }
}