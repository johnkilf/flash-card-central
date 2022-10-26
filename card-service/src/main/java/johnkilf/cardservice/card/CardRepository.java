package johnkilf.cardservice.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByDeckId(Long deckId);

    @Query(value = "SELECT * FROM Card WHERE deck_id = ?1 AND id IN ?2",
            nativeQuery = true)
    List<Card> findCardsByIdList(long deckId, List<Long> cardIds);
}
