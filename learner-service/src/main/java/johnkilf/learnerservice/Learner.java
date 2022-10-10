package johnkilf.learnerservice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Learner {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    public Learner(String name) {
        this.name = name;
    }
}
