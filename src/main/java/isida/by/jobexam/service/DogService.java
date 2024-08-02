package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.repository.DogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static isida.by.jobexam.utility.Utility.objectMapper;

@RequiredArgsConstructor
@Service
@Transactional
public class DogService {

    private final DogRepository dogRepository;
    private final BreedService breedService;
    private final DogApiConnectionClient dogApiConnectionClient;
    private final FileStorageService fileStorageService;
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
        JsonNode root = objectMapper.readTree(response);
        return root.get("message").textValue();
    }

    /**
     * Получает с UI информацию о собаке, сохраняет изображение в локальное файловое хранилище, сохраняет информацию о
     * собаке в базу данных
     * @param dogDto Данные о собаке
     */
    public void saveToDatabase(DogDto dogDto) throws IOException {
        Dog dog = new Dog();
        dog.setName(dogDto.getName());
        dog.setComment(dogDto.getComment());
        String imgLink = dogDto.getLink();
        dog.setLink(imgLink);
        String storageLocation = storage + "\\" + imgLink.substring(imgLink.lastIndexOf("/") + 1);
        dog.setPath(storageLocation);
        fileStorageService.saveToStorage(imgLink, storageLocation);
        dog.setBreed(breedService.findByName(dogDto.getBreed()));
        dogRepository.save(dog);
    }

}
