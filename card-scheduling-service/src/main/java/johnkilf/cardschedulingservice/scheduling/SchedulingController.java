package johnkilf.cardschedulingservice.scheduling;

import johnkilf.cardschedulingservice.remote.cardservice.CardDTO;
import johnkilf.cardschedulingservice.remote.cardservice.CardServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "Location")
public class SchedulingController {

    private final Logger logger = LoggerFactory.getLogger(SchedulingController.class);
    @Autowired
    private CardServiceProxy cardServiceProxy;

    @Autowired
    private StudyRecordRepository studyRecordRepository;

    @Autowired
    private CardScheduler cardScheduler;

    @GetMapping(value = "/schedule/learners/{learnerId}/decks/{deckId}")
    public List<ScheduledCard> getCardsToStudy(@PathVariable long learnerId, @PathVariable long deckId){
        return cardScheduler.getCardsDue(learnerId, deckId);
    }



    @PostMapping(value = "/revision")
    public ResponseEntity postRevisionResult(@RequestBody @Valid RevisionResult revisionResult) {
        cardScheduler.createOrUpdateStudyRecord(revisionResult);
        return ResponseEntity.ok().build();
    }


}
