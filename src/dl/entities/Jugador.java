package dl.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import utils.Constants;

/**
 * Clase que representa a un jugador en el sistema de pozo de padel.
 */
public class Jugador {
    private String id;
    private String nombre;
    private String nivel;
    private LocalDateTime fechaRegistro;
    private boolean estado;

    /**
     * Constructor completo de Jugador
     */
    public Jugador(String id, String nombre, String nivel) {
        this.setId(id);
        this.setNombre(nombre);
        this.setNivel(nivel);
        this.fechaRegistro = LocalDateTime.now();
        this.estado = true;
    }

    /**
     * Constructor completo con todos los campos
     */
    public Jugador(String id, String nombre, String nivel, LocalDateTime fechaRegistro, boolean estado) {
        this.setId(id);
        this.setNombre(nombre);
        this.setNivel(nivel);
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    // Getters y Setters con validaciones
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede estar vacío");
        }
        if (id.length() < Constants.Validacion.MIN_LONGITUD_ID ||
                id.length() > Constants.Validacion.MAX_LONGITUD_ID) {
            throw new IllegalArgumentException(
                    "El ID debe tener entre " + Constants.Validacion.MIN_LONGITUD_ID +
                            " y " + Constants.Validacion.MAX_LONGITUD_ID + " caracteres"
            );
        }
        if (!id.matches(Constants.Validacion.PATRON_ID)) {
            throw new IllegalArgumentException(
                    "El ID solo puede contener letras mayúsculas y números"
            );
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() < Constants.Validacion.MIN_LONGITUD_NOMBRE ||
                nombre.length() > Constants.Validacion.MAX_LONGITUD_NOMBRE) {
            throw new IllegalArgumentException(
                    "El nombre debe tener entre " + Constants.Validacion.MIN_LONGITUD_NOMBRE +
                            " y " + Constants.Validacion.MAX_LONGITUD_NOMBRE + " caracteres"
            );
        }
        if (!nombre.matches(Constants.Validacion.PATRON_NOMBRE)) {
            throw new IllegalArgumentException(
                    "El nombre solo puede contener letras y espacios"
            );
        }
        this.nombre = nombre.trim();
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        if (nivel == null || nivel.trim().isEmpty()) {
            throw new IllegalArgumentException("El nivel no puede estar vacío");
        }
        nivel = nivel.trim();
        if (!nivel.equals(Constants.NIVEL_PRINCIPIANTE) &&
                !nivel.equals(Constants.NIVEL_INTERMEDIO) &&
                !nivel.equals(Constants.NIVEL_AVANZADO)) {
            throw new IllegalArgumentException(
                    "Nivel inválido. Debe ser: Principiante, Intermedio o Avanzado"
            );
        }
        this.nivel = nivel;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        if (fechaRegistro == null) {
            throw new IllegalArgumentException("La fecha de registro no puede ser nula");
        }
        if (fechaRegistro.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de registro no puede ser futura");
        }
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Verifica si el jugador está activo en el sistema
     */
    public boolean estaActivo() {
        return this.estado;
    }

    /**
     * Desactiva al jugador en el sistema
     */
    public void desactivar() {
        this.estado = false;
    }

    /**
     * Reactiva al jugador en el sistema
     */
    public void reactivar() {
        this.estado = true;
    }

    /**
     * Verifica si el jugador puede formar pareja según su nivel
     */
    public boolean puedeFormarParejaCon(Jugador otroJugador) {
        if (otroJugador == null) {
            return false;
        }
        // Lógica para determinar si pueden formar pareja según niveles
        if (this.nivel.equals(otroJugador.getNivel())) {
            return true;
        }
        if (this.nivel.equals(Constants.NIVEL_INTERMEDIO) &&
                (otroJugador.getNivel().equals(Constants.NIVEL_PRINCIPIANTE) ||
                        otroJugador.getNivel().equals(Constants.NIVEL_AVANZADO))) {
            return true;
        }
        if (otroJugador.getNivel().equals(Constants.NIVEL_INTERMEDIO) &&
                (this.nivel.equals(Constants.NIVEL_PRINCIPIANTE) ||
                        this.nivel.equals(Constants.NIVEL_AVANZADO))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Jugador[ID: %s, Nombre: %s, Nivel: %s, Estado: %s]",
                id, nombre, nivel, estado ? "Activo" : "Inactivo");
    }

    /**
     * Crea una copia del jugador
     */
    public Jugador clone() {
        return new Jugador(
                this.id,
                this.nombre,
                this.nivel,
                this.fechaRegistro,
                this.estado
        );
    }
}