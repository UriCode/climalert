package ar.edu.utn.frba.dds.domain;

import java.util.List;
import java.util.Optional;

public interface RepositorioMediciones {
    void guardar(MedicionClimatica medicion);

    List<MedicionClimatica> obtenerTodas();

    Optional<MedicionClimatica> obtenerUltima();    
}
