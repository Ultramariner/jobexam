package isida.by.jobexam.service.impl;

import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.mapper.DogMapper;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.repository.DogRepository;
import isida.by.jobexam.service.BreedService;
import isida.by.jobexam.service.DogApiClient;
import isida.by.jobexam.service.FileStorageService;
import isida.by.jobexam.utility.DogJsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DogServiceImplTest {

    @Mock
    private DogRepository dogRepository;
    @Mock
    private BreedService breedService;
    @Mock
    private DogApiClient dogApiClient;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private DogMapper dogMapper;
    @Mock
    private DogJsonParser jsonParser;
    @InjectMocks
    private DogServiceImpl dogService;

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

        ArgumentCaptor<Dog> captor = ArgumentCaptor.forClass(Dog.class);

        when(dogMapper.mapToEntity(any(DogDto.class), anyString(), any()))
                .thenReturn(dog);

        when(dogRepository.findByNameAndBreedAndLink(eq("Buddy"), any(Breed.class), eq("http://example.com/dog.jpg")))
                .thenReturn(dog);

        dogService.saveToDatabase(dogDto);

        verify(dogRepository).save(dog);
        verify(dogRepository).save(captor.capture());
    }
}