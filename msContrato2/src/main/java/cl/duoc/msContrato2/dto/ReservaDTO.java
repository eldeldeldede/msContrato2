package cl.duoc.msContrato2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    private Integer id;
    private String estado;


    private SucursalDTO sucursal;
    private ClienteDTO cliente;

}
