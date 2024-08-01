package isida.by.jobexam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.service.BreedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/vue/dogs/breeds")
public class BreedController {

    private final BreedService breedService;

    @GetMapping
    public ResponseEntity<Void> getAllBreeds() throws JsonProcessingException {
        breedService.getAllBreeds();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{lang}")
    public ResponseEntity<Map<String, String>> getBreedsLocalization(@PathVariable String lang) throws IOException {
        return ResponseEntity.ok(breedService.getBreedsLocalization(lang));
    }

}
