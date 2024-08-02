package isida.by.jobexam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "breeds")
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(unique = true)
    private String name;
    @ElementCollection
    @CollectionTable(name = "sub_breeds", joinColumns = @JoinColumn(name = "breed_id"))
    @Column(name = "sub_breed")
    private List<String> subBreeds;

    public Breed(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        return name.equals(breed.name) && Objects.equals(subBreeds, breed.subBreeds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subBreeds);
    }
}