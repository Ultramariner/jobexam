package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.repository.DogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static isida.by.jobexam.config.utility.Utility.objectMapper;

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

    //todo tests
    public String getDogImageByBreed(String breed) throws JsonProcessingException {
        String response = dogApiConnectionClient.getBreedImage(breed);
        JsonNode root = objectMapper.readTree(response);
        return root.get("message").textValue();
    }

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
