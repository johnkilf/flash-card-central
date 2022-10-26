package johnkilf.learnerservice.learner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(exposedHeaders = "Location")
public class LearnerController {

    @Autowired
    LearnerRepository learnerRepository;
    private final Logger logger = LoggerFactory.getLogger(LearnerController.class);

    @GetMapping(value = "/learners")
    public List<Learner> GetAllLearners(){

        return learnerRepository.findAll();
    }

    @GetMapping(value = "/learners/{id}")
    public ResponseEntity<Learner> GetLearner(@PathVariable long id) {
        Optional<Learner> learner = learnerRepository.findById(id);
        return ResponseEntity.of(learner);
    }

    @PostMapping(value = "/learners")
    public ResponseEntity CreateLearner(@RequestBody Learner learner) {
        logger.info("posting learner " + learner);
        Learner result = learnerRepository.save(learner);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "learners/{id}")
    public ResponseEntity UpdateLeaner(@PathVariable long id, @RequestBody Learner learner) {
        learner.setId(id);
        Learner result = learnerRepository.save(learner);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok().location(location).build();
    }

    @DeleteMapping(value = "/learners/{id}")
    public ResponseEntity DeleteLearner(@PathVariable long id){
        learnerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
