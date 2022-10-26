package johnkilf.learnerservice.learnerdeck;

import com.fasterxml.jackson.annotation.JsonIgnore;
import johnkilf.learnerservice.learner.Learner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearnerDeck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "learnerdeckidgen")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Learner learner;

    @NotEmpty
    private String deckId;

}
