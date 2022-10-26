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
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(exposedHeaders = "Location")
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

    // Can request all cards in a deck, or can request specific card ids
    @GetMapping(value = "/decks/{deckId}/cards")
    public List<Card> queryCards(@PathVariable long deckId, @RequestParam(required = false) String id){
        if(id == null){
            return cardRepository.findByDeckId(deckId);
        } else {
            final List<Long> cardIds = Arrays.stream(id.split(",")).map(idString -> Long.parseLong(idString)).collect(
                    Collectors.toList()
            );

            return cardRepository.findCardsByIdList(deckId, cardIds).stream()
                    .filter(card -> (card.getDeck().getId() == deckId))
                    .collect(Collectors.toList());
        }
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
