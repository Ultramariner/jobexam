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
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/vue/dogs")
public class DogController {

    private final DogService dogService;
    private final BreedService breedService;

    @GetMapping("/breeds")
    public void getAllBreeds() throws JsonProcessingException {
        dogService.getAllBreeds();
    }

    @GetMapping
    public ResponseEntity<List<Breed>> findAllBreeds() throws JsonProcessingException {
        return ResponseEntity.ok(breedService.findAllBreeds());
    }

    @PostMapping
    public ResponseEntity<String> getImageByBreed(@RequestParam(value = "breed") String breed) throws JsonProcessingException {
        return ResponseEntity.ok(dogService.getDogImageByBreed(breed));
    }

    //todo dto
    @PutMapping
    @ResponseBody
    public void save(@RequestBody Dog dog) throws IOException {
        dogService.saveToDatabase(dog);
    }
}