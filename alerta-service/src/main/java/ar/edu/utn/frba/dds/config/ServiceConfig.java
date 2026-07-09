package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.domain.NotificadorAlerta;
import ar.edu.utn.frba.dds.domain.ProveedorClima;
import ar.edu.utn.frba.dds.domain.ReglaAlerta;
import ar.edu.utn.frba.dds.domain.ReglaAlertaCritica;
import ar.edu.utn.frba.dds.domain.RepositorioMediciones;
import ar.edu.utn.frba.dds.services.ClimaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public ReglaAlerta reglaAlerta(){
        return new ReglaAlertaCritica();
    }

    @Bean
    public ClimaService climaService(
        ProveedorClima proveedorClima,
        RepositorioMediciones repositorioMediciones,
        NotificadorAlerta notificadorAlerta,
        ReglaAlerta reglaAlerta
    ) {
        return new ClimaService(proveedorClima, repositorioMediciones, notificadorAlerta, reglaAlerta);
    }
}
