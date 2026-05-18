package cl.duoc.msContrato2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msContrato2.dto.ContratoDTO;
import cl.duoc.msContrato2.model.Contrato;
import cl.duoc.msContrato2.service.ContratoService;

@RestController
@RequestMapping("/api/v1/contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping
    public ResponseEntity<List<Contrato>> listar() {
        try{
            List<Contrato> contrato = contratoService.listarContratos();
            return ResponseEntity.ok(contrato);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> obtenerPorId(@PathVariable Integer id) {
        try{
            Contrato contrato = contratoService.buscarContrato(id);
            return ResponseEntity.ok(contrato);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Contrato> GuardarContrato(@RequestBody Contrato contrato) {
        try{
            Contrato nuevoContrato = contratoService.guardarContrato(contrato);
            return ResponseEntity.ok(nuevoContrato);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrato> actualizarContrato(@PathVariable Integer id, @RequestBody Contrato contratoActualizado){
        try {
            Contrato contrato = contratoService.actualizarContrato(id, contratoActualizado);
            return ResponseEntity.ok(contrato);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<ContratoDTO> detalleMantenimientoDTO(@PathVariable Integer id){
        try {
            ContratoDTO contratoDTO = contratoService.obtenerDetallesContrato(id);
            return ResponseEntity.ok(contratoDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
