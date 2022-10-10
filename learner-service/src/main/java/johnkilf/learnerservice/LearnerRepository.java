package johnkilf.learnerservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
}
