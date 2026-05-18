package cl.duoc.msContrato2.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msContrato2.dto.ClienteDTO;



@FeignClient(name = "msCliente", url = "https://localhost:8083")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/dto/{id}")
    ClienteDTO clienteDTO(@PathVariable("id") Integer id);

}
