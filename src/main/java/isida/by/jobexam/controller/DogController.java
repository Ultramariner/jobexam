package isida.by.jobexam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.service.impl.BreedServiceImpl;
import isida.by.jobexam.service.impl.DogServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

// todo (8) /vue/ ->/api/?
@AllArgsConstructor
@RestController
@RequestMapping("/vue/dogs")
public class DogController {

    private final DogServiceImpl dogService;
    private final BreedServiceImpl breedService;

    //todo try-catch
    //todo comment methods
    //todo @ApiResponces
    //todo interfaces

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