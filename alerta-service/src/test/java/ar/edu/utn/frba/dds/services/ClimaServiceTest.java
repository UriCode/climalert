package ar.edu.utn.frba.dds.services;                                                                                                                                                                                                  
                                                                                                                                                                                                                                        
import static org.mockito.Mockito.*;                                                                                                                                                                                                   
                                                                                                                                                                                                                                        
import ar.edu.utn.frba.dds.domain.Alerta;                                                                                                                                                                                              
import ar.edu.utn.frba.dds.domain.MedicionClimatica;                                                                                                                                                                                   
import ar.edu.utn.frba.dds.domain.NotificadorAlerta;                                                                                                                                                                                   
import ar.edu.utn.frba.dds.domain.ProveedorClima;                                                                                                                                                                                      
import ar.edu.utn.frba.dds.domain.ReglaAlerta;                                                                                                                                                                                         
import ar.edu.utn.frba.dds.domain.ReglaAlertaCritica;                                                                                                                                                                                  
import ar.edu.utn.frba.dds.domain.RepositorioMediciones;                                                                                                                                                                               
import java.time.LocalDateTime;                                                                                                                                                                                                        
import java.util.Optional;                                                                                                                                                                                                             
import org.junit.jupiter.api.BeforeEach;                                                                                                                                                                                               
import org.junit.jupiter.api.Test;                                                                                                                                                                                                     
                                                                                                                                                                                                                                        
public class ClimaServiceTest {                                                                                                                                                                                                        
                                                                                                                                                                                                                                        
    private ProveedorClima proveedorClima;                                                                                                                                                                                             
    private RepositorioMediciones repositorioMediciones;                                                                                                                                                                               
    private NotificadorAlerta notificadorAlerta;                                                                                                                                                                                       
    private ReglaAlerta reglaAlerta;                                                                                                                                                                                                   
                                                                                                                                                                                                                                        
    private ClimaService climaService;                                                                                                                                                                                                 
                                                                                                                                                                                                                                        
    @BeforeEach                                                                                                                                                                                                                        
    public void setUp() {                                                                                                                                                                                                              
        proveedorClima = mock(ProveedorClima.class);                                                                                                                                                                                   
        repositorioMediciones = mock(RepositorioMediciones.class);                                                                                                                                                                     
        notificadorAlerta = mock(NotificadorAlerta.class);                                                                                                                                                                             
        // Usamos la regla de negocio real                                                                                                                                                                                             
        reglaAlerta = new ReglaAlertaCritica();                                                                                                                                                                                        
                                                                                                                                                                                                                                        
        climaService = new ClimaService(                                                                                                                                                                                               
            proveedorClima,                                                                                                                                                                                                            
            repositorioMediciones,                                                                                                                                                                                                     
            notificadorAlerta,                                                                                                                                                                                                         
            reglaAlerta                                                                                                                                                                                                                
        );                                                                                                                                                                                                                             
    }                                                                                                                                                                                                                                  
                                                                                                                                                                                                                                        
    @Test                                                                                                                                                                                                                              
    public void registrarClimaActual_deberiaGuardarLaMedicion() {                                                                                                                                                                      
        // Arrange                                                                                                                                                                                                                     
        String ubicacion = "CABA";                                                                                                                                                                                                     
        MedicionClimatica medicion = MedicionClimatica.builder()                                                                                                                                                                       
            .temperatura(25.0)                                                                                                                                                                                                         
            .humedad(50.0)                                                                                                                                                                                                             
            .fechaHora(LocalDateTime.now())                                                                                                                                                                                            
            .ubicacion(ubicacion)                                                                                                                                                                                                      
            .build();                                                                                                                                                                                                                  
                                                                                                                                                                                                                                        
        when(proveedorClima.obtenerClimaActual(ubicacion)).thenReturn(medicion);                                                                                                                                                       
                                                                                                                                                                                                                                        
        // Act                                                                                                                                                                                                                         
        climaService.registrarClimaActual(ubicacion);                                                                                                                                                                                  
                                                                                                                                                                                                                                        
        // Assert                                                                                                                                                                                                                      
        verify(proveedorClima, times(1)).obtenerClimaActual(ubicacion);                                                                                                                                                                
        verify(repositorioMediciones, times(1)).guardar(medicion);                                                                                                                                                                     
    }                                                                                                                                                                                                                                  

    @Test
    public void evaluarAlertas_conClimaCritico_deberiaEnviarAlerta() {
        // Arrange
        MedicionClimatica climaCritico = MedicionClimatica.builder()
            .temperatura(36.0) // Mayor a 35°
            .humedad(65.0)     // Mayor a 60%
            .fechaHora(LocalDateTime.now())
            .ubicacion("CABA")
            .build();
            
        when(repositorioMediciones.obtenerUltima()).thenReturn(Optional.of(climaCritico));

        // Act
        climaService.evaluarAlertas();

        // Assert
        verify(repositorioMediciones, times(1)).obtenerUltima();
        verify(notificadorAlerta, times(1)).notificar(any(Alerta.class));
    }

    @Test
    public void evaluarAlertas_conClimaNormal_noDeberiaEnviarAlerta() {
        // Arrange
        MedicionClimatica climaNormal = MedicionClimatica.builder()
            .temperatura(30.0) // Menor a 35°
            .humedad(50.0)     // Menor a 60%
            .fechaHora(LocalDateTime.now())
            .ubicacion("CABA")
            .build();
            
        when(repositorioMediciones.obtenerUltima()).thenReturn(Optional.of(climaNormal));

        // Act
        climaService.evaluarAlertas();

        // Assert
        verify(repositorioMediciones, times(1)).obtenerUltima();
        verify(notificadorAlerta, never()).notificar(any(Alerta.class));
    }
}
