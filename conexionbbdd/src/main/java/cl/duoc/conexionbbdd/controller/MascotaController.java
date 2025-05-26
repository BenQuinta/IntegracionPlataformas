package cl.duoc.conexionbbdd.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.conexionbbdd.model.Mascota;
import cl.duoc.conexionbbdd.service.MascotaService;

@RestController
@RequestMapping
public class MascotaController {

     @Autowired
    private MascotaService mascotaService;

    @GetMapping("/mascotas")
    public ResponseEntity<List<Mascota>> getAll() {
        return ResponseEntity.ok(mascotaService.getAll());
    }

    @GetMapping("/mascotas/{id}")
    public ResponseEntity<Mascota> getById(@PathVariable Long id) {
        Mascota mascota = mascotaService.getById(id);
        if (mascota == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mascota);
    }

    @GetMapping("/mascotas/buscar")
    public ResponseEntity<List<Mascota>> getByNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(mascotaService.getByNombre(nombre));
    }

    @PostMapping("/mascotas")
    public ResponseEntity<Mascota> create(@RequestBody Mascota mascota) {
        return new ResponseEntity<>(mascotaService.create(mascota), HttpStatus.CREATED);
    }

    @PutMapping("/mascotas/{id}")
    public ResponseEntity<Mascota> update(@PathVariable Long id, @RequestBody Mascota mascotaDetalles) {
        Mascota updated = mascotaService.update(id, mascotaDetalles);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/mascotas/{id}")
    public ResponseEntity<Mascota> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Mascota updated = mascotaService.patch(id, updates);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/mascotas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!mascotaService.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

}
