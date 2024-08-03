package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.model.Breed;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BreedService {

    Breed findByName(String name);

    void getAllBreeds() throws JsonProcessingException;

    Map<String, String> getBreedsLocalization(String lang) throws IOException;

    void saveToDatabase(Map<String, List<String>> breedsMap);

    List<BreedDto> findAllBreeds();

}
