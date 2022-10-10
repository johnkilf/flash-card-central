package johnkilf.learnerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class LearnerController {

    @Autowired
    LearnerRepository learnerRepository;
    private final Logger logger = LoggerFactory.getLogger(LearnerController.class);
    private final List<Learner> learners =
            new ArrayList<>(Arrays.asList(
            new Learner(1l, "John")));

    @GetMapping(value = "/learners")
    public List<Learner> GetAllLearners(){

        return learnerRepository.findAll();
    }

    @GetMapping(value = "/learners/{id}")
    public ResponseEntity<Learner> GetLearner(@PathVariable long id){
        Optional<Learner> learner = learnerRepository.findById(id);
        return ResponseEntity.of(learner);
    }

    @PostMapping(value = "/learners")
    public ResponseEntity CreateLearner(@RequestBody Learner learner){
        logger.info("posting learner " + learner);
        Learner result = learnerRepository.save(learner);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/learners/{id}")
    public ResponseEntity DeleteLearner(@PathVariable long id){
        learnerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
