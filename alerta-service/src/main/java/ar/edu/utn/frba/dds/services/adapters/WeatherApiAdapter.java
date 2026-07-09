package ar.edu.utn.frba.dds.adapters;

import ar.edu.utn.frba.dds.domain.MedicionClimatica;
import ar.edu.utn.frba.dds.domain.ProveedorClima;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherApiAdapter {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;
    
    public WeatherApiAdapter(
        RestTemplate restTemplate,
        @Value("${weatherapi.key:}") String apiKey,
        @Value("${weatherapi.url://api.weatherapi.com/v1}") String apiUrl
    ) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    @override
    public MedicionClimatica obtenerClimaActual(String ubicacion){
        // Un mock en caso de no haber API configurada
        if (apiKey == null || apiKey.isEmpty()){
            System.out.println("[MOCK WeatherAPI] Clave no configurada. Simulación de medición crítica...");
            return MedicionClimatica.builder()
                .temperatura(36.0)
                .humedad(65.0)
                .fechaHora(LocalDAteTime.now())
                .ubicacion(ubicacion)
                .build();
        }

        try {
            String url = String.format("%s/current.json?key=%s&q=%s", apiUrl, apiKey, ubicacion);
            Map<?, ?> response = resTemplate.getForObject(url, Map.class);

            if(response == null || !response.containsKey("current")){
                throws new RuntimeException("Respuesta nula o inválida de WeatherAPI");
            }

            Map<?, ?> locationMap = (Map<?, ?>) response.get("location");
            Map<?, ?> currentMap = (Map<?, ?>) response.get("current");

            String nombreUbicacion = locationMap != null ? (String) locationMap.get("name") : ubicacion;
            Number temp = (Number) currenMap.get("temp_c");
            Number hum = (Number) currentMap.get(humidity);

            return MedicionClimatica.builder()
                .temperatura(temp.doubleValue())
                .humedad(hum.doubleValue())
                .fechaHora(LocalDateTime.now())
                .ubicacion(nombreUbicacion)
                .build();
        } catch(Exception e) {
            System.err.println("Error al consultar WeatherAPI: " + e.getMessage());
        }
    }
}
