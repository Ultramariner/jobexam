package isida.by.jobexam.service;

import isida.by.jobexam.model.Breed;
import isida.by.jobexam.repository.BreedRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
@Transactional
public class BreedService {

    private final BreedRepository breedRepository;

    //todo upsert
    public void saveToDatabase(Map<String, List<String>> breedsMap) {
        List<Breed> breedsList = new ArrayList<>();
        breedsMap.forEach((breedName, subBreeds) -> {
            Breed breed = new Breed();
            breed.setName(breedName);
            breed.setSubBreeds(subBreeds);
            breedsList.add(breed);
        });
        breedRepository.saveAll(breedsList);
    }

}
