package shepherd.birdup.controllers;

import org.springframework.web.bind.annotation.*;
import shepherd.birdup.domain.SpeciesService;
import shepherd.birdup.models.Species;

import java.util.List;

@RestController
@RequestMapping("/api/birdup/species")
public class SpeciesController {

    private final SpeciesService service;

    public SpeciesController(SpeciesService service) {
        this.service = service;
    }

    @GetMapping
    public List<Species> findAll(@RequestParam(value = "partialName",required = false) String partialName) {
        if(partialName == null) {
            return service.findAll();
        }
        return service.findByPartialName(partialName);
    }

    @GetMapping("/{speciesId}")
    public Species findById(@PathVariable int speciesId) {
        return service.findById(speciesId);
    }

//    @GetMapping
//    public List<Species> findByPartialName(@RequestParam String partialName) {
//        return service.findByPartialName(partialName);
//    }

//    @GetMapping
//    public Species findByName(@RequestParam String speciesName) {
//        return service.findByShortName(speciesName);
//    }
}
