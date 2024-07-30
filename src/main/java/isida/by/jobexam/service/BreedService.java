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

    public void saveToDatabase(Map<String, List<String>> breedsMap) {
//        List<Breed> breedsList = new ArrayList<>();
//        breedsMap.forEach((breedName, subBreeds) -> {
//            Breed breed = new Breed();
//            breed.setName(breedName);
//            breed.setSubBreeds(subBreeds);
//            breedsList.add(breed);
//        });
//        breedRepository.saveAll(breedsList);

        for (Map.Entry<String, List<String>> entry : breedsMap.entrySet()) {
            String name = entry.getKey();
            List<String> subBreeds = entry.getValue();
            Breed breed = breedRepository.findByName(name);
            if (breed == null) {
                breed = new Breed(name);
            }
            breed.setSubBreeds(subBreeds);
            breedRepository.save(breed);
        }
    }

    public List<Breed> findAllBreeds() {
        return breedRepository.findAll();
    }

}
