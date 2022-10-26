package johnkilf.learnerservice.learnerdeck;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearnerDeckRepository extends JpaRepository<LearnerDeck, Long> {

    List<LearnerDeck> findByLearnerId(Long learnerId);
}
