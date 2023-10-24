package shepherd.birdup.controllers;

import org.springframework.web.bind.annotation.*;
import shepherd.birdup.domain.StateService;
import shepherd.birdup.models.State;

import java.util.List;

@RestController
@RequestMapping("/api/birdup/state")
public class StateController {

    private final StateService service;

    public StateController(StateService service) {
        this.service = service;
    }
    @GetMapping
    public List<State> findAll() {
        return service.findAll();
    }

    @GetMapping("/{stateId}")
    public State findById(@PathVariable int stateId) {
        return service.findByStateId(stateId);
    }

//    @GetMapping
//    public State findByStateAbbrv(@RequestParam String stateAbbrv) {
//        return service.findByStateAbbrv(stateAbbrv);
//    }
}
