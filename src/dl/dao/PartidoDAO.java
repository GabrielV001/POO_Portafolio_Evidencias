package dl.dao;

import dl.entities.Partido;
import dl.entities.Pareja;
import utils.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO implements IDAO<Partido> {
    private ParejaDAO parejaDAO;

    public PartidoDAO() {
        this.parejaDAO = new ParejaDAO();
    }

    @Override
    public void insertar(Partido partido) throws Exception {
        String sql = "INSERT INTO partidos (pareja1_id, pareja2_id, resultado) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, partido.getPareja1().getId());
            stmt.setInt(2, partido.getPareja2().getId());
            stmt.setString(3, partido.getResultado());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    partido.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Partido partido) throws Exception {
        String sql = "UPDATE partidos SET pareja1_id = ?, pareja2_id = ?, resultado = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, partido.getPareja1().getId());
            stmt.setInt(2, partido.getPareja2().getId());
            stmt.setString(3, partido.getResultado());
            stmt.setInt(4, partido.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(String id) throws Exception {
        String sql = "DELETE FROM partidos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            stmt.executeUpdate();
        }
    }

    @Override
    public Partido obtener(String id) throws Exception {
        String sql = "SELECT * FROM partidos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return crearPartidoDesdeResultSet(rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<Partido> listarTodos() throws Exception {
        List<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM partidos";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                partidos.add(crearPartidoDesdeResultSet(rs));
            }
        }
        return partidos;
    }

    private Partido crearPartidoDesdeResultSet(ResultSet rs) throws Exception {
        Pareja pareja1 = parejaDAO.obtener(String.valueOf(rs.getInt("pareja1_id")));
        Pareja pareja2 = parejaDAO.obtener(String.valueOf(rs.getInt("pareja2_id")));
        Partido partido = new Partido(pareja1, pareja2);
        partido.setId(rs.getInt("id"));
        partido.setResultado(rs.getString("resultado"));
        return partido;
    }

    // Métodos adicionales útiles
    public List<Partido> obtenerPartidosPorPareja(int parejaId) throws Exception {
        List<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM partidos WHERE pareja1_id = ? OR pareja2_id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, parejaId);
            stmt.setInt(2, parejaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    partidos.add(crearPartidoDesdeResultSet(rs));
                }
            }
        }
        return partidos;
    }

    public void actualizarResultado(int partidoId, String resultado) throws Exception {
        String sql = "UPDATE partidos SET resultado = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, resultado);
            stmt.setInt(2, partidoId);
            stmt.executeUpdate();
        }
    }

    public List<Partido> obtenerPartidosPendientes() throws Exception {
        List<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM partidos WHERE resultado = 'Pendiente'";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                partidos.add(crearPartidoDesdeResultSet(rs));
            }
        }
        return partidos;
    }
}
