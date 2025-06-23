package cl.duoc.miprimeraapi.repository;

import cl.duoc.miprimeraapi.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Usuarios findByEmail(String email);
    boolean existsByEmail(String email);
}
