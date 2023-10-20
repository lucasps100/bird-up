package shepherd.birdup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import shepherd.birdup.domain.Result;

public class ResultToResponseEntity {
    public static ResponseEntity<?> toResponseEntity(Result result, HttpStatus successStatus) {
        return switch (result.getStatus()) {
            case SUCCESS -> new ResponseEntity<>(result.getPayload(), successStatus);
            case INVALID -> new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
            case NOT_FOUND -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };
    }
}
