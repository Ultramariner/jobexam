package isida.by.jobexam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.service.BreedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@RestController
//todo (8) /vue/ ->/api/?
@RequestMapping("/vue/dogs/breeds")
public class BreedController {

    private final BreedService breedService;

    /**
     * Получает от стороннего api список пород и обновляет в базе данных информацию о всех породах
     */
    @GetMapping
    public ResponseEntity<Void> getAllBreeds() throws JsonProcessingException {
        breedService.getAllBreeds();
        return ResponseEntity.ok().build();
    }

    /**
     * Считывает из файла на сервере локализованные названия пород и возвращает их в формате Map
     * @param lang Заглушка для варианта с несколькими локализациями
     * @return Локализованные названия пород в формате Map
     */
    @GetMapping("/{lang}")
    public ResponseEntity<Map<String, String>> getBreedsLocalization(@PathVariable String lang) throws IOException {
        return ResponseEntity.ok(breedService.getBreedsLocalization(lang));
    }

}
