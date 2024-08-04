package isida.by.jobexam.mapper;

import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.model.Breed;

import java.util.List;

public interface BreedMapper {

    Breed mapToEntity(BreedDto dto);
    BreedDto mapToDto(Breed model);
    List<Breed> mapToEntity(List<BreedDto> dto);
    List<BreedDto> mapToDto(List<Breed> model);

}
