package cl.duoc.conexionbbdd.service;

import java.util.List;
import java.util.Map;

import cl.duoc.conexionbbdd.model.Mascota;

public interface MascotaService {
    
    List<Mascota> getAll();
    Mascota getById(Long id);
    List<Mascota> getByNombre(String nombre);
    Mascota create(Mascota mascota);
    Mascota update(Long id, Mascota mascotaDetalles);
    Mascota patch(Long id, Map<String, Object> updates);
    boolean delete(Long id);

}
