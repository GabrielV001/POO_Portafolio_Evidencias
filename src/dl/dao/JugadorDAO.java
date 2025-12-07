package dl.dao;

import dl.entities.Jugador;
import utils.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO implements IDAO<Jugador> {
    @Override
    public void insertar(Jugador jugador) throws Exception {
        String sql = "INSERT INTO jugadores (id, nombre, nivel) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jugador.getId());
            stmt.setString(2, jugador.getNombre());
            stmt.setString(3, jugador.getNivel());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Jugador jugador) throws Exception {
        String sql = "UPDATE jugadores SET nombre = ?, nivel = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jugador.getNombre());
            stmt.setString(2, jugador.getNivel());
            stmt.setString(3, jugador.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(String id) throws Exception {
        String sql = "DELETE FROM jugadores WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Jugador obtener(String id) throws Exception {
        String sql = "SELECT * FROM jugadores WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Jugador(
                            rs.getString("id"),
                            rs.getString("nombre"),
                            rs.getString("nivel")
                    );
                }
                return null;
            }
        }
    }

    @Override
    public List<Jugador> listarTodos() throws Exception {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM jugadores";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                jugadores.add(new Jugador(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("nivel")
                ));
            }
        }
        return jugadores;
    }
}
