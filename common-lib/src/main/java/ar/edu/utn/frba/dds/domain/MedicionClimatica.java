package ar.edu.utn.frba.dds.domain;

import java.time.LocalDateTime;
import java.util.Optional;
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
    private boolean evaluada;

    public Optional<Alerta> evaluar(ReglaAlerta regla) {
        // Si ya fue evaluada no la volvemos a procesar
        if (this.evaluada) {
            return Optional.empty();
        }

        // Cambiamos su estado si no fue evaluada
        this.evaluada = true;

        if (regla.esCritica(this)) {
            String mensaje = regla.generarMensaje(this);

            Alerta alerta = Alerta.builder()
                .fechaHora(LocalDateTime.now())
                .mensaje(mensaje)
                .medicionOriginal(this)
                .build();
            return Optional.of(alerta);
        }

        return Optional.empty();

    }
}
