package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.Alerta;
import ar.edu.utn.frba.dds.domain.MedicionClimatica;
import ar.edu.utn.frba.dds.domain.NotificadorAlerta;
import ar.edu.utn.frba.dds.domain.ProveedorClima;
import ar.edu.utn.frba.dds.domain.ReglaAlerta;
import ar.edu.utn.frba.dds.domain.RepositorioMediciones;
import java.util.Optional;

public class ClimaService {
    private final ProveedorClima proveedorClima;
    private final RepositorioMediciones repositorioMediciones;
    private final NotificadorAlerta notificadorAlerta;
    private final ReglaAlerta reglaAlerta;

    // Constructor (Inyección de dependencias)
    public ClimaService(ProveedorClima proveedorClima, RepositorioMediciones repositorioMediciones, NotificadorAlerta notificadorAlerta, ReglaAlerta reglaAlerta) {
        this.proveedorClima = proveedorClima;
        this.repositorioMediciones = repositorioMediciones;
        this.notificadorAlerta = notificadorAlerta;
        this.reglaAlerta = reglaAlerta;
    }

    // Obtener y registrar el clima actual
    public void registrarClimaActual(String ubicacion) {
        MedicionClimatica medicion = proveedorClima.obtenerClimaActual(ubicacion);
        repositorioMediciones.guardar(medicion);

        // Loggeo para ver si la API responde bien
        System.out.printf("[API CLIMA] Registro exitoso para %s -> Temp: %.1f°C, Humedad: %.1f%% (Fecha: %s)\n",                                                                                                                       
                medicion.getUbicacion(),                                                                                                                                                                                                   
                medicion.getTemperatura(),                                                                                                                                                                                                 
                medicion.getHumedad(),                                                                                                                                                                                                     
                medicion.getFechaHora()                                                                                                                                                                                                    
            );  
    }

    // Evaluar si el clima actual amerita una alerta
    public void evaluarAlertas() {
        Optional<MedicionClimatica> ultimaMedicionOpt = repositorioMediciones.obtenerUltima();
        if (ultimaMedicionOpt.isEmpty()) {
            System.out.println("No hay mediciones registradas para evaluar alertas.");
            return;
        }

        MedicionClimatica ultimaMedicion = ultimaMedicionOpt.get();

        Optional<Alerta> alertaOpt = ultimaMedicion.evaluar(reglaAlerta);

        alertaOpt.ifPresent(alerta->{
            notificadorAlerta.notificar(alerta);
            // Guarda el nuevo estado de la medicion
            repositorioMediciones.guardar(ultimaMedicion);
        });
    }
}
