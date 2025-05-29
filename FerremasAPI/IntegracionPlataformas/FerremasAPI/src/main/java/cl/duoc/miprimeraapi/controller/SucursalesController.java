package cl.duoc.miprimeraapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.miprimeraapi.model.Sucursales;
@RestController
public class SucursalesController {
    private Map<Long, Sucursales> data = new HashMap<Long, Sucursales>();
    private Long contadorId = 0L;

    @GetMapping("sucursales/{id}")
    public ResponseEntity<Sucursales> getSucursales(@PathVariable Long id) {
        Sucursales sucursalData = data.get(id);
        if (sucursalData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sucursalData, HttpStatus.OK);
    }

    @PostMapping("sucursales")
    public ResponseEntity<Sucursales> postSucursales(@RequestBody Sucursales sucursal) {
        sucursal.setId(++contadorId);
        data.put(contadorId, sucursal);
        return new ResponseEntity<>(data.get(contadorId), HttpStatus.CREATED);
    }

    @GetMapping("sucursales")
    public ResponseEntity<List<Sucursales>> getTodosSucursales() {
        List<Sucursales> response = new ArrayList<Sucursales>(data.values());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("sucursales/{id}")
    public ResponseEntity<Sucursales> putSucursales(@PathVariable Long id, @RequestBody Sucursales sucursalRequest) {
        Sucursales sucursalData = data.get(id);
        if (sucursalData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sucursalRequest.setId(id);
        data.put(id, sucursalRequest);
        return new ResponseEntity<>(data.get(id), HttpStatus.OK);
    }

    @PatchMapping("sucursales/{id}")
    public ResponseEntity<Sucursales> patchSucursales(@PathVariable Long id, @RequestBody Sucursales sucursalRequest) {
        Sucursales sucursalData = data.get(id);
        if (sucursalData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (sucursalRequest.getNombreSucursal() != null) {
            sucursalData.setNombreSucursal(sucursalRequest.getNombreSucursal());
        }
        if (sucursalRequest.getDireccion() != null) {
            sucursalData.setDireccion(sucursalRequest.getDireccion());
        }
        data.put(id, sucursalData);
        return new ResponseEntity<>(data.get(id), HttpStatus.OK);
    }

    @DeleteMapping("sucursales/{id}")
    public ResponseEntity<Void> deleteSucursales(@PathVariable Long id) {
        Sucursales sucursalData = data.remove(id);
        if (sucursalData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}