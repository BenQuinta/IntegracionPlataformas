package cl.duoc.conexionbbdd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.conexionbbdd.model.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    List<Mascota> findByNombreContaining(String nombre);
    List<Mascota> findByTipo(String tipo);
    
}
