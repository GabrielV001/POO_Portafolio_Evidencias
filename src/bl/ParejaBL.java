package bl;

import dl.dao.ParejaDAO;
import dl.entities.Pareja;
import dl.entities.Jugador;
import java.util.List;

public class ParejaBL {
    private ParejaDAO parejaDAO;

    public ParejaBL() {
        this.parejaDAO = new ParejaDAO();
    }

    public void registrarPareja(Pareja pareja) throws Exception {
        if (pareja.getJugador1() == null || pareja.getJugador2() == null) {
            throw new Exception("Ambos jugadores son requeridos");
        }
        parejaDAO.insertar(pareja);
    }

    public List<Pareja> listarParejas() throws Exception {
        return parejaDAO.listarTodos();
    }

    public void eliminarPareja(int id) throws Exception {
        parejaDAO.eliminar(String.valueOf(id));
    }

    public Pareja obtenerPareja(String id) {
        return null;
    }
}
