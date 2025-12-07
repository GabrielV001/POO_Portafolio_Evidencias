package bl;

import dl.dao.JugadorDAO;
import dl.entities.Jugador;
import java.util.List;

public class JugadorBL {
    private JugadorDAO jugadorDAO;

    public JugadorBL() {
        this.jugadorDAO = new JugadorDAO();
    }

    public void registrarJugador(Jugador jugador) throws Exception {
        if (jugador.getId() == null || jugador.getId().trim().isEmpty()) {
            throw new Exception("El ID del jugador es requerido");
        }
        if (jugador.getNombre() == null || jugador.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del jugador es requerido");
        }
        if (jugador.getNivel() == null || jugador.getNivel().trim().isEmpty()) {
            throw new Exception("El nivel del jugador es requerido");
        }
        jugadorDAO.insertar(jugador);
    }

    public List<Jugador> listarJugadores() throws Exception {
        return jugadorDAO.listarTodos();
    }

    public Jugador buscarJugador(String id) throws Exception {
        return jugadorDAO.obtener(id);
    }

    public void actualizarJugador(Jugador jugador) throws Exception {
        jugadorDAO.actualizar(jugador);
    }

    public void eliminarJugador(String id) throws Exception {
        jugadorDAO.eliminar(id);
    }
}