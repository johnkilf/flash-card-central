package johnkilf.learnerservice.learner;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Learner {
    @Id
    @GeneratedValue(generator = "learner_id_seq")
    private long id;
    private String name;

    public Learner(String name) {
        this.name = name;
    }
}
