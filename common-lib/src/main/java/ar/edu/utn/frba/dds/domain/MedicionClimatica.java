package ar.edu.utn.frba.dds.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicionClimatica {
    private Double temperatura;
    private Double humedad;
    private LocalDateTime fechaHora;
    private String ubicacion;
}
