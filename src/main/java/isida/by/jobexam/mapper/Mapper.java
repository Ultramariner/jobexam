package isida.by.jobexam.mapper;

import isida.by.jobexam.dto.BreedDto;
import isida.by.jobexam.dto.DogDto;
import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class Mapper {
    public abstract Breed map(BreedDto dto);
    public abstract BreedDto map(Breed model);
    public abstract Dog map(DogDto dto);
}
