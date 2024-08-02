package isida.by.jobexam.mapper;

import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
import isida.by.jobexam.service.BreedService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class MapperImpl extends Mapper  {

    @Override
    public Breed map(BreedDto dto) {
        Breed breed = new Breed();
        breed.setName(dto.getName());
        return breed;
    }

    @Override
    public BreedDto map(Breed model) {
        BreedDto breedDto = new BreedDto();
        breedDto.setName(model.getName());
        return breedDto;
    }

    @Override
    public Dog map(DogDto dto) {
        Dog dog = new Dog();
        dog.setName(dto.getName());
        dog.setComment(dto.getComment());
        dog.setLink(dto.getLink());
        return dog;
    }
}
