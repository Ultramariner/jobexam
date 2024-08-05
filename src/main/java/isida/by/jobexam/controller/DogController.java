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

@AllArgsConstructor
@RestController
// todo /vue -> /api?
@RequestMapping("/vue/dogs")
public class DogController {

    private final DogService dogService;
    private final BreedService breedService;

    //todo try-catch & exceptions
    //todo @ApiResponses
    /**
     * Возвращает список всех пород, хранящихся в базе данных
     * @return Список всех пород, хранящихся в базе данных
     */
    @GetMapping
    public ResponseEntity<List<BreedDto>> findAllBreeds() {
        return ResponseEntity.ok(breedService.findAllBreeds());
    }

    /**
     * Запрашивает у стороннего api ссылку на случайную картинку определённой породы и возвращает её
     * @param breed Уникальное имя породы
     * @return Ссылка на изображение
     */
    @GetMapping("/{breed}")
    public ResponseEntity<String> getImageByBreed(@PathVariable String breed) throws JsonProcessingException {
        return ResponseEntity.ok(dogService.getDogImageByBreed(breed));
    }

    /**
     * Получет с UI информацию о собаке и сохраняет её в базу данных, а картинку сохраняет в
     * локальное файловое хранилище
     * @param dog Информация о собаке
     */
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody DogDto dog) throws IOException {
        dogService.saveToDatabase(dog);
        return ResponseEntity.ok().build();
    }
}