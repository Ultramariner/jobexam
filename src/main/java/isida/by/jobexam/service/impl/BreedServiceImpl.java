package isida.by.jobexam.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.mapper.BreedMapper;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.repository.BreedRepository;
import isida.by.jobexam.service.BreedService;
import isida.by.jobexam.utility.BreedJsonParser;
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
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;
    private final DogApiClientImpl dogApiConnectionClient;
    private final BreedMapper breedMapper;
    private final BreedJsonParser jsonParser;
    private static final String LOCALIZATION_JSON_FILE_NAME = "DogsRussianNames.json";

    /**
     * Ищет запись о собаке в базе данных по имени
     * @param name Имя собаки
     * @return Объект Dog, содержащий информацию о собаке
     */
    @Override
    public Breed findByName(String name) {
        return breedRepository.findByName(name);
    }

    /**
     * Отправляет запрос на сторонний api, получает ответ в виде json со списком всех пород, приводит полученные
     * данные к формату Map, сохраняет или обновляет записи о всех породах собак в базе данных
     */
    @Override
    public void getAllBreeds() throws JsonProcessingException {
        String response = dogApiConnectionClient.getAllBreeds();
        saveToDatabase(jsonParser.parseToMap(response));
    }

    /**
     * Считывает из файла на сервере информацию о локализованных названиях пород и возвращает её в формате Map
     * @param lang Заглушка для варианта с несколькими локализациями
     * @return Локализованные названия пород в формате Map
     */
    @Override
    public Map<String, String> getBreedsLocalization(String lang) throws IOException {
        TypeReference<Map<String, String>> typeReference = new TypeReference<>() {};
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(LOCALIZATION_JSON_FILE_NAME);
        return ObjectMapperProvider.get().readValue(inputStream, typeReference);
    }

    /**
     * Сохраняет или обновляет полученные записи о всех породах собак в базе данных
     * @param breedsMap Записи о всех породах собак
     */
    @Override
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
    @Override
    public List<BreedDto> findAllBreeds() {
        List<Breed> breeds = breedRepository.findAll();
        return breedMapper.mapToDto(breeds);
    }

}
