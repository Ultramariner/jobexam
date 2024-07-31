package isida.by.jobexam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "breeds")
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    @ElementCollection
    @CollectionTable(name = "sub_breeds", joinColumns = @JoinColumn(name = "breed_id"))
    @Column(name = "sub_breed")
    private List<String> subBreeds;
//    @ManyToMany
//    @JoinTable(name = "sub_breeds",
//        joinColumns = @JoinColumn(name = "breed_id"),
//        inverseJoinColumns = @JoinColumn(name = "sub_breed_id"))
//    private List<Breed> subBreeds;


    public Breed(String name) {
        this.name = name;
    }
}