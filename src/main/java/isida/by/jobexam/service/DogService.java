package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.dto.DogDto;

import java.io.IOException;

public interface DogService {
    //todo tests
    String getDogImageByBreed(String breed) throws JsonProcessingException;

    //todo refactor mapper
    void saveToDatabase(DogDto dogDto) throws IOException;
}
