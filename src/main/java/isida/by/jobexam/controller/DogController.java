package isida.by.jobexam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.service.BreedService;
import isida.by.jobexam.service.DogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/vue/dogs")
public class DogController {

    private final DogService dogService;
    private final BreedService breedService;

    @GetMapping("/breeds")
    public ResponseEntity<Void> getAllBreeds() throws JsonProcessingException {
        dogService.getAllBreeds();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Breed>> findAllBreeds() {
        return ResponseEntity.ok(breedService.findAllBreeds());
    }

    @GetMapping("/{breed}")
    public ResponseEntity<String> getImageByBreed(@PathVariable String breed) throws JsonProcessingException {
        return ResponseEntity.ok(dogService.getDogImageByBreed(breed));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> save(@RequestBody Dog dog) throws IOException {
        dogService.saveToDatabase(dog);
        return ResponseEntity.ok().build();
    }
}