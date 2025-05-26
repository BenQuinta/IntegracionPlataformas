package cl.duoc.conexionbbdd.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import cl.duoc.conexionbbdd.model.Persona;
import cl.duoc.conexionbbdd.repository.PersonaRepository;
import cl.duoc.conexionbbdd.service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    public List<Persona> getAll() {
        return personaRepository.findAll();
    }

    public Persona getById(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    public List<Persona> getByNombre(String nombre) {
        return personaRepository.findByNombreContaining(nombre);
    }

    public Persona create(Persona persona) {
        return personaRepository.save(persona);
    }

    public Persona update(Long id, Persona personaDetalles) {
        Persona persona = getById(id);
        if (persona == null) return null;
        persona.setNombre(personaDetalles.getNombre());
        persona.setEmail(personaDetalles.getEmail());
        return personaRepository.save(persona);
    }

    public Persona patch(Long id, Map<String, Object> updates) {
        Persona persona = getById(id);
        if (persona == null) return null;
        for (String key : updates.keySet()) {
            Field field = ReflectionUtils.findField(Persona.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, persona, updates.get(key));
            }
        }
        return personaRepository.save(persona);
    }

    public boolean delete(Long id) {
        Persona persona = getById(id);
        if (persona == null) return false;
        personaRepository.delete(persona);
        return true;
    }

}
