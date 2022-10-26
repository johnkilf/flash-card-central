package johnkilf.cardservice.deck;

import johnkilf.cardservice.card.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(exposedHeaders = "Location")
public class DeckController {

    @Autowired
    DeckRespository deckRespository;

    @GetMapping(value = "/decks")
//    @CrossOrigin(exposedHeaders = "Location")
    public List<Deck> queryDecks(@RequestParam(required = false) String id){
        if(id == null){
            return deckRespository.findAll();
        } else {
            final List<Long> deckIds = Arrays.stream(id.split(",")).map(idString -> Long.parseLong(idString)).collect(
                    Collectors.toList()
            );
            return deckRespository.findDecksByIdList(deckIds);
        }
    }

    @GetMapping(value="/decks/{id}")
    public ResponseEntity<Deck> getDeck(@PathVariable long id){
        return ResponseEntity.of(deckRespository.findById(id));
    }

    @PostMapping(value="/decks")
    public ResponseEntity createDeck(@RequestBody @Valid Deck deck){
        Deck result = deckRespository.save(deck);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value="/decks/{id}")
    public ResponseEntity deleteDeck(@PathVariable long id){
        deckRespository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
