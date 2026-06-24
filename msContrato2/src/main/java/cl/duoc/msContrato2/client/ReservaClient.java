package cl.duoc.msContrato2.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msContrato2.dto.ReservaDTO;




@FeignClient(name = "msReserva")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/id/{id}")
    ReservaDTO ObtenerReservaCompleta(@PathVariable("id") Integer id);
}
