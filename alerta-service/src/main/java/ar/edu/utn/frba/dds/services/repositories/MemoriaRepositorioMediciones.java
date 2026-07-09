package ar.edu.utn.frba.dds.services.repositories;

import ar.edu.utn.frba.dds.domain.MedicionClimatica;
import ar.edu.utn.frba.dds.domain.RepositorioMediciones;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repositoryl;

@Repository
public class MemoriaRepositorioMediciones implements RepositorioMediciones {
    private final List<MedicionClimatica> mediciones = new ArrayList<>();

    @Override
    public void guardar(MedicionClimatica medicion) {
        mediciones.add(medicion);
    }

    @Override
    public Optional<MedicionClimatica> obetenerUltima() {
        if (mediciones.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mediciones.get(mediciones.size() - 1));
    }

    @Override
    public List<MedicionClimatica> obtenerTodas() {
        return new ArrayList<>(mediciones);
    }
}
