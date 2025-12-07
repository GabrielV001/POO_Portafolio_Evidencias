package dl.dao;

import java.util.List;

public interface IDAO<T> {
    void insertar(T entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(String id) throws Exception;
    T obtener(String id) throws Exception;
    List<T> listarTodos() throws Exception;
}