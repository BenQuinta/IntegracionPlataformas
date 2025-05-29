package cl.duoc.miprimeraapi.repository;

import cl.duoc.miprimeraapi.model.Precio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PrecioRepository extends JpaRepository<Precio, Long> {
    List<Precio> findByFecha(LocalDateTime fecha);
}
