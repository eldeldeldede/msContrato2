package cl.duoc.msContrato2.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msContrato2.client.ReservaClient;
import cl.duoc.msContrato2.dto.ContratoDTO;
import cl.duoc.msContrato2.dto.ReservaDTO;
import cl.duoc.msContrato2.model.Contrato;
import cl.duoc.msContrato2.model.EstadoContrato;
import cl.duoc.msContrato2.repository.ContratoRepository;

@ExtendWith(MockitoExtension.class)
public class ContratoServiceTest {

    @Mock
    private ContratoRepository contratoRepo;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private ContratoService contratoService;

    private Contrato ejemContrato;
    private ReservaDTO ejemReserva;

    @BeforeEach
    void setUp() {
        EstadoContrato estado = new EstadoContrato();
        estado.setId(1);
        estado.setNombre("Activo");

        ejemContrato = new Contrato();
        ejemContrato.setId(1);
        ejemContrato.setNumContrato("CONTR-2026-001");
        ejemContrato.setClienteId("CLIENTE-100");
        ejemContrato.setFechaInicio("2026-01-01");
        ejemContrato.setFechaFin("2026-12-31");
        ejemContrato.setMonto(450000.0);
        ejemContrato.setEstado(estado);

        ejemReserva = new ReservaDTO();
    }

    @Test
    void listarContratos_retornaLista() {
        when(contratoRepo.findAll()).thenReturn(List.of(ejemContrato));

        List<Contrato> resultado = contratoService.listarContratos();

        assertEquals(1, resultado.size());
        assertEquals("CONTR-2026-001", resultado.get(0).getNumContrato());
    }

    @Test
    void buscarContrato_encontrado() {
        when(contratoRepo.findById(1)).thenReturn(Optional.of(ejemContrato));

        Contrato resultado = contratoService.buscarContrato(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarContrato_noEncontrado() {
        when(contratoRepo.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contratoService.buscarContrato(1);
        });

        assertEquals("Contrato no encontrado", exception.getMessage());
    }

    @Test
    void guardarContrato_exitoso() {
        when(contratoRepo.save(ejemContrato)).thenReturn(ejemContrato);

        Contrato resultado = contratoService.guardarContrato(ejemContrato);

        assertNotNull(resultado);
        assertEquals("CONTR-2026-001", resultado.getNumContrato());
        verify(contratoRepo, times(1)).save(ejemContrato);
    }

    @Test
    void eliminarContrato_exitoso() {
        when(contratoRepo.existsById(1)).thenReturn(true);

        assertDoesNotThrow(() -> contratoService.eliminarContrato(1));
        verify(contratoRepo, times(1)).deleteById(1);
    }

    @Test
    void eliminarContrato_noExiste() {
        when(contratoRepo.existsById(99)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contratoService.eliminarContrato(99);
        });

        assertEquals("Contrato no encontrado", exception.getMessage());
        verify(contratoRepo, times(0)).deleteById(99);
    }

    @Test
    void actualizarContrato_exitoso() {
        Contrato datosNuevos = new Contrato();
        datosNuevos.setNumContrato("CONTR-MODIFICADO");
        datosNuevos.setClienteId("CLIENTE-200");
        datosNuevos.setFechaInicio("2026-02-01");
        datosNuevos.setFechaFin("2026-11-30");
        datosNuevos.setMonto(500000.0);

        when(contratoRepo.findById(1)).thenReturn(Optional.of(ejemContrato));
        when(contratoRepo.save(ejemContrato)).thenReturn(ejemContrato);

        Contrato resultado = contratoService.actualizarContrato(1, datosNuevos);

        assertEquals("CONTR-MODIFICADO", resultado.getNumContrato());
        assertEquals("CLIENTE-200", resultado.getClienteId());
        assertEquals(500000.0, resultado.getMonto());
        verify(contratoRepo, times(1)).save(ejemContrato);
    }

    @Test
    void actualizarContrato_noEncontrado() {
        when(contratoRepo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contratoService.actualizarContrato(99, ejemContrato);
        });

        assertEquals("Contrato no encontrado", exception.getMessage());
        verify(contratoRepo, times(0)).save(any(Contrato.class));
    }

    @Test
    void obtenerDetallesContrato_exitoso() {
        when(contratoRepo.findById(1)).thenReturn(Optional.of(ejemContrato));
        when(reservaClient.ObtenerReservaCompleta(1)).thenReturn(ejemReserva);

        ContratoDTO resultado = contratoService.obtenerDetallesContrato(1);

        assertNotNull(resultado);
        assertEquals("CONTR-2026-001", resultado.getNumeroContrato());
        assertEquals("Activo", resultado.getEstado());
        assertEquals(ejemReserva, resultado.getReserva());
    }

    @Test
    void obtenerDetallesContrato_noEncontrado() {
        when(contratoRepo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contratoService.obtenerDetallesContrato(99);
        });

        assertEquals("contrato no encontrado", exception.getMessage());
    }
}