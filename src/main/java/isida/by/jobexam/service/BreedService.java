package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.mapper.BreedMapper;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.repository.BreedRepository;
import isida.by.jobexam.utility.ObjectMapperProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class BreedService {

    private final BreedRepository breedRepository;
    private final DogApiConnectionClient dogApiConnectionClient;
    private final BreedMapper breedMapper;

    /**
     * Ищет запись о собаке в базе данных по имени
     * @param name Имя собаки
     * @return Объект Dog, содержащий информацию о собаке
     */
    public Breed findByName(String name) {
        return breedRepository.findByName(name);
    }

    /**
     * Отправляет запрос на сторонний api, получает ответ в виде json со списком всех пород, приводит полученные
     * данные к формату Map, сохраняет или обновляет записи о всех породах собак в базе данных
     */
    public void getAllBreeds() throws JsonProcessingException {
        String response;
        response = dogApiConnectionClient.getAllBreeds();
        //todo (4) separate class for parsing
        JsonNode jsonNode = ObjectMapperProvider.get().readTree(response);
        JsonNode messageNode = jsonNode.get("message");
        Map<String, List<String>> breeds = ObjectMapperProvider.get().convertValue(messageNode, new TypeReference<>() {
        });
        saveToDatabase(breeds);
    }

    /**
     * Считывает из файла на сервере информацию о локализованных названиях пород и возвращает её в формате Map
     * @param lang Заглушка для варианта с несколькими локализациями
     * @return Локализованные названия пород в формате Map
     */
    public Map<String, String> getBreedsLocalization(String lang) throws IOException {
        TypeReference<Map<String, String>> typeReference = new TypeReference<>() {};
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DogsRussianNames.json");
        return ObjectMapperProvider.get().readValue(inputStream, typeReference);
    }

    /**
     * Cохраняет или обновляет полученные записи о всех породах собак в базе данных
     * @param breedsMap Записи о всех породах собак
     */
    public void saveToDatabase(Map<String, List<String>> breedsMap) {
        List<Breed> breedsList = new ArrayList<>();
        breedsMap.forEach((breedName, subBreeds) -> {
            Breed breed = breedRepository.findByName(breedName);
            if (breed == null) {
                breed = new Breed(breedName);
            }
            breed.setName(breedName);
            breed.setSubBreeds(subBreeds);
            breedsList.add(breed);
        });
        breedRepository.saveAll(breedsList);
    }

    /**
     * Получает из базы данных список всех пород и возвращает его
     * @return Список всех хранящихся в базе данных пород
     */
    public List<BreedDto> findAllBreeds() {
        List<Breed> breeds = breedRepository.findAll();
        return breedMapper.mapToDto(breeds);
    }

}
