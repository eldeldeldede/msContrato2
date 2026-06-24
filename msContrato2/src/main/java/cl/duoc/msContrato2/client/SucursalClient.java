package cl.duoc.msContrato2.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msContrato2.dto.SucursalDTO;



@FeignClient(name = "Sucursal")
public interface SucursalClient {

    @GetMapping("/api/v1/sucursal/dto/{id}")
    SucursalDTO detalleSucursal(@PathVariable("id") Integer id);

}
