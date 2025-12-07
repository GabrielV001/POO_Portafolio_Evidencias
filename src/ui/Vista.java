package ui;

import dl.entities.Jugador;
import dl.entities.Pareja;
import dl.entities.Partido;

import java.util.List;
import java.util.Scanner;

public class Vista {
    private Scanner scanner;

    public Vista() {
        this.scanner = new Scanner(System.in);
    }

    // Métodos de menú principal
    public int mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE GESTIÓN DE POZO DE PADEL ===");
        System.out.println("1. Gestionar Jugadores");
        System.out.println("2. Gestionar Parejas");
        System.out.println("3. Gestionar Partidos");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
        return leerEntero();
    }

    public int mostrarMenuJugadores() {
        System.out.println("\n=== GESTIÓN DE JUGADORES ===");
        System.out.println("1. Registrar nuevo jugador");
        System.out.println("2. Listar jugadores");
        System.out.println("3. Modificar jugador");
        System.out.println("4. Eliminar jugador");
        System.out.println("5. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        return leerEntero();
    }

    public int mostrarMenuParejas() {
        System.out.println("\n=== GESTIÓN DE PAREJAS ===");
        System.out.println("1. Formar nueva pareja");
        System.out.println("2. Listar parejas");
        System.out.println("3. Eliminar pareja");
        System.out.println("4. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        return leerEntero();
    }

    public int mostrarMenuPartidos() {
        System.out.println("\n=== GESTIÓN DE PARTIDOS ===");
        System.out.println("1. Crear nuevo partido");
        System.out.println("2. Listar partidos");
        System.out.println("3. Actualizar resultado");
        System.out.println("4. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        return leerEntero();
    }

    // Métodos para entrada de datos
    public String[] pedirDatosJugador() {
        String[] datos = new String[3];
        System.out.println("\n=== DATOS DEL JUGADOR ===");
        System.out.print("ID del jugador: ");
        datos[0] = scanner.nextLine();
        System.out.print("Nombre del jugador: ");
        datos[1] = scanner.nextLine();
        System.out.print("Nivel (Principiante/Intermedio/Avanzado): ");
        datos[2] = scanner.nextLine();
        return datos;
    }

    public String pedirId(String tipo) {
        System.out.print("\nIngrese el ID del " + tipo + ": ");
        return scanner.nextLine();
    }

    public String[] pedirDosJugadores() {
        String[] ids = new String[2];
        System.out.println("\n=== SELECCIÓN DE JUGADORES PARA PAREJA ===");
        System.out.print("ID del primer jugador: ");
        ids[0] = scanner.nextLine();
        System.out.print("ID del segundo jugador: ");
        ids[1] = scanner.nextLine();
        return ids;
    }

    public String[] pedirDosParejasParaPartido() {
        String[] ids = new String[2];
        System.out.println("\n=== SELECCIÓN DE PAREJAS PARA PARTIDO ===");
        System.out.print("ID de la primera pareja: ");
        ids[0] = scanner.nextLine();
        System.out.print("ID de la segunda pareja: ");
        ids[1] = scanner.nextLine();
        return ids;
    }

    public String pedirResultado() {
        System.out.println("\n=== INGRESO DE RESULTADO ===");
        System.out.print("Ingrese el resultado (ejemplo: '6-4, 7-5'): ");
        return scanner.nextLine();
    }

    // Métodos para mostrar datos
    public void mostrarJugadores(List<Jugador> jugadores) {
        System.out.println("\n=== LISTA DE JUGADORES ===");
        System.out.println("----------------------------------------");
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
        } else {
            for (Jugador jugador : jugadores) {
                System.out.println(jugador);
            }
        }
        System.out.println("----------------------------------------");
    }

    public void mostrarParejas(List<Pareja> parejas) {
        System.out.println("\n=== LISTA DE PAREJAS ===");
        System.out.println("----------------------------------------");
        if (parejas.isEmpty()) {
            System.out.println("No hay parejas formadas.");
        } else {
            for (Pareja pareja : parejas) {
                System.out.println(pareja);
            }
        }
        System.out.println("----------------------------------------");
    }

    public void mostrarPartidos(List<Partido> partidos) {
        System.out.println("\n=== LISTA DE PARTIDOS ===");
        System.out.println("----------------------------------------");
        if (partidos.isEmpty()) {
            System.out.println("No hay partidos registrados.");
        } else {
            for (Partido partido : partidos) {
                System.out.println(partido);
            }
        }
        System.out.println("----------------------------------------");
    }

    // Métodos para mensajes
    public void mostrarMensaje(String mensaje) {
        System.out.println("\n>>> " + mensaje);
    }

    public void mostrarError(String error) {
        System.out.println("\n¡ERROR! " + error);
    }

    // Métodos utilitarios
    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Método para confirmar acciones
    public boolean confirmarAccion(String accion) {
        System.out.print("\n¿Está seguro que desea " + accion + "? (S/N): ");
        String respuesta = scanner.nextLine();
        return respuesta.equalsIgnoreCase("S");
    }

    // Métodos para detalles específicos
    public void mostrarDetallesPartido(Partido partido) {
        System.out.println("\n=== DETALLES DEL PARTIDO ===");
        System.out.println("----------------------------------------");
        System.out.println("ID: " + partido.getId());
        System.out.println("Pareja 1: " + partido.getPareja1());
        System.out.println("Pareja 2: " + partido.getPareja2());
        System.out.println("Resultado: " + partido.getResultado());
        System.out.println("----------------------------------------");
    }

    public void mostrarEstadisticas(int totalJugadores, int totalParejas, int totalPartidos) {
        System.out.println("\n=== ESTADÍSTICAS DEL SISTEMA ===");
        System.out.println("----------------------------------------");
        System.out.println("Total de Jugadores: " + totalJugadores);
        System.out.println("Total de Parejas: " + totalParejas);
        System.out.println("Total de Partidos: " + totalPartidos);
        System.out.println("----------------------------------------");
    }

    // Método para cerrar el scanner
    public void cerrar() {
        if (scanner != null) {
            scanner.close();
        }
    }
}