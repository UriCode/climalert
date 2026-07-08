package ar.edu.utn.frba.dds.domain;

public interface ReglaAlerta {
    boolean esCritica(MedicionClimatica medicion);
    String generarMensaje(MedicionClimatica medicion);
}
