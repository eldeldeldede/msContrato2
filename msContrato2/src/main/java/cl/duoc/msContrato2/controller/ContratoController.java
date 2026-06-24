package cl.duoc.msContrato2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/contratos")
@Tag(name = "Contrato", description = "Controlador para la gestión de contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping
    @Operation(summary = "Listar contratos",
            description = "Obtiene una lista de todos los contratos registrados"
    )
    public ResponseEntity<List<Contrato>> listar() {
        try{
            List<Contrato> contrato = contratoService.listarContratos();
            return ResponseEntity.ok(contrato);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener contrato por ID",
            description = "Obtiene un contrato específico según su ID"
    )
    public ResponseEntity<Contrato> obtenerPorId(@PathVariable Integer id) {
        try{
            Contrato contrato = contratoService.buscarContrato(id);
            return ResponseEntity.ok(contrato);
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar contrato",
            description = "Guarda un nuevo contrato en el sistema"
    )
    public ResponseEntity<Contrato> GuardarContrato(@RequestBody Contrato contrato) {
        try{
            Contrato nuevoContrato = contratoService.guardarContrato(contrato);
            return ResponseEntity.ok(nuevoContrato);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar contrato",
            description = "Actualiza un contrato existente según su ID"
    )
    public ResponseEntity<Contrato> actualizarContrato(@PathVariable Integer id, @RequestBody Contrato contratoActualizado){
        try {
            Contrato contrato = contratoService.actualizarContrato(id, contratoActualizado);
            return ResponseEntity.ok(contrato);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/detalle/{id}")
    @Operation(summary = "Obtener detalles del contrato",
            description = "Obtiene los detalles de un contrato específico según su ID"
    )
    public ResponseEntity<ContratoDTO> detalleMantenimientoDTO(@PathVariable Integer id){
        try {
            ContratoDTO contratoDTO = contratoService.obtenerDetallesContrato(id);
            return ResponseEntity.ok(contratoDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar contrato",
            description = "Elimina un contrato específico según su ID"
    )
    public ResponseEntity<Void> eliminarContrato(@PathVariable Integer id) {
        try {
            contratoService.eliminarContrato(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
}
}
