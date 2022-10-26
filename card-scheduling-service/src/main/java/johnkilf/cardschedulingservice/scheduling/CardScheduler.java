package johnkilf.cardschedulingservice.scheduling;

import johnkilf.cardschedulingservice.remote.cardservice.CardDTO;
import johnkilf.cardschedulingservice.remote.cardservice.CardServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CardScheduler {

    private static final Duration LEARN_STAGE_INTERVAL_1 = Duration.ofMinutes(1);
    private static final Duration LEARN_STAGE_INTERVAL_2 = Duration.ofMinutes(10);
    private static final Duration START_INTERVAL = Duration.ofDays(1);
    Logger logger = LoggerFactory.getLogger(CardScheduler.class);

    @Autowired
    StudyRecordRepository studyRecordRepository;

    @Autowired
    CardServiceProxy cardServiceProxy;

    private static final double DEFAULT_EASE = 2.5d;

    public List<ScheduledCard> getCardsDue(long learnerId, long deckId) {
        final List<ScheduledCard> newCardsDue = getNewCardsDue(learnerId, deckId);
        final List<ScheduledCard> oldCardsDue = getOldCardsDue(learnerId, deckId);
        return Stream.concat(newCardsDue.stream(), oldCardsDue.stream()).toList();
    }


    private List<ScheduledCard> getNewCardsDue(long learnerId, long deckId) {
        final Optional<Long> latestCardRecordId = studyRecordRepository.getHighestCardStudied(learnerId, deckId);



        long latestCardInSchedule = latestCardRecordId.orElse(0L);

        logger.info("Highest card studied = " + latestCardInSchedule);

        // TODO Should be able to request this directly from card service
        // OR should this be handled some other way?
        final List<CardDTO> allCardsInDeck = cardServiceProxy.getCards(deckId);

        final List<ScheduledCard> newCardsDue = allCardsInDeck.stream()
                .filter(card -> card.getId() > latestCardInSchedule)
                .map(card -> ScheduledCard.builder()
                        .details(card)
                        .scheduledTime(LocalDateTime.now())
                        .newDurationInSecondsWhenIncorrect(LEARN_STAGE_INTERVAL_1.toSeconds())
                        .newDurationInSecondsWhenCorrect(LEARN_STAGE_INTERVAL_2.toSeconds())
                        .build())
                .collect(Collectors.toList());
        logger.info("Returning " + newCardsDue.size() + " new cards");
        return newCardsDue;
    }

    private List<ScheduledCard> getOldCardsDue(long learnerId, long deckId) {
        final List<StudyRecord> recordsDueByEndOfDay = studyRecordRepository.getCardsDue(learnerId, deckId, endOfDay());

        System.out.println(recordsDueByEndOfDay);

        for (var record: recordsDueByEndOfDay) {
            System.out.println(record.getCardId() + " - " + record.getNextDue());
        }

        if(recordsDueByEndOfDay.isEmpty()){
            return Collections.emptyList();
        }

        String commaSeparatedCardIds = String.join(",", recordsDueByEndOfDay.stream().map(record -> String.valueOf(record.getCardId())).toList());

        logger.info("comma sep" + commaSeparatedCardIds);
        List<CardDTO> oldCardsDue = cardServiceProxy.getCards(deckId, commaSeparatedCardIds);

        for(var ca : oldCardsDue){
            System.out.println(ca.getId());
        }

        logger.info("converting them cards");
        List<ScheduledCard> temp = oldCardsDue
                .stream().map(card -> convertToScheduledCard(recordsDueByEndOfDay, card))
                .sorted(Comparator.comparing(ScheduledCard::getScheduledTime))
                .collect(Collectors.toList());
        logger.info("Returning " + temp.size() + " old cards");
        return temp;
    }

    private ScheduledCard convertToScheduledCard(List<StudyRecord> recordsDueByEndOfDay, CardDTO card) {
        final StudyRecord studyRecord = recordsDueByEndOfDay.stream().filter(record -> record.getCardId() == card.getId()).findFirst().get();
//        logger.info("The study record is" + studyRecord);
        logger.info("innnn");
        return ScheduledCard.builder()
                .details(card)
                .scheduledTime(studyRecord.getNextDue())
                .newDurationInSecondsWhenIncorrect(calculateInterval(false, studyRecord).toSeconds())
                .newDurationInSecondsWhenCorrect(calculateInterval(true, studyRecord).toSeconds())
                .build();
    }

    private static LocalDateTime getNextDue(List<StudyRecord> recordsDueByEndOfDay, CardDTO card) {
        return recordsDueByEndOfDay.stream().filter(record -> record.getCardId() == card.getId()).findFirst().get().getNextDue();
    }

    private static LocalDateTime endOfDay() {
        return LocalDate.now().atTime(LocalTime.MAX);
    }

    public void createOrUpdateStudyRecord(RevisionResult revisionResult) {
        Optional<StudyRecord> existingStudyRecord = studyRecordRepository.findByLearnerIdAndCardId(revisionResult.getLearnerId(), revisionResult.getCardId());

        // TODO Validation
        // Validate deck is assigned to learner
        // Validate card belongs to deck

        final StudyRecord studyRecord;
        if (existingStudyRecord.isEmpty()) {
            studyRecord = getStartingStudyRecord(revisionResult);
        } else {
            studyRecord = existingStudyRecord.get();
        }

        updateStudyRecordBasedOnResult(revisionResult, studyRecord);

        studyRecordRepository.save(studyRecord);
    }

    private static StudyRecord getStartingStudyRecord(RevisionResult revisionResult) {
        final StudyRecord studyRecord;
        studyRecord = new StudyRecord();

        studyRecord.setLearnerId(revisionResult.getLearnerId());
        studyRecord.setDeckId(revisionResult.getDeckId());
        studyRecord.setCardId(revisionResult.getCardId());
        studyRecord.setLearningStage(0);
        studyRecord.setEase(DEFAULT_EASE);
        studyRecord.setLearningStage(0);
        studyRecord.setCurrentInterval(LEARN_STAGE_INTERVAL_1);
        return studyRecord;
    }

    private void updateStudyRecordBasedOnResult(RevisionResult revisionResult, StudyRecord studyRecord) {
        studyRecord.setCurrentInterval(calculateInterval(revisionResult.isSuccess(), studyRecord));
        studyRecord.setLearningStage(calculateLearningStage(revisionResult, studyRecord.getLearningStage()));
        studyRecord.setLastRevision(LocalDateTime.now());
        studyRecord.setNextDue(studyRecord.getLastRevision().plus(studyRecord.getCurrentInterval()));
    }
    private int calculateLearningStage(RevisionResult revisionResult, int existingStage) {
        if(existingStage == 2){
            return 2;
        } else {
            if(revisionResult.isSuccess()){
                return existingStage + 1;
            } else {
                return 0;
            }
        }
    }

    private Duration calculateInterval(boolean success, StudyRecord studyRecord) {

        if(studyRecord.getLearningStage() == 0) {
            if(success){
                return LEARN_STAGE_INTERVAL_2;
            } else {
                return LEARN_STAGE_INTERVAL_1;
            }
        } else if (studyRecord.getLearningStage() == 1) {
            if(success){
                return START_INTERVAL;
            } else {
                return LEARN_STAGE_INTERVAL_1;
            }
        } else {
            if(success){
                return multiplyDuration(studyRecord.getCurrentInterval(), studyRecord.getEase());
            } else {
                return START_INTERVAL;
            }
        }
    }

    public Duration multiplyDuration(Duration duration, double multiplicand) {
        long durationInSeconds = duration.toSeconds();
        long multipliedDuration = (long) (durationInSeconds * multiplicand);
        return Duration.ofSeconds(multipliedDuration);
    }

}
