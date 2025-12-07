package dl.dao;

import dl.entities.Pareja;
import dl.entities.Jugador;
import utils.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParejaDAO implements IDAO<Pareja> {
    private JugadorDAO jugadorDAO;

    public ParejaDAO() {
        this.jugadorDAO = new JugadorDAO();
    }

    @Override
    public void insertar(Pareja pareja) throws Exception {
        String sql = "INSERT INTO parejas (jugador1_id, jugador2_id) VALUES (?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pareja.getJugador1().getId());
            stmt.setString(2, pareja.getJugador2().getId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    // Aquí podrías establecer el ID en la pareja si agregas un campo id
                }
            }
        }
    }

    @Override
    public void actualizar(Pareja pareja) throws Exception {
        String sql = "UPDATE parejas SET jugador1_id = ?, jugador2_id = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pareja.getJugador1().getId());
            stmt.setString(2, pareja.getJugador2().getId());
            stmt.setInt(3, pareja.getId()); // Asegúrate de agregar getId() a la clase Pareja
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(String id) throws Exception {
        String sql = "DELETE FROM parejas WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            stmt.executeUpdate();
        }
    }

    @Override
    public Pareja obtener(String id) throws Exception {
        String sql = "SELECT * FROM parejas WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Jugador jugador1 = jugadorDAO.obtener(rs.getString("jugador1_id"));
                    Jugador jugador2 = jugadorDAO.obtener(rs.getString("jugador2_id"));
                    Pareja pareja = new Pareja(jugador1, jugador2);
                    // Asegúrate de agregar setId() a la clase Pareja
                    pareja.setId(rs.getInt("id"));
                    return pareja;
                }
                return null;
            }
        }
    }

    @Override
    public List<Pareja> listarTodos() throws Exception {
        List<Pareja> parejas = new ArrayList<>();
        String sql = "SELECT * FROM parejas";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Jugador jugador1 = jugadorDAO.obtener(rs.getString("jugador1_id"));
                Jugador jugador2 = jugadorDAO.obtener(rs.getString("jugador2_id"));
                Pareja pareja = new Pareja(jugador1, jugador2);
                pareja.setId(rs.getInt("id"));
                parejas.add(pareja);
            }
        }
        return parejas;
    }

    // Métodos adicionales útiles
    public List<Pareja> obtenerParejasPorJugador(String jugadorId) throws Exception {
        List<Pareja> parejas = new ArrayList<>();
        String sql = "SELECT * FROM parejas WHERE jugador1_id = ? OR jugador2_id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jugadorId);
            stmt.setString(2, jugadorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Jugador jugador1 = jugadorDAO.obtener(rs.getString("jugador1_id"));
                    Jugador jugador2 = jugadorDAO.obtener(rs.getString("jugador2_id"));
                    Pareja pareja = new Pareja(jugador1, jugador2);
                    pareja.setId(rs.getInt("id"));
                    parejas.add(pareja);
                }
            }
        }
        return parejas;
    }

    public boolean existePareja(String jugador1Id, String jugador2Id) throws Exception {
        String sql = "SELECT COUNT(*) FROM parejas WHERE (jugador1_id = ? AND jugador2_id = ?) OR (jugador1_id = ? AND jugador2_id = ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jugador1Id);
            stmt.setString(2, jugador2Id);
            stmt.setString(3, jugador2Id);
            stmt.setString(4, jugador1Id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}