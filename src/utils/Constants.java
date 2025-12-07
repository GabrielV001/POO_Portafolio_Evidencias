package utils;

public class Constants {
    // Configuración de base de datos
    public static final String DB_URL = "jdbc:mysql://localhost:3306/pozo_tenis";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "[PASSWORD]";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Niveles de jugadores
    public static final String NIVEL_PRINCIPIANTE = "Principiante";
    public static final String NIVEL_INTERMEDIO = "Intermedio";
    public static final String NIVEL_AVANZADO = "Avanzado";

    // Estados de partidos
    public static final String ESTADO_PENDIENTE = "Pendiente";
    public static final String ESTADO_EN_CURSO = "En Curso";
    public static final String ESTADO_FINALIZADO = "Finalizado";
    public static final String ESTADO_CANCELADO = "Cancelado";

    // Mensajes de error
    public static final String ERROR_CONEXION_BD = "Error al conectar con la base de datos";
    public static final String ERROR_CONSULTA_BD = "Error al ejecutar la consulta en la base de datos";
    public static final String ERROR_DATOS_INVALIDOS = "Los datos ingresados no son válidos";
    public static final String ERROR_REGISTRO_NO_ENCONTRADO = "Registro no encontrado";
    public static final String ERROR_REGISTRO_DUPLICADO = "El registro ya existe";
    public static final String ERROR_OPERACION_NO_PERMITIDA = "Operación no permitida";

    // Mensajes de éxito
    public static final String EXITO_REGISTRO = "Registro creado exitosamente";
    public static final String EXITO_ACTUALIZACION = "Registro actualizado exitosamente";
    public static final String EXITO_ELIMINACION = "Registro eliminado exitosamente";

    // Consultas SQL
    public static final class SQL {
        // Consultas para Jugadores
        public static final String INSERT_JUGADOR =
                "INSERT INTO jugadores (id, nombre, nivel) VALUES (?, ?, ?)";
        public static final String UPDATE_JUGADOR =
                "UPDATE jugadores SET nombre = ?, nivel = ? WHERE id = ?";
        public static final String DELETE_JUGADOR =
                "DELETE FROM jugadores WHERE id = ?";
        public static final String SELECT_JUGADOR =
                "SELECT * FROM jugadores WHERE id = ?";
        public static final String SELECT_ALL_JUGADORES =
                "SELECT * FROM jugadores";

        // Consultas para Parejas
        public static final String INSERT_PAREJA =
                "INSERT INTO parejas (jugador1_id, jugador2_id) VALUES (?, ?)";
        public static final String UPDATE_PAREJA =
                "UPDATE parejas SET jugador1_id = ?, jugador2_id = ? WHERE id = ?";
        public static final String DELETE_PAREJA =
                "DELETE FROM parejas WHERE id = ?";
        public static final String SELECT_PAREJA =
                "SELECT * FROM parejas WHERE id = ?";
        public static final String SELECT_ALL_PAREJAS =
                "SELECT * FROM parejas";
        public static final String SELECT_PAREJAS_POR_JUGADOR =
                "SELECT * FROM parejas WHERE jugador1_id = ? OR jugador2_id = ?";

        // Consultas para Partidos
        public static final String INSERT_PARTIDO =
                "INSERT INTO partidos (pareja1_id, pareja2_id, resultado) VALUES (?, ?, ?)";
        public static final String UPDATE_PARTIDO =
                "UPDATE partidos SET pareja1_id = ?, pareja2_id = ?, resultado = ? WHERE id = ?";
        public static final String UPDATE_RESULTADO_PARTIDO =
                "UPDATE partidos SET resultado = ? WHERE id = ?";
        public static final String DELETE_PARTIDO =
                "DELETE FROM partidos WHERE id = ?";
        public static final String SELECT_PARTIDO =
                "SELECT * FROM partidos WHERE id = ?";
        public static final String SELECT_ALL_PARTIDOS =
                "SELECT * FROM partidos";
        public static final String SELECT_PARTIDOS_PENDIENTES =
                "SELECT * FROM partidos WHERE resultado = 'Pendiente'";
        public static final String SELECT_PARTIDOS_POR_PAREJA =
                "SELECT * FROM partidos WHERE pareja1_id = ? OR pareja2_id = ?";
    }

    // Validaciones
    public static final class Validacion {
        public static final int MIN_LONGITUD_NOMBRE = 3;
        public static final int MAX_LONGITUD_NOMBRE = 50;
        public static final int MIN_LONGITUD_ID = 4;
        public static final int MAX_LONGITUD_ID = 10;
        public static final String PATRON_ID = "^[A-Z0-9]+$";
        public static final String PATRON_NOMBRE = "^[a-zA-Z\\s]+$";
    }

    // Configuración de la aplicación
    public static final class Config {
        public static final int MAX_INTENTOS_CONEXION = 3;
        public static final int TIEMPO_ESPERA_CONEXION = 5000; // milisegundos
        public static final int MAX_PAREJAS_POR_JUGADOR = 2;
        public static final int MAX_PARTIDOS_POR_DIA = 5;
    }

    // Formatos de fecha y hora
    public static final class Formato {
        public static final String FORMATO_FECHA = "yyyy-MM-dd";
        public static final String FORMATO_HORA = "HH:mm:ss";
        public static final String FORMATO_FECHA_HORA = "yyyy-MM-dd HH:mm:ss";
    }

    // Títulos de menús
    public static final class Menu {
        public static final String TITULO_PRINCIPAL = "=== SISTEMA DE GESTIÓN DE POZO DE TENIS ===";
        public static final String TITULO_JUGADORES = "=== GESTIÓN DE JUGADORES ===";
        public static final String TITULO_PAREJAS = "=== GESTIÓN DE PAREJAS ===";
        public static final String TITULO_PARTIDOS = "=== GESTIÓN DE PARTIDOS ===";
        public static final String TITULO_RESULTADOS = "=== GESTIÓN DE RESULTADOS ===";
    }

    // Caracteres especiales y formateo
    public static final class Caracteres {
        public static final String SEPARADOR_LINEA = "----------------------------------------";
        public static final String VIÑETA = "• ";
        public static final String FLECHA = "→ ";
        public static final String MARCA_SELECCION = "✓ ";
        public static final String MARCA_ERROR = "✗ ";
    }
}
