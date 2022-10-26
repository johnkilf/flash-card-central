package johnkilf.cardschedulingservice.scheduling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long> {

    public Optional<StudyRecord> findByLearnerIdAndCardId(long learnerId, long cardId);

    @Query(value = "SELECT MAX (CARD_ID) FROM STUDY_RECORD WHERE LEARNER_ID = ?1 AND DECK_ID = ?2",
            nativeQuery = true)
    public Optional<Long> getHighestCardStudied(long learnerId, long deckId);


    @Query(value = "SELECT * FROM STUDY_RECORD WHERE LEARNER_ID = ?1 AND DECK_ID = ?2 AND NEXT_DUE < ?3",
            nativeQuery = true)
    public List<StudyRecord> getCardsDue(long learnerId, long deckId, LocalDateTime dueBy);


}
