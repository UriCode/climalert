package ar.edu.utn.frba.dds.domain;

import java.time.format.DateTimeFormatter;

public class ReglaAlertaCritica implements ReglaAlerta {
    private static final double TEMPERATURA_CRITICA = 35.0;
    private static final double HUMEDAD_CRITICA = 60.0;

    @Override
    public boolean esCritica(MedicionClimatica medicion) {
        return medicion.getTemperatura() > TEMPERATURA_CRITICA && medicion.getHumedad() > HUMEDAD_CRITICA;
    }

    @Override
    public String generarMensaje(MedicionClimatica medicion) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
            "ALERTA METEOROLÓGICA CRÍTICA para %s a las %s: Temperatura: %.1f°C (Límite: >35°C), Humedad: %.1f%% (Límite: >60%%)",
            medicion.getUbicacion(),
            medicion.getFechaHora().format(formatter),
            medicion.getTemperatura(),
            medicion.getHumedad()
        );
    }}
