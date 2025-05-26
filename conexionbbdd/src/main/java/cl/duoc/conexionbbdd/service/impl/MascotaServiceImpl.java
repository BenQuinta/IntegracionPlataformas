package cl.duoc.conexionbbdd.service.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import cl.duoc.conexionbbdd.model.Mascota;
import cl.duoc.conexionbbdd.repository.MascotaRepository;
import cl.duoc.conexionbbdd.service.MascotaService;

@Service
public class MascotaServiceImpl implements MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<Mascota> getAll() {
        return mascotaRepository.findAll();
    }

    public Mascota getById(Long id) {
        return mascotaRepository.findById(id).orElse(null);
    }

    public List<Mascota> getByNombre(String nombre) {
        return mascotaRepository.findByNombreContaining(nombre);
    }

    public Mascota create(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public Mascota update(Long id, Mascota mascotaDetalles) {
        Mascota mascota = getById(id);
        if (mascota == null) return null;
        mascota.setNombre(mascotaDetalles.getNombre());
        mascota.setTipo(mascotaDetalles.getTipo());
        mascota.setPersona(mascotaDetalles.getPersona());
        return mascotaRepository.save(mascota);
    }

    public Mascota patch(Long id, Map<String, Object> updates) {
        Mascota mascota = getById(id);
        if (mascota == null) return null;
        for (String key : updates.keySet()) {
            Field field = ReflectionUtils.findField(Mascota.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, mascota, updates.get(key));
            }
        }
        return mascotaRepository.save(mascota);
    }

    public boolean delete(Long id) {
        Mascota mascota = getById(id);
        if (mascota == null) return false;
        mascotaRepository.delete(mascota);
        return true;
    }

}
