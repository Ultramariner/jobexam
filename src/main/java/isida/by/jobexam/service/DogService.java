package isida.by.jobexam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import isida.by.jobexam.dto.DogDto;

import java.io.IOException;

public interface DogService {
    String getDogImageByBreed(String breed) throws JsonProcessingException;

    void saveToDatabase(DogDto dogDto) throws IOException;
}
