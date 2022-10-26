package johnkilf.cardschedulingservice.learnerdeckassignment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "learnerId", "deckId" })})
@NoArgsConstructor
public class LearnerDeckAssignment {

    @Id
    @GeneratedValue
    private long id;

    private long learnerId;

    private long deckId;
}
