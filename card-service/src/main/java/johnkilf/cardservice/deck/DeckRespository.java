package johnkilf.cardservice.deck;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRespository extends JpaRepository<Deck, Long> {
}
