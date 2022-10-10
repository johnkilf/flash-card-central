package johnkilf.cardservice.deck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DeckController {

    @Autowired
    DeckRespository deckRespository;

    @GetMapping(value="/decks")
    public List<Deck> getDecks(){
        return deckRespository.findAll();
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
