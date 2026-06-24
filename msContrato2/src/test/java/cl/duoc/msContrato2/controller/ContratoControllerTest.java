package cl.duoc.msContrato2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.msContrato2.dto.ContratoDTO;
import cl.duoc.msContrato2.dto.ReservaDTO;
import cl.duoc.msContrato2.model.Contrato;
import cl.duoc.msContrato2.model.EstadoContrato;
import cl.duoc.msContrato2.service.ContratoService;

@WebMvcTest(ContratoController.class)
public class ContratoControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private ContratoService contratoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Contrato ejemContrato;
    private ContratoDTO dtoEjemplo;

    @BeforeEach
    void setUp() {
        EstadoContrato estado = new EstadoContrato();
        estado.setId(1);
        estado.setNombre("Firmado");

        ejemContrato = new Contrato();
        ejemContrato.setId(1);
        ejemContrato.setNumContrato("CON-100");
        ejemContrato.setClienteId("CLI-55");
        ejemContrato.setFechaInicio("2026-06-01");
        ejemContrato.setFechaFin("2027-06-01");
        ejemContrato.setMonto(350000.0);
        ejemContrato.setEstado(estado);

        dtoEjemplo = new ContratoDTO();
        dtoEjemplo.setNumeroContrato("CON-100");
        dtoEjemplo.setClienteId("CLI-55");
        dtoEjemplo.setFechaInicio("2026-06-01");
        dtoEjemplo.setFechaFin("2027-06-01");
        dtoEjemplo.setMonto(350000.0);
        dtoEjemplo.setEstado("Firmado");
        dtoEjemplo.setReserva(new ReservaDTO());
    }

    // ---------- listar ----------

    @Test
    public void listar_retorna200conLista() throws Exception {
        when(contratoService.listarContratos()).thenReturn(List.of(ejemContrato));

        mock.perform(get("/api/v1/contratos"))
            .andExpect(status().isOk());
    }

    @Test
    public void listar_retorna404SiHayError() throws Exception {
        when(contratoService.listarContratos()).thenThrow(new RuntimeException());

        mock.perform(get("/api/v1/contratos"))
            .andExpect(status().isNotFound());
    }

    // ---------- obtenerPorId ----------

    @Test
    public void obtenerPorId_retorna200() throws Exception {
        when(contratoService.buscarContrato(1)).thenReturn(ejemContrato);

        mock.perform(get("/api/v1/contratos/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void obtenerPorId_retorna404() throws Exception {
        when(contratoService.buscarContrato(99)).thenThrow(new RuntimeException());

        mock.perform(get("/api/v1/contratos/99"))
            .andExpect(status().isNotFound());
    }

    // ---------- GuardarContrato (POST) ----------

    @Test
    public void guardarContrato_retorna200() throws Exception {
        when(contratoService.guardarContrato(any(Contrato.class))).thenReturn(ejemContrato);

        mock.perform(post("/api/v1/contratos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejemContrato)))
            .andExpect(status().isOk());
    }

    @Test
    public void guardarContrato_retorna404SiHayError() throws Exception {
        when(contratoService.guardarContrato(any(Contrato.class))).thenThrow(new RuntimeException());

        mock.perform(post("/api/v1/contratos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejemContrato)))
            .andExpect(status().isNotFound());
    }

    // ---------- actualizarContrato (PUT) ----------

    @Test
    public void actualizarContrato_retorna200() throws Exception {
        when(contratoService.actualizarContrato(eq(1), any(Contrato.class))).thenReturn(ejemContrato);

        mock.perform(put("/api/v1/contratos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejemContrato)))
            .andExpect(status().isOk());
    }

    @Test
    public void actualizarContrato_retorna404() throws Exception {
        when(contratoService.actualizarContrato(eq(99), any(Contrato.class))).thenThrow(new RuntimeException());

        mock.perform(put("/api/v1/contratos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ejemContrato)))
            .andExpect(status().isNotFound());
    }

    // ---------- detalleMantenimientoDTO (Detalle por ID) ----------

    @Test
    public void detalleMantenimientoDTO_retorna200() throws Exception {
        when(contratoService.obtenerDetallesContrato(1)).thenReturn(dtoEjemplo);

        mock.perform(get("/api/v1/contratos/detalle/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numeroContrato").value("CON-100"))
            .andExpect(jsonPath("$.estado").value("Firmado"));
    }

    @Test
    public void detalleMantenimientoDTO_retorna404() throws Exception {
        when(contratoService.obtenerDetallesContrato(99)).thenThrow(new RuntimeException());

        mock.perform(get("/api/v1/contratos/detalle/99"))
            .andExpect(status().isNotFound());
    }

    // ---------- eliminarContrato (DELETE) ----------

    @Test
    public void eliminarContrato_retorna204() throws Exception {
        doNothing().when(contratoService).eliminarContrato(1);

        mock.perform(delete("/api/v1/contratos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void eliminarContrato_retorna404() throws Exception {
        doThrow(new RuntimeException("Contrato no encontrado")).when(contratoService).eliminarContrato(99);

        mock.perform(delete("/api/v1/contratos/99"))
            .andExpect(status().isNotFound());
    }
}