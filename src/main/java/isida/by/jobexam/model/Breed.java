package isida.by.jobexam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
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

}