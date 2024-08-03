package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.mapper.DogMapper;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.repository.DogRepository;
import isida.by.jobexam.utility.ObjectMapperProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class DogService {

    private final DogRepository dogRepository;
    private final BreedService breedService;
    private final DogApiConnectionClient dogApiConnectionClient;
    private final FileStorageService fileStorageService;
    private final DogMapper dogMapper;
    //todo static final in utility
    @Value("${server.storage}")
    private String storage;

    /**
     * Получает ссылку на случайное избражение определённой породы
     * @param breed Уникальное имя породы
     * @return Ссылка на изображение
     */
    //todo tests
    public String getDogImageByBreed(String breed) throws JsonProcessingException {
        String response = dogApiConnectionClient.getBreedImage(breed);
        JsonNode root = ObjectMapperProvider.get().readTree(response);
        return root.get("message").textValue();
    }

    /**
     * Получает с UI информацию о собаке, сохраняет изображение в локальное файловое хранилище, сохраняет информацию о
     * собаке в базу данных
     * @param dogDto Данные о собаке
     */
    //todo refacor mapper
    public void saveToDatabase(DogDto dogDto) throws IOException {
        String imgLink = dogDto.getLink();
        String storageLocation = storage + "\\" + imgLink.substring(imgLink.lastIndexOf("/") + 1);
        Dog dog = dogMapper.mapToEntity(dogDto, storageLocation, breedService.findByName(dogDto.getBreed()));
        fileStorageService.saveToStorage(imgLink, storageLocation);
        dogRepository.save(dog);
    }

}
