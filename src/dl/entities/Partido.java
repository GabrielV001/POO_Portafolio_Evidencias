package dl.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import utils.Constants;

/**
 * Clase que representa un partido de padel entre dos parejas.
 */
public class Partido {
    private int id;
    private Pareja pareja1;
    private Pareja pareja2;
    private String resultado;
    private LocalDateTime fechaPartido;
    private LocalDateTime fechaRegistro;
    private String estado;
    private String observaciones;

    /**
     * Constructor básico para crear un nuevo partido
     */
    public Partido(Pareja pareja1, Pareja pareja2) {
        this.setPareja1(pareja1);
        this.setPareja2(pareja2);
        this.resultado = "Pendiente";
        this.fechaPartido = LocalDateTime.now().plusDays(1); // Por defecto, mañana
        this.fechaRegistro = LocalDateTime.now();
        this.estado = Constants.ESTADO_PENDIENTE;
        validarPartido();
    }

    /**
     * Constructor completo con todos los campos
     */
    public Partido(int id, Pareja pareja1, Pareja pareja2, String resultado,
                   LocalDateTime fechaPartido, LocalDateTime fechaRegistro,
                   String estado, String observaciones) {
        this.id = id;
        this.setPareja1(pareja1);
        this.setPareja2(pareja2);
        this.setResultado(resultado);
        this.setFechaPartido(fechaPartido);
        this.fechaRegistro = fechaRegistro;
        this.setEstado(estado);
        this.observaciones = observaciones;
        validarPartido();
    }

    // Getters y Setters con validaciones
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID no puede ser negativo");
        }
        this.id = id;
    }

    public Pareja getPareja1() {
        return pareja1;
    }

    public void setPareja1(Pareja pareja1) {
        if (pareja1 == null) {
            throw new IllegalArgumentException("La pareja 1 no puede ser nula");
        }
        if (!pareja1.estaActiva()) {
            throw new IllegalArgumentException("La pareja 1 no está activa");
        }
        this.pareja1 = pareja1;
    }

    public Pareja getPareja2() {
        return pareja2;
    }

    public void setPareja2(Pareja pareja2) {
        if (pareja2 == null) {
            throw new IllegalArgumentException("La pareja 2 no puede ser nula");
        }
        if (!pareja2.estaActiva()) {
            throw new IllegalArgumentException("La pareja 2 no está activa");
        }
        this.pareja2 = pareja2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        if (resultado == null || resultado.trim().isEmpty()) {
            throw new IllegalArgumentException("El resultado no puede estar vacío");
        }
        this.resultado = resultado.trim();
    }

    public LocalDateTime getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(LocalDateTime fechaPartido) {
        if (fechaPartido == null) {
            throw new IllegalArgumentException("La fecha del partido no puede ser nula");
        }
        if (fechaPartido.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha del partido no puede ser en el pasado");
        }
        this.fechaPartido = fechaPartido;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }
        estado = estado.trim();
        if (!estado.equals(Constants.ESTADO_PENDIENTE) &&
                !estado.equals(Constants.ESTADO_EN_CURSO) &&
                !estado.equals(Constants.ESTADO_FINALIZADO) &&
                !estado.equals(Constants.ESTADO_CANCELADO)) {
            throw new IllegalArgumentException("Estado inválido");
        }
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Valida que el partido cumpla con las reglas de negocio
     */
    private void validarPartido() {
        if (pareja1 == null || pareja2 == null) {
            throw new IllegalArgumentException("Ambas parejas son requeridas");
        }

        if (pareja1.equals(pareja2)) {
            throw new IllegalArgumentException("Las parejas deben ser diferentes");
        }

        if (!pareja1.estaActiva() || !pareja2.estaActiva()) {
            throw new IllegalArgumentException("Ambas parejas deben estar activas");
        }

        if (!pareja1.puedeJugarContra(pareja2)) {
            throw new IllegalArgumentException("Las parejas no son compatibles para jugar");
        }

        // Validar que no haya jugadores repetidos entre las parejas
        if (tieneJugadoresRepetidos()) {
            throw new IllegalArgumentException("No puede haber jugadores repetidos en el partido");
        }
    }

    /**
     * Verifica si hay jugadores repetidos entre las parejas
     */
    private boolean tieneJugadoresRepetidos() {
        return pareja1.incluyeJugador(pareja2.getJugador1()) ||
                pareja1.incluyeJugador(pareja2.getJugador2());
    }

    /**
     * Inicia el partido
     */
    public void iniciar() {
        if (!estado.equals(Constants.ESTADO_PENDIENTE)) {
            throw new IllegalStateException("El partido no está en estado pendiente");
        }
        if (fechaPartido.isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Aún no es tiempo de iniciar el partido");
        }
        this.estado = Constants.ESTADO_EN_CURSO;
    }

    /**
     * Finaliza el partido con un resultado
     */
    public void finalizar(String resultado) {
        if (!estado.equals(Constants.ESTADO_EN_CURSO)) {
            throw new IllegalStateException("El partido no está en curso");
        }
        this.resultado = resultado;
        this.estado = Constants.ESTADO_FINALIZADO;
    }

    /**
     * Cancela el partido
     */
    public void cancelar(String motivo) {
        if (estado.equals(Constants.ESTADO_FINALIZADO)) {
            throw new IllegalStateException("No se puede cancelar un partido finalizado");
        }
        this.estado = Constants.ESTADO_CANCELADO;
        this.observaciones = motivo;
    }

    /**
     * Verifica si el partido está pendiente
     */
    public boolean estaPendiente() {
        return estado.equals(Constants.ESTADO_PENDIENTE);
    }

    /**
     * Verifica si el partido está en curso
     */
    public boolean estaEnCurso() {
        return estado.equals(Constants.ESTADO_EN_CURSO);
    }

    /**
     * Verifica si el partido está finalizado
     */
    public boolean estaFinalizado() {
        return estado.equals(Constants.ESTADO_FINALIZADO);
    }

    /**
     * Verifica si el partido está cancelado
     */
    public boolean estaCancelado() {
        return estado.equals(Constants.ESTADO_CANCELADO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return id == partido.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Partido[ID: %d, Fecha: %s, %s vs %s, Resultado: %s, Estado: %s]",
                id,
                fechaPartido.format(java.time.format.DateTimeFormatter.ofPattern(Constants.Formato.FORMATO_FECHA_HORA)),
                pareja1.toString(),
                pareja2.toString(),
                resultado,
                estado);
    }

    /**
     * Crea una copia del partido
     */
    public Partido clone() {
        return new Partido(
                this.id,
                this.pareja1.clone(),
                this.pareja2.clone(),
                this.resultado,
                this.fechaPartido,
                this.fechaRegistro,
                this.estado,
                this.observaciones
        );
    }
}