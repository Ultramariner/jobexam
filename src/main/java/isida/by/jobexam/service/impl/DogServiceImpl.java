package isida.by.jobexam.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.mapper.DogMapper;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.repository.DogRepository;
import isida.by.jobexam.service.BreedService;
import isida.by.jobexam.service.DogApiClient;
import isida.by.jobexam.service.DogService;
import isida.by.jobexam.service.FileStorageService;
import isida.by.jobexam.utility.DogJsonParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class DogServiceImpl implements DogService {

    private final DogRepository dogRepository;
    private final BreedService breedService;
    private final DogApiClient dogApiClient;
    private final FileStorageService fileStorageService;
    private final DogMapper dogMapper;
    private final DogJsonParser jsonParser;
    //todo static final in utility?
    @Value("${server.storage}")
    private String storage;

    //todo service tests
    //todo extended logging
    /**
     * Получает ссылку на случайное избражение определённой породы
     * @param breed Уникальное имя породы
     * @return Ссылка на изображение
     */
    @Override
    public String getDogImageByBreed(String breed) throws JsonProcessingException {
        String response = dogApiClient.getBreedImage(breed);
        return jsonParser.parseToString(response);
    }

    /**
     * Получает с UI информацию о собаке, сохраняет изображение в локальное файловое хранилище, сохраняет информацию о
     * собаке в базу данных
     * @param dogDto Данные о собаке
     */
    @Override
    public void saveToDatabase(DogDto dogDto) throws IOException {

        String imgLink = dogDto.getLink();
        String storageLocation = storage + "\\" + imgLink.substring(imgLink.lastIndexOf("/") + 1);
        fileStorageService.saveToStorage(imgLink, storageLocation);
        Dog dog = dogMapper.mapToEntity(dogDto, storageLocation, breedService.findByName(dogDto.getBreed()));
        Dog existingDog = dogRepository.findByNameAndBreedAndLink(dog.getName(), dog.getBreed(), dog.getLink());
        if (existingDog != null) {
            existingDog.setComment(dog.getComment());
            existingDog.setPath(storageLocation);
        } else {
            existingDog = dog;
        }
        dogRepository.save(existingDog);
        log.info("Информация о картинке сохранена");
    }

}
