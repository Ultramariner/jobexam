package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.repository.BreedRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
@Transactional
public class BreedService {

    private final BreedRepository breedRepository;
    private final RestTemplate restTemplate;

    public Breed findByName(String name) {
        return breedRepository.findByName(name);
    }

    public void getAllBreeds() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String response = restTemplate.getForEntity("https://dog.ceo/api/breeds/list/all", String.class).getBody();
        JsonNode jsonNode = objectMapper.readTree(response);
        JsonNode messageNode = jsonNode.get("message");
        //        return objectMapper.readValue(breeds, new TypeReference<List<Breed>>(){});
        Map<String, List<String>> breeds = objectMapper.convertValue(messageNode, new TypeReference<>() {
        });
        saveToDatabase(breeds);
    }

    public Map<String, String> getBreedsLocalization(String lang) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, String>> typeReference = new TypeReference<>() {};
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DogsRussianNames.json");
        return objectMapper.readValue(inputStream, typeReference);
    }

    public void saveToDatabase(Map<String, List<String>> breedsMap) {
        for (Map.Entry<String, List<String>> entry : breedsMap.entrySet()) {
            String name = entry.getKey();
            List<String> subBreeds = entry.getValue();
            Breed breed = breedRepository.findByName(name);
            if (breed == null) {
                breed = new Breed(name);
            }
            breed.setSubBreeds(subBreeds);
            breedRepository.save(breed);
        }
    }

    public List<BreedDto> findAllBreeds() {
        List<Breed> breeds = breedRepository.findAll();
        List<BreedDto> breedsDto = new ArrayList<>();
        for (Breed breed : breeds) {
            BreedDto dto = new BreedDto();
            dto.setName(breed.getName());
            breedsDto.add(dto);
        }
        return breedsDto;
    }

}
