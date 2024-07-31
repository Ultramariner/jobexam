package isida.by.jobexam.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BreedDto {

    private String name;
    private List<String> subBreeds;

}
