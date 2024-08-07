package isida.by.jobexam.repository;

import isida.by.jobexam.model.Breed;
import isida.by.jobexam.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {

    Dog findByNameAndBreedAndLink(String name, Breed breed, String link);

}