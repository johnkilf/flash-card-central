package johnkilf.learnerservice.learnerdeck;

import johnkilf.learnerservice.learner.Learner;
import johnkilf.learnerservice.learner.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(exposedHeaders = "Location")
public class LearnerDeckController {

    @Autowired
    LearnerRepository learnerRepository;
    @Autowired
    LearnerDeckRepository learnerDeckRepository;

    @GetMapping(value = "learners/{learnerId}/learnerdecks")
    public List<LearnerDeck> getLearnerDecks(@PathVariable long learnerId){
        List<LearnerDeck> results = learnerDeckRepository.findByLearnerId(learnerId);
        return results;
    }

    @PostMapping(value= "learners/{learnerId}/learnerdecks")
    public ResponseEntity addLearnerDeck(@PathVariable long learnerId, @RequestBody LearnerDeck learnerDeck){
        Optional<Learner> learner = learnerRepository.findById(learnerId);
        learnerDeck.setLearner(learner.get());
        LearnerDeck result = learnerDeckRepository.save(learnerDeck);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
