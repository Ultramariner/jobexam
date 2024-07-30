package isida.by.jobexam.repository;

import isida.by.jobexam.model.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Integer> {

    Breed findByName(String name);

}
