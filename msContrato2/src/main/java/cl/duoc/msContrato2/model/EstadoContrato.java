package cl.duoc.msContrato2.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estado_contrato")
@Schema(description = "Entidad que representa el estado de un contrato")
public class EstadoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del estado del contrato", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del estado del contrato", example = "Activo")
    private String nombre;
}
