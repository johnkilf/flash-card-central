package johnkilf.cardservice.deck;

import johnkilf.cardservice.card.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deckidgen")
    private long id;

    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "deck")
    private List<Card> cards;

    public Deck(String name) {
        this.name = name;
    }
}
