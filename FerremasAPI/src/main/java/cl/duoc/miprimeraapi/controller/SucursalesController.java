package cl.duoc.miprimeraapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.duoc.miprimeraapi.model.Sucursales;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sucursales")
public class SucursalesController {

    private Map<Long, Sucursales> data = new HashMap<>();
    private Long contadorId = 0L;

    // 👇 Sucursal precargada para que funcione GET /sucursales/1
    public SucursalesController() {
        Sucursales sucursalInicial = new Sucursales();
        sucursalInicial.setNombreSucursal("Sucursal Central");
        sucursalInicial.setDireccion("Av. Principal 123");
        sucursalInicial.setId(++contadorId);
        data.put(contadorId, sucursalInicial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursales> getSucursales(@PathVariable Long id) {
        Sucursales sucursalData = data.get(id);
        if (sucursalData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sucursalData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sucursales> postSucursales(@RequestBody Sucursales sucursal) {
        sucursal.setId(++contadorId);
        data.put(contadorId, sucursal);
        return new ResponseEntity<>(data.get(contadorId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Sucursales>> getTodosSucursales() {
        return new ResponseEntity<>(new ArrayList<>(data.values()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursales> putSucursales(@PathVariable Long id, @RequestBody Sucursales sucursalRequest) {
        if (!data.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sucursalRequest.setId(id);
        data.put(id, sucursalRequest);
        return new ResponseEntity<>(data.get(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Sucursales> patchSucursales(@PathVariable Long id, @RequestBody Sucursales sucursalRequest) {
        Sucursales existente = data.get(id);
        if (existente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (sucursalRequest.getNombreSucursal() != null) {
            existente.setNombreSucursal(sucursalRequest.getNombreSucursal());
        }
        if (sucursalRequest.getDireccion() != null) {
            existente.setDireccion(sucursalRequest.getDireccion());
        }
        data.put(id, existente);
        return new ResponseEntity<>(existente, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursales(@PathVariable Long id) {
        if (data.remove(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
