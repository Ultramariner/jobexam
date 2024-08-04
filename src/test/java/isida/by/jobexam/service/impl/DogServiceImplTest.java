package isida.by.jobexam.service.impl;

import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.mapper.DogMapper;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.repository.DogRepository;
import isida.by.jobexam.service.BreedService;
import isida.by.jobexam.service.DogApiClient;
import isida.by.jobexam.service.DogService;
import isida.by.jobexam.service.FileStorageService;
import isida.by.jobexam.utility.DogJsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class DogServiceImplTest {

    @Test
    void saveToDatabaseIfExists() throws IOException {

        DogDto dogDto = new DogDto();
        dogDto.setName("Buddy");
        dogDto.setComment("Comment");
        dogDto.setLink("http://example.com/dog.jpg");
        dogDto.setBreed("Golden Retriever");

        Breed breed = new Breed("Golden Retriever");

        Dog dog = new Dog();
        dog.setName("Buddy");
        dog.setBreed(breed);
        dog.setLink("http://example.com/dog.jpg");

        DogRepository dogRepository = Mockito.mock(DogRepository.class);
        BreedService breedService = Mockito.mock(BreedService.class);
        DogApiClient dogApiClient = Mockito.mock(DogApiClient.class);
        FileStorageService fileStorageService = Mockito.mock(FileStorageService.class);
        DogMapper dogMapper = Mockito.mock(isida.by.jobexam.mapper.DogMapper.class);
        DogJsonParser jsonParser = Mockito.mock(DogJsonParser.class);

        DogService dogService = new DogServiceImpl(dogRepository,breedService,dogApiClient,fileStorageService,dogMapper,jsonParser);

        when(dogMapper.mapToEntity(any(DogDto.class), anyString(), any()))
                .thenReturn(dog);

        when(dogRepository.findByNameAndBreedAndLink(eq("Buddy"), any(Breed.class), eq("http://example.com/dog.jpg")))
                .thenReturn(dog);

        dogService.saveToDatabase(dogDto);

    }
}