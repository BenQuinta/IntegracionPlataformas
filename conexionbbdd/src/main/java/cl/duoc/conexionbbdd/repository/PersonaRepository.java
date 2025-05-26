package cl.duoc.conexionbbdd.repository;

import java.util.List;

import cl.duoc.conexionbbdd.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    List<Persona> findByNombreContaining(String nombre);

}
