package isida.by.jobexam.mapper.impl;

import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.mapper.BreedMapper;
import isida.by.jobexam.model.Breed;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BreedMapperImpl implements BreedMapper {

    @Override
    public Breed mapToEntity(BreedDto dto) {
        Breed breed = new Breed();
        breed.setName(dto.getName());
        return breed;
    }

    @Override
    public BreedDto mapToDto(Breed model) {
        BreedDto breedDto = new BreedDto();
        breedDto.setName(model.getName());
        return breedDto;
    }

    @Override
    public List<Breed> mapToEntity(List<BreedDto> dtoList) {
        List<Breed> entityList = new ArrayList<>();
        for (BreedDto dto : dtoList) {
            entityList.add(mapToEntity(dto));
        }
        return entityList;
    }

    @Override
    public List<BreedDto> mapToDto(List<Breed> modelList) {
        List<BreedDto> dtoList = new ArrayList<>();
        for (Breed breed : modelList) {
            dtoList.add(mapToDto(breed));
        }
        return dtoList;
    }
}
