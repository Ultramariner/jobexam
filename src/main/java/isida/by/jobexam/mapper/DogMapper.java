package isida.by.jobexam.mapper;

import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;

public interface DogMapper {

    Dog mapToEntity(DogDto dto, String path, Breed breed);
    DogDto mapToDto(Dog model);
}
