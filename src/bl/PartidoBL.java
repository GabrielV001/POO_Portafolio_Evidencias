package bl;


import dl.dao.PartidoDAO;
import dl.entities.Partido;
import java.util.List;

public class PartidoBL {
    private PartidoDAO partidoDAO;

    public PartidoBL() {
        this.partidoDAO = new PartidoDAO();
    }

    public void registrarPartido(Partido partido) throws Exception {
        if (partido.getPareja1() == null || partido.getPareja2() == null) {
            throw new Exception("Ambas parejas son requeridas");
        }
        partidoDAO.insertar(partido);
    }

    public void actualizarResultado(Partido partido) throws Exception {
        if (partido.getResultado() == null || partido.getResultado().trim().isEmpty()) {
            throw new Exception("El resultado es requerido");
        }
        partidoDAO.actualizar(partido);
    }

    public List<Partido> listarPartidos() throws Exception {
        return partidoDAO.listarTodos();
    }

    public Partido obtenerPartido(String id) {
        return null;
    }

    public void actualizarPartido(Partido partido) {
    }
}