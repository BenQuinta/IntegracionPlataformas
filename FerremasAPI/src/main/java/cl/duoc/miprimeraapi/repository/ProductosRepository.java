package cl.duoc.miprimeraapi.repository;

import cl.duoc.miprimeraapi.model.Productos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
    List<Productos> findByCategoria(String categoria);
    List<Productos> findByCodigoProducto(String codigoProducto);

    List<Productos> findByNombreContainingIgnoreCase(String nombre);

    List<Productos> findByStockLessThan(int stock);


}
