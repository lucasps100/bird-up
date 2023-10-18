package shepherd.birdup.data;

import shepherd.birdup.models.Species;

import java.util.List;

public interface SpeciesRepository {
    List<Species> findAll();

    List<Species> findByPartialName(String partialName);

    Species findByShortName(String speciesShortName);
}
