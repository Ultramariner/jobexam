package isida.by.jobexam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name="breed_id")
    private Breed breed;
    private String comment;
    private String link;
    private String path;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return name.equals(dog.name) && breed.equals(dog.breed) && link.equals(dog.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, breed, link);
    }
}