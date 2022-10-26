package johnkilf.cardschedulingservice.remote.cardservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "card", url = "${CARD_URI:http://localhost}:8100")
public interface CardServiceProxy {

    @GetMapping(value = "/decks/{id}")
    public DeckDTO getDeck(@PathVariable long id);


    @GetMapping(value = "/decks/{deckId}/cards")
    public List<CardDTO> getCards(@PathVariable long deckId);

    @GetMapping(value = "/decks/{deckId}/cards")
    public List<CardDTO> getCards(@PathVariable long deckId, @RequestParam String id);

    @GetMapping(value = "/decks/{deckId}/cards/{cardId}")
    public List<CardDTO> getCard(@PathVariable long deckId, @PathVariable long cardId);
}
