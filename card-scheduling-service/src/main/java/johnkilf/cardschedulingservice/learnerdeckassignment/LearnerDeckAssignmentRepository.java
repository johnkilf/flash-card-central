package johnkilf.cardschedulingservice.learnerdeckassignment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearnerDeckAssignmentRepository extends JpaRepository<LearnerDeckAssignment, Long> {

    public List<LearnerDeckAssignment> findByLearnerId(long learnerId);
}
