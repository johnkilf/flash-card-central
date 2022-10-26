package johnkilf.cardservice.deck;

import johnkilf.cardservice.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeckRespository extends JpaRepository<Deck, Long> {

    @Query(value = "SELECT * FROM Deck WHERE id IN ?1",
            nativeQuery = true)
    List<Deck> findDecksByIdList(List<Long> deckIds);
}
