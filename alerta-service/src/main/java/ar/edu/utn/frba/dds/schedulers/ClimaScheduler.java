package ar.edu.utn.frba.dds.schedulers;

import ar.edu.utn.frba.dds.services.ClimaService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClimaScheduler {
    private final ClimaService climaService;

    public ClimaScheduler(ClimaService climaService) {
        this.climaService = climaService;
    }

    // Cada 5 min obtiene el clima de CABA y lo guarda
    @Scheduled(fixedRate = 300000)
    public void obtenerClimaPeriodicamente(){
        System.out.println("[Scheduler] Obteniendo clima actual...");
        climaService.registrarClimaActual("Buenos Aires, Argentina");
    }

    // Cada 1 min analiza la ultima medicion disponible
    @Scheduled(fixedRate = 60000)
    public void evaluarAlertasPeriodicamente() {
        System.out.println("[Scheduler] Analizando última medicion disponible...");
        climaService.evaluarAlertas();
    }
}
