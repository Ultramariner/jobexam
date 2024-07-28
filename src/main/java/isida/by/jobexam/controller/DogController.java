package isida.by.jobexam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.service.DogService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping(path = "/vue/dogs")
public class DogController {

    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping("/breeds")
    public ResponseEntity<Breed> getBreeds() {
        return ResponseEntity.ok(dogService.getDogBreeds());
    }

    @GetMapping()
    public ResponseEntity<String> getImageByBreed(@RequestParam(value = "breed") String breed) throws JsonProcessingException {
        return ResponseEntity.ok(dogService.getDogImageByBreed(breed));
    }

    //todo dto
    @PostMapping
    @ResponseBody
    public void save(@RequestBody Dog dog) throws IOException {
        dogService.saveToDatabase(dog);
    }
}