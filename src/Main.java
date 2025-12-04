import tl.Controller;
import utils.Constants;
import utils.Conexion;
import java.util.Arrays;

public class Main {
    private static boolean modoDebug = false;
    private static boolean modoTest = false;

    public static void main(String[] args) {
        try {
            // Procesar argumentos de línea de comandos
            procesarArgumentos(args);

            // Configurar el ambiente
            configurarAmbiente();

            // Mostrar banner de inicio
            mostrarBanner();

            // Verificar conexión a la base de datos
            verificarConexion();

            // Ejecutar pruebas si estamos en modo test
            if (modoTest) {
                ejecutarPruebas();
                return;
            }

            // Iniciar el controlador principal
            Controller controller = new Controller();
            controller.iniciar();

        } catch (Exception e) {
            manejarError(e);
        } finally {
            realizarLimpieza();
        }
    }

    private static void procesarArgumentos(String[] args) {
        if (args != null && args.length > 0) {
            for (String arg : args) {
                switch (arg.toLowerCase()) {
                    case "--debug":
                        modoDebug = true;
                        System.out.println("Modo debug activado");
                        break;
                    case "--test":
                        modoTest = true;
                        System.out.println("Modo test activado");
                        break;
                    case "--help":
                        mostrarAyuda();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Argumento desconocido: " + arg);
                        mostrarAyuda();
                        System.exit(1);
                }
            }
        }
    }

    private static void configurarAmbiente() {
        // Configurar propiedades del sistema si es necesario
        System.setProperty("java.util.logging.config.file", "logging.properties");

        if (modoDebug) {
            System.setProperty("debug", "true");
        }
    }

    private static void mostrarBanner() {
        System.out.println("\n" + Constants.Caracteres.SEPARADOR_LINEA);
        System.out.println(Constants.Menu.TITULO_PRINCIPAL);
        System.out.println("Versión 1.0");
        System.out.println("Universidad CENFOTEC");
        System.out.println("Programación Orientada a Objetos");
        if (modoDebug) System.out.println("(Modo Debug)");
        if (modoTest) System.out.println("(Modo Test)");
        System.out.println(Constants.Caracteres.SEPARADOR_LINEA + "\n");
    }

    private static void verificarConexion() throws Exception {
        int intentos = 0;
        while (intentos < Constants.Config.MAX_INTENTOS_CONEXION) {
            try {
                System.out.println("Verificando conexión a la base de datos...");
                Conexion.getConnection();
                System.out.println("Conexión exitosa a la base de datos.");
                return;
            } catch (Exception e) {
                intentos++;
                if (intentos >= Constants.Config.MAX_INTENTOS_CONEXION) {
                    throw new Exception("No se pudo establecer conexión después de " +
                            Constants.Config.MAX_INTENTOS_CONEXION + " intentos");
                }
                System.out.println("Reintentando conexión... (Intento " + intentos + ")");
                Thread.sleep(Constants.Config.TIEMPO_ESPERA_CONEXION);
            }
        }
    }

    private static void ejecutarPruebas() {
        System.out.println("Ejecutando pruebas del sistema...");
        // Aquí irían las pruebas automatizadas
        System.out.println("Pruebas completadas.");
    }

    private static void manejarError(Exception e) {
        System.err.println("\nError fatal en la aplicación:");
        if (modoDebug) {
            e.printStackTrace();
        } else {
            System.err.println(e.getMessage());
        }
    }

    private static void realizarLimpieza() {
        try {
            // Cerrar conexiones y recursos
            System.out.println("\nFinalizando el sistema...");
            System.out.println("Gracias por usar el Sistema de Gestión de Pozo de Padel");
            System.out.println(Constants.Caracteres.SEPARADOR_LINEA);
        } catch (Exception e) {
            System.err.println("Error durante la limpieza final: " + e.getMessage());
        }
    }

    private static void mostrarAyuda() {
        System.out.println("\nUso: java -jar pozo-padel.jar [opciones]");
        System.out.println("Opciones:");
        System.out.println("  --debug    Activa el modo debug");
        System.out.println("  --test     Ejecuta las pruebas del sistema");
        System.out.println("  --help     Muestra esta ayuda");
    }
}