package isida.by.jobexam.service;

import isida.by.jobexam.dto.BreedDto;
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
//        breedsMap.forEach((breedName, subBreeds) -> {
//            Breed breed = breedRepository.findByName(breedName);
//            if (breed == null) {
//                breed = new Breed(breedName);
//            }
//            breedRepository.save(breed);;
//        });
//        breedsMap.forEach((breedName, subBreedsStr) -> {
//            Breed breed = breedRepository.findByName(breedName);
//            List<Breed> subBreeds = new ArrayList<>();
//            for (String name : subBreedsStr) {
//                Breed subBreed = breedRepository.findByName(name);
//                if (breed != null) {
//                    subBreeds.add(subBreed);
//                }
//            }
//            assert breed != null;
//            breed.setSubBreeds(subBreeds);
//            breedRepository.save(breed);;
//        });

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

    public List<BreedDto> findAllBreeds() {
        List<Breed> breeds = breedRepository.findAll();
        List<BreedDto> breedsDto = new ArrayList<>();
        for (Breed breed : breeds) {
            BreedDto dto = new BreedDto();
            dto.setName(breed.getName());
            breedsDto.add(dto);
        }
        return breedsDto;
    }

}
