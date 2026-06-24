package cl.duoc.msContrato2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contrato")
@Schema(description = "Entidad que representa un contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del contrato", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Número del contrato", example = "CONT-1001")
    private String numContrato;

    @Column(nullable = false)
    @Schema(description = "ID del cliente asociado al contrato", example = "1")
    private String clienteId;

    @Column(nullable = false)
    @Schema(description = "Fecha de inicio del contrato", example = "2026-05-17")
    private String fechaInicio;

    @Column(nullable = false)
    @Schema(description = "Fecha de fin del contrato", example = "2026-05-25")
    private String fechaFin;

    @Column(nullable = false)
    @Schema(description = "Monto del contrato", example = "150000")
    private double monto;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    @JsonBackReference
    @Schema(description = "Estado del contrato")
    private EstadoContrato estado;

}
