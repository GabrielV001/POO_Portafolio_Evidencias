package tl;

import bl.JugadorBL;
import bl.ParejaBL;
import bl.PartidoBL;
import dl.entities.Jugador;
import dl.entities.Pareja;
import dl.entities.Partido;
import ui.Vista;

import java.util.List;
import java.util.ArrayList;

public class Controller {
    private JugadorBL jugadorBL;
    private ParejaBL parejaBL;
    private PartidoBL partidoBL;
    private Vista vista;

    public Controller() {
        this.jugadorBL = new JugadorBL();
        this.parejaBL = new ParejaBL();
        this.partidoBL = new PartidoBL();
        this.vista = new Vista();
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            try {
                int opcion = vista.mostrarMenuPrincipal();
                switch (opcion) {
                    case 1:
                        gestionarJugadores();
                        break;
                    case 2:
                        gestionarParejas();
                        break;
                    case 3:
                        gestionarPartidos();
                        break;
                    case 4:
                        continuar = false;
                        vista.mostrarMensaje("¡Gracias por usar el sistema!");
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }
            } catch (Exception e) {
                vista.mostrarError("Error: " + e.getMessage());
            }
        }
    }

    private void gestionarJugadores() {
        try {
            boolean continuar = true;
            while (continuar) {
                int opcion = vista.mostrarMenuJugadores();
                switch (opcion) {
                    case 1:
                        registrarJugador();
                        break;
                    case 2:
                        listarJugadores();
                        break;
                    case 3:
                        modificarJugador();
                        break;
                    case 4:
                        eliminarJugador();
                        break;
                    case 5:
                        continuar = false;
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }
            }
        } catch (Exception e) {
            vista.mostrarError("Error en gestión de jugadores: " + e.getMessage());
        }
    }

    private void registrarJugador() {
        try {
            String[] datosJugador = vista.pedirDatosJugador();
            Jugador jugador = new Jugador(datosJugador[0], datosJugador[1], datosJugador[2]);
            jugadorBL.registrarJugador(jugador);
            vista.mostrarMensaje("Jugador registrado exitosamente");
        } catch (Exception e) {
            vista.mostrarError("Error al registrar jugador: " + e.getMessage());
        }
    }

    private void listarJugadores() {
        try {
            List<Jugador> jugadores = jugadorBL.listarJugadores();
            vista.mostrarJugadores(jugadores);
        } catch (Exception e) {
            vista.mostrarError("Error al listar jugadores: " + e.getMessage());
        }
    }

    private void modificarJugador() {
        try {
            listarJugadores();
            String id = vista.pedirId("jugador");
            Jugador jugador = jugadorBL.buscarJugador(id);
            if (jugador != null) {
                String[] datosNuevos = vista.pedirDatosJugador();
                jugador.setNombre(datosNuevos[1]);
                jugador.setNivel(datosNuevos[2]);
                jugadorBL.actualizarJugador(jugador);
                vista.mostrarMensaje("Jugador actualizado exitosamente");
            } else {
                vista.mostrarError("Jugador no encontrado");
            }
        } catch (Exception e) {
            vista.mostrarError("Error al modificar jugador: " + e.getMessage());
        }
    }

    private void eliminarJugador() {
        try {
            listarJugadores();
            String id = vista.pedirId("jugador");
            jugadorBL.eliminarJugador(id);
            vista.mostrarMensaje("Jugador eliminado exitosamente");
        } catch (Exception e) {
            vista.mostrarError("Error al eliminar jugador: " + e.getMessage());
        }
    }

    private void gestionarParejas() {
        try {
            boolean continuar = true;
            while (continuar) {
                int opcion = vista.mostrarMenuParejas();
                switch (opcion) {
                    case 1:
                        formarPareja();
                        break;
                    case 2:
                        listarParejas();
                        break;
                    case 3:
                        eliminarPareja();
                        break;
                    case 4:
                        continuar = false;
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }
            }
        } catch (Exception e) {
            vista.mostrarError("Error en gestión de parejas: " + e.getMessage());
        }
    }

    private void formarPareja() {
        try {
            listarJugadores();
            String[] ids = vista.pedirDosJugadores();
            Jugador jugador1 = jugadorBL.buscarJugador(ids[0]);
            Jugador jugador2 = jugadorBL.buscarJugador(ids[1]);

            if (jugador1 != null && jugador2 != null) {
                Pareja pareja = new Pareja(jugador1, jugador2);
                parejaBL.registrarPareja(pareja);
                vista.mostrarMensaje("Pareja formada exitosamente");
            } else {
                vista.mostrarError("Uno o ambos jugadores no encontrados");
            }
        } catch (Exception e) {
            vista.mostrarError("Error al formar pareja: " + e.getMessage());
        }
    }

    private void listarParejas() {
        try {
            List<Pareja> parejas = parejaBL.listarParejas();
            vista.mostrarParejas(parejas);
        } catch (Exception e) {
            vista.mostrarError("Error al listar parejas: " + e.getMessage());
        }
    }

    private void eliminarPareja() {
        try {
            listarParejas();
            String id = vista.pedirId("pareja");
            parejaBL.eliminarPareja(Integer.parseInt(id));
            vista.mostrarMensaje("Pareja eliminada exitosamente");
        } catch (Exception e) {
            vista.mostrarError("Error al eliminar pareja: " + e.getMessage());
        }
    }

    private void gestionarPartidos() {
        try {
            boolean continuar = true;
            while (continuar) {
                int opcion = vista.mostrarMenuPartidos();
                switch (opcion) {
                    case 1:
                        crearPartido();
                        break;
                    case 2:
                        listarPartidos();
                        break;
                    case 3:
                        actualizarResultado();
                        break;
                    case 4:
                        continuar = false;
                        break;
                    default:
                        vista.mostrarError("Opción no válida");
                }
            }
        } catch (Exception e) {
            vista.mostrarError("Error en gestión de partidos: " + e.getMessage());
        }
    }

    private void crearPartido() {
        try {
            listarParejas();
            String[] ids = vista.pedirDosParejasParaPartido();
            Pareja pareja1 = parejaBL.obtenerPareja(ids[0]);
            Pareja pareja2 = parejaBL.obtenerPareja(ids[1]);

            if (pareja1 != null && pareja2 != null) {
                Partido partido = new Partido(pareja1, pareja2);
                partidoBL.registrarPartido(partido);
                vista.mostrarMensaje("Partido creado exitosamente");
            } else {
                vista.mostrarError("Una o ambas parejas no encontradas");
            }
        } catch (Exception e) {
            vista.mostrarError("Error al crear partido: " + e.getMessage());
        }
    }

    private void listarPartidos() {
        try {
            List<Partido> partidos = partidoBL.listarPartidos();
            vista.mostrarPartidos(partidos);
        } catch (Exception e) {
            vista.mostrarError("Error al listar partidos: " + e.getMessage());
        }
    }

    private void actualizarResultado() {
        try {
            listarPartidos();
            String id = vista.pedirId("partido");
            String resultado = vista.pedirResultado();
            Partido partido = partidoBL.obtenerPartido(id);
            if (partido != null) {
                partido.setResultado(resultado);
                partidoBL.actualizarPartido(partido);
                vista.mostrarMensaje("Resultado actualizado exitosamente");
            } else {
                vista.mostrarError("Partido no encontrado");
            }
        } catch (Exception e) {
            vista.mostrarError("Error al actualizar resultado: " + e.getMessage());
        }
    }
}