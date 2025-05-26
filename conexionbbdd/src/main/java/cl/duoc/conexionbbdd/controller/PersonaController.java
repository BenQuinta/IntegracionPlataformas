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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.conexionbbdd.model.Persona;
import cl.duoc.conexionbbdd.service.PersonaService;

@RestController
public class PersonaController {
    
    @Autowired
    private PersonaService personaService;

    @GetMapping("/personas")
    public ResponseEntity<List<Persona>> getAll() {
        return ResponseEntity.ok(personaService.getAll());
    }

    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getById(@PathVariable Long id) {
        Persona persona = personaService.getById(id);
        if (persona == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(persona);
    }

    @GetMapping("/personas/buscar")
    public ResponseEntity<List<Persona>> getByNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(personaService.getByNombre(nombre));
    }

    @PostMapping("/personas")
    public ResponseEntity<Persona> create(@RequestBody Persona persona) {
        return new ResponseEntity<>(personaService.create(persona), HttpStatus.CREATED);
    }

    @PutMapping("/personas/{id}")
    public ResponseEntity<Persona> update(@PathVariable Long id, @RequestBody Persona personaDetalles) {
        Persona updated = personaService.update(id, personaDetalles);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/personas/{id}")
    public ResponseEntity<Persona> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Persona updated = personaService.patch(id, updates);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/personas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!personaService.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
