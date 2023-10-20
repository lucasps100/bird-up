package shepherd.birdup.data;

import shepherd.birdup.models.Species;

import java.util.List;

public interface SpeciesRepository {
    List<Species> findAll();

    List<Species> findByPartialName(String partialName);
    Species findById(int speciesId);
    Species findByShortName(String speciesShortName);
}
