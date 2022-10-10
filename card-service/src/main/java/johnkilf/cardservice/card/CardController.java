package johnkilf.cardservice.card;

import johnkilf.cardservice.deck.Deck;
import johnkilf.cardservice.deck.DeckRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CardController {

    Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRespository deckRespository;

    @GetMapping(value = "/decks/{deckId}/cards/{cardId}")
    public ResponseEntity<Card> getCard(@PathVariable long deckId, @PathVariable long cardId){
        return ResponseEntity.of(cardRepository.findById(cardId));
    }

    @GetMapping(value = "/decks/{deckId}/cards")
    public List<Card> getCardsInDeck(@PathVariable long deckId){

        logger.info("getting cards for deck " + deckId);
        List<Card> byDeckId = cardRepository.findByDeckId(deckId);
        logger.info("cards gotten " + byDeckId.isEmpty() + " is empty");
        return byDeckId;
    }

    @PostMapping(value = "decks/{deckId}/cards")
    public ResponseEntity createCard(@PathVariable long deckId, @RequestBody @Valid Card card){
        // Find Deck
        logger.error("Finding deck with id " + deckId);
        final Optional<Deck> deck = deckRespository.findById(deckId);
        logger.error("Deck was found? " + !deck.isEmpty());

        if(deck.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        card.setDeck(deck.get());

        final Card result = cardRepository.save(card);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
