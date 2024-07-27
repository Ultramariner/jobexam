package isida.by.jobexam.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
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
    private String comment;
    private String path;
    private String link;

    public Dog(String name, String comment, String link) {
        this.name = name;
        this.comment = comment;
        this.link = link;
    }

    public Dog(String name, String comment, String path, String link) {
        this.name = name;
        this.comment = comment;
        this.path = path;
        this.link = link;
    }
}