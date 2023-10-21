package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.SpeciesRepository;
import shepherd.birdup.models.Species;

import java.util.List;

@Service
public class SpeciesService {

    private final SpeciesRepository repository;

    public SpeciesService(SpeciesRepository repository) {
        this.repository = repository;
    }

    public List<Species> findAll() {
        return repository.findAll();
    }

    public List<Species> findByPartialName(String partialName) {
        return repository.findByPartialName(partialName);
    }

    public Species findById(int speciesId) {
        return repository.findById(speciesId);
    }

    public Species findByShortName(String shortName) {
        return repository.findByShortName(shortName);
    }
}
