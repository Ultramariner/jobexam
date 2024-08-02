package isida.by.jobexam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.service.BreedService;
import isida.by.jobexam.service.DogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

// todo (8) why /vue/? ->/api/
@AllArgsConstructor
@RestController
@RequestMapping("/vue/dogs")
public class DogController {

    private final DogService dogService;
    private final BreedService breedService;

    //todo try-catch
    //todo comment methods
    //todo @ApiResponces
    @GetMapping
    public ResponseEntity<List<BreedDto>> findAllBreeds() {
        return ResponseEntity.ok(breedService.findAllBreeds());
    }

    @GetMapping("/{breed}")
    public ResponseEntity<String> getImageByBreed(@PathVariable String breed) throws JsonProcessingException {
        return ResponseEntity.ok(dogService.getDogImageByBreed(breed));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody DogDto dog) throws IOException {
        dogService.saveToDatabase(dog);
        return ResponseEntity.ok().build();
    }
}