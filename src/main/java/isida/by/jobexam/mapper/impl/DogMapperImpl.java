package isida.by.jobexam.mapper.impl;

import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.mapper.DogMapper;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
import org.springframework.stereotype.Component;

@Component
public class DogMapperImpl implements DogMapper {
    @Override
    public Dog mapToEntity(DogDto dto, String path, Breed breed) {
        Dog dog = new Dog();
        dog.setName(dto.getName());
        dog.setComment(dto.getComment());
        dog.setLink(dto.getLink());
        dog.setPath(path);
        dog.setBreed(breed);
        return dog;
    }

    @Override
    public DogDto mapToDto(Dog model) {
        DogDto dto = new DogDto();
        dto.setName(model.getName());
        dto.setComment(model.getComment());
        dto.setLink(model.getLink());
        dto.setBreed(model.getBreed().getName());
        return dto;
    }
}
