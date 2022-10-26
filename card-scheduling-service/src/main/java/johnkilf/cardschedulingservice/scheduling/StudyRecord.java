package johnkilf.cardschedulingservice.scheduling;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "learnedId", "cardId" })})
@Data
@NoArgsConstructor
public class StudyRecord {

    @Id
    @GeneratedValue(generator = "studyrecordidgen")
    private long id;

    private long learnerId;

    private long cardId;

    private long deckId;

    // 0 - for never seen cards
    // 1 - for cards correct at least once in a row
    // 2 - for cards correct at least twice in a row
    // getting a card wrong at learning stage 0 or 1 sets learning stage to 0
    // getting a card right increases the learning stage up to a maximum of 2
    // once learning stage reaches 2 it cannot be reset
    private int learningStage;

    private LocalDateTime lastRevision;

    private LocalDateTime nextDue;

    private Duration currentInterval;

    private double ease;


    // new cards - get cards from deck where card id > biggest card id in study record for user and deck


}
