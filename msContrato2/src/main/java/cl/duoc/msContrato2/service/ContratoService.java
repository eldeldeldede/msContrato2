package cl.duoc.msContrato2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msContrato2.client.ReservaClient;
import cl.duoc.msContrato2.dto.ContratoDTO;
import cl.duoc.msContrato2.dto.ReservaDTO;
import cl.duoc.msContrato2.model.Contrato;
import cl.duoc.msContrato2.repository.ContratoRepository;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepo;

    @Autowired
    private ReservaClient reservaClient;

    


    public List<Contrato> listarContratos() {
        return contratoRepo.findAll();
    }

    public Contrato buscarContrato(Integer id) {
        return contratoRepo.findById(id).orElseThrow(() -> new RuntimeException("Contrato no encontrado"));

    }

    public Contrato guardarContrato(Contrato contrato) {
        return contratoRepo.save(contrato);
    }

    public void eliminarContrato(Integer id){
        if(contratoRepo.existsById(id)){
            contratoRepo.deleteById(id);
        } else {
            throw new RuntimeException("Contrato no encontrado");
        }
    }

    public Contrato actualizarContrato(Integer id, Contrato contratoActualizado) {
        Contrato contratoExistente = contratoRepo.findById(id).orElseThrow(() -> new RuntimeException("Contrato no encontrado"));

        contratoExistente.setNumContrato(contratoActualizado.getNumContrato());
        contratoExistente.setClienteId(contratoActualizado.getClienteId());
        contratoExistente.setFechaInicio(contratoActualizado.getFechaInicio());
        contratoExistente.setFechaFin(contratoActualizado.getFechaFin());
        contratoExistente.setMonto(contratoActualizado.getMonto());
        contratoExistente.setEstado(contratoActualizado.getEstado());

        return contratoRepo.save(contratoExistente);
    }

    public ContratoDTO obtenerDetallesContrato(Integer id){
        Contrato contrato = contratoRepo.findById(id).orElseThrow(() -> new RuntimeException("contrato no encontrado"));

        ReservaDTO reserva = reservaClient.ObtenerReservaCompleta(id);
        

        ContratoDTO contratoCompleto = new ContratoDTO();
        contratoCompleto.setNumeroContrato(contrato.getNumContrato());
        contratoCompleto.setClienteId(contrato.getClienteId());
        contratoCompleto.setFechaInicio(contrato.getFechaInicio());
        contratoCompleto.setFechaFin(contrato.getFechaFin());
        contratoCompleto.setMonto(contrato.getMonto());
        contratoCompleto.setEstado(contrato.getEstado().getNombre());

        contratoCompleto.setReserva(reserva);

        return contratoCompleto;
    
    }

}
