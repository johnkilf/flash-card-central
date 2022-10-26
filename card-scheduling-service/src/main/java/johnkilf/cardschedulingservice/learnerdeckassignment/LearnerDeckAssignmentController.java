package johnkilf.cardschedulingservice.learnerdeckassignment;

import feign.FeignException;
import johnkilf.cardschedulingservice.remote.cardservice.CardServiceProxy;
import johnkilf.cardschedulingservice.remote.cardservice.DeckDTO;
import johnkilf.cardschedulingservice.remote.learnerservice.LearnerDTO;
import johnkilf.cardschedulingservice.remote.learnerservice.LearnerServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "Location")
public class LearnerDeckAssignmentController {

    Logger logger = LoggerFactory.getLogger(LearnerDeckAssignmentController.class);
    @Autowired
    private LearnerDeckAssignmentRepository learnerDeckAssignmentRepository;

    @Autowired
    private LearnerServiceProxy learnerServiceProxy;

    @Autowired
    private CardServiceProxy cardServiceProxy;


    @GetMapping(value = "/learners/{learnerId}/decks")
    public List<LearnerDeckAssignment> getAssignedDecks (@PathVariable long learnerId){

        final List<LearnerDeckAssignment> assignedDecks = learnerDeckAssignmentRepository.findByLearnerId(learnerId);

        return assignedDecks;
    }

    @PostMapping(value = "/learners/{learnerId}/decks")
    public ResponseEntity assignDeck(@PathVariable long learnerId, @RequestBody LearnerDeckAssignment assignment){

        // Validate Learner exists
        final LearnerDTO learner;
        try {
            learner = learnerServiceProxy.getLearner(learnerId);;
        } catch (FeignException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
        assignment.setLearnerId(learner.getId());

        // Validate Deck exists
        final DeckDTO deck;
        try {
            deck = cardServiceProxy.getDeck(assignment.getDeckId());
        } catch (FeignException.NotFound e) {
            return ResponseEntity.notFound().build();
        }

        final LearnerDeckAssignment result;
        try {
            result = learnerDeckAssignmentRepository.save(assignment);
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Deck is already assigned");
        }

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();

        return  ResponseEntity.created(location).build();

    }
}
