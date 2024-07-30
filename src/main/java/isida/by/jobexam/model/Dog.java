package isida.by.jobexam.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String breed;
    private String comment;
    private String link;
    private String path;

//    public Dog(String name, String breed, String comment, String link) {
//        this.name = name;
//        this.breed = breed;
//        this.comment = comment;
//        this.link = link;
//    }
//
//    public Dog(String name, String breed, String comment, String path, String link) {
//        this.name = name;
//        this.breed = breed;
//        this.comment = comment;
//        this.path = path;
//        this.link = link;
//    }
}