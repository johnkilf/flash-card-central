package johnkilf.cardservice.deck;

import johnkilf.cardservice.card.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Deck {

    @Id
    @GeneratedValue(generator = "deck_id_seq")
    private long id;

    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "deck")
    private List<Card> cards;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public Deck(String name) {
        this.name = name;
    }
}
