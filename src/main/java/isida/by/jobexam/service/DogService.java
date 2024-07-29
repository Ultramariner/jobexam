package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
//import isida.by.jobexam.repository.BreedRepository;
import isida.by.jobexam.repository.DogRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class DogService {

    private final DogRepository dogRepository;
    private final BreedService breedService;
    private final RestTemplate restTemplate;
    @Value("${server.storage}")
    private String storage;

//    public List<String> getDogBreeds() {
//        Map<String, List<String>> breeds = (Map<String, List<String>>) restTemplate.getForEntity("https://dog.ceo/api/breeds/list/all", Map.class).getBody().get("message");
//        return new ArrayList<>(breeds.keySet());
//    }

    public List<String> getAllBreeds() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = restTemplate.getForEntity("https://dog.ceo/api/breeds/list/all", String.class).getBody();
        JsonNode jsonNode = objectMapper.readTree(response);
        JsonNode messageNode = jsonNode.get("message");
        //        return objectMapper.readValue(breeds, new TypeReference<List<Breed>>(){});
        Map<String, List<String>> breeds = objectMapper.convertValue(messageNode, new TypeReference<Map<String, List<String>>>() {});
        breedService.saveToDatabase(breeds);
        return new ArrayList<>(breeds.keySet());
    }

    public String getDogImageByBreed(String breed) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity("https://dog.ceo/api/breed/" + breed + "/images/random", String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return root.get("message").textValue();
    }

    //todo @Cached
    public void saveToDatabase(Dog dog) throws IOException {
        String imgLink = dog.getLink();
        String storageLocation = storage + imgLink.substring(imgLink.lastIndexOf("/"));
        dog.setPath(storageLocation);
        saveToStorage(imgLink, storageLocation);
        dogRepository.save(dog);
    }

    public void saveToStorage(String imgLink, String storageLocation) throws IOException {
        URL url = new URL(imgLink);
        InputStream inputStream = url.openStream();
        OutputStream fileOutputStream = new FileOutputStream(storageLocation);
        int ch;
        while ((ch = inputStream.read()) != -1) {
            fileOutputStream.write(ch);
        }
        inputStream.close();
        fileOutputStream.close();
    }
}
