package dl.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import utils.Constants;

    /**
     * Clase que representa una pareja de jugadores en el sistema de pozo de padel.
     */
    public class Pareja {
        private int id;
        private Jugador jugador1;
        private Jugador jugador2;
        private LocalDateTime fechaFormacion;
        private boolean estado;

        /**
         * Constructor básico para crear una nueva pareja
         */
        public Pareja(Jugador jugador1, Jugador jugador2) {
            this.setJugador1(jugador1);
            this.setJugador2(jugador2);
            this.fechaFormacion = LocalDateTime.now();
            this.estado = true;
            validarPareja();
        }

        /**
         * Constructor completo con todos los campos
         */
        public Pareja(int id, Jugador jugador1, Jugador jugador2, LocalDateTime fechaFormacion, boolean estado) {
            this.id = id;
            this.setJugador1(jugador1);
            this.setJugador2(jugador2);
            this.fechaFormacion = fechaFormacion;
            this.estado = estado;
            validarPareja();
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

        public Jugador getJugador1() {
            return jugador1;
        }

        public void setJugador1(Jugador jugador1) {
            if (jugador1 == null) {
                throw new IllegalArgumentException("El jugador 1 no puede ser nulo");
            }
            if (!jugador1.estaActivo()) {
                throw new IllegalArgumentException("El jugador 1 no está activo");
            }
            this.jugador1 = jugador1;
        }

        public Jugador getJugador2() {
            return jugador2;
        }

        public void setJugador2(Jugador jugador2) {
            if (jugador2 == null) {
                throw new IllegalArgumentException("El jugador 2 no puede ser nulo");
            }
            if (!jugador2.estaActivo()) {
                throw new IllegalArgumentException("El jugador 2 no está activo");
            }
            this.jugador2 = jugador2;
        }

        public LocalDateTime getFechaFormacion() {
            return fechaFormacion;
        }

        public void setFechaFormacion(LocalDateTime fechaFormacion) {
            if (fechaFormacion == null) {
                throw new IllegalArgumentException("La fecha de formación no puede ser nula");
            }
            if (fechaFormacion.isAfter(LocalDateTime.now())) {
                throw new IllegalArgumentException("La fecha de formación no puede ser futura");
            }
            this.fechaFormacion = fechaFormacion;
        }

        public boolean isEstado() {
            return estado;
        }

        public void setEstado(boolean estado) {
            this.estado = estado;
        }

        /**
         * Valida que la pareja cumpla con las reglas de negocio
         */
        private void validarPareja() {
            if (jugador1 == null || jugador2 == null) {
                throw new IllegalArgumentException("Ambos jugadores son requeridos");
            }

            if (jugador1.equals(jugador2)) {
                throw new IllegalArgumentException("Los jugadores deben ser diferentes");
            }

            if (!jugador1.estaActivo() || !jugador2.estaActivo()) {
                throw new IllegalArgumentException("Ambos jugadores deben estar activos");
            }

            if (!jugador1.puedeFormarParejaCon(jugador2)) {
                throw new IllegalArgumentException(
                        "Los jugadores no son compatibles por su nivel para formar pareja"
                );
            }
        }

        /**
         * Verifica si la pareja está activa
         */
        public boolean estaActiva() {
            return this.estado && jugador1.estaActivo() && jugador2.estaActivo();
        }

        /**
         * Desactiva la pareja
         */
        public void desactivar() {
            this.estado = false;
        }

        /**
         * Reactiva la pareja si ambos jugadores están activos
         */
        public boolean reactivar() {
            if (jugador1.estaActivo() && jugador2.estaActivo()) {
                this.estado = true;
                return true;
            }
            return false;
        }

        /**
         * Calcula el nivel promedio de la pareja
         */
        public String getNivelPromedio() {
            if (jugador1.getNivel().equals(jugador2.getNivel())) {
                return jugador1.getNivel();
            }
            return Constants.NIVEL_INTERMEDIO;
        }

        /**
         * Verifica si la pareja puede jugar contra otra pareja
         */
        public boolean puedeJugarContra(Pareja otraPareja) {
            if (otraPareja == null || !otraPareja.estaActiva()) {
                return false;
            }

            // Evitar que una pareja juegue contra sí misma
            if (this.equals(otraPareja)) {
                return false;
            }

            String nivelThis = this.getNivelPromedio();
            String nivelOtra = otraPareja.getNivelPromedio();

            // Parejas del mismo nivel siempre pueden jugar
            if (nivelThis.equals(nivelOtra)) {
                return true;
            }

            // Si una pareja es de nivel intermedio, puede jugar con cualquier otra
            return nivelThis.equals(Constants.NIVEL_INTERMEDIO) ||
                    nivelOtra.equals(Constants.NIVEL_INTERMEDIO);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pareja pareja = (Pareja) o;
            return id == pareja.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return String.format("Pareja[ID: %d, %s - %s, Nivel: %s, Estado: %s]",
                    id,
                    jugador1.getNombre(),
                    jugador2.getNombre(),
                    getNivelPromedio(),
                    estado ? "Activa" : "Inactiva");
        }

        /**
         * Crea una copia de la pareja
         */
        public Pareja clone() {
            return new Pareja(
                    this.id,
                    this.jugador1.clone(),
                    this.jugador2.clone(),
                    this.fechaFormacion,
                    this.estado
            );
        }

        /**
         * Verifica si un jugador es parte de la pareja
         */
        public boolean incluyeJugador(Jugador jugador) {
            if (jugador == null) return false;
            return jugador.getId().equals(jugador1.getId()) ||
                    jugador.getId().equals(jugador2.getId());
        }

        /**
         * Obtiene el compañero de un jugador en la pareja
         */
        public Jugador obtenerCompañero(Jugador jugador) {
            if (jugador == null) return null;
            if (jugador.getId().equals(jugador1.getId())) return jugador2;
            if (jugador.getId().equals(jugador2.getId())) return jugador1;
            return null;
        }
    }