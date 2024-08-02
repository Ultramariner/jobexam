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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static isida.by.jobexam.config.utility.Utility.objectMapper;

@RequiredArgsConstructor
@Service
@Transactional
public class BreedService {

    private final BreedRepository breedRepository;
    private final DogApiConnectionClient dogApiConnectionClient;

    public Breed findByName(String name) {
        return breedRepository.findByName(name);
    }

    public void getAllBreeds() throws JsonProcessingException {
        String response;
        response = dogApiConnectionClient.getAllBreeds();
        //todo (4) separate class for parsing
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
//        for (Map.Entry<String, List<String>> entry : breedsMap.entrySet()) {
//            String name = entry.getKey();
//            List<String> subBreeds = entry.getValue();
//            Breed breed = breedRepository.findByName(name);
//            if (breed == null) {
//                breed = new Breed(name);
//            }
//            breed.setSubBreeds(subBreeds);
//            breedRepository.save(breed);
//        }
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

    public List<BreedDto> findAllBreeds() {
        List<Breed> breeds = breedRepository.findAll();
        //todo (9) mapper class or mapStruct
        List<BreedDto> breedsDto = new ArrayList<>();
        for (Breed breed : breeds) {
            BreedDto dto = new BreedDto();
            dto.setName(breed.getName());
            breedsDto.add(dto);
        }
        return breedsDto;
    }

}
