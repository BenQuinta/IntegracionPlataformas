package cl.duoc.conexionbbdd.service;

import java.util.List;
import java.util.Map;

import cl.duoc.conexionbbdd.model.Persona;

public interface PersonaService {
    
    List<Persona> getAll();
    Persona getById(Long id);
    List<Persona> getByNombre(String nombre);
    Persona create(Persona persona);
    Persona update(Long id, Persona personaDetalles);
    Persona patch(Long id, Map<String, Object> updates);
    boolean delete(Long id);
    
}
