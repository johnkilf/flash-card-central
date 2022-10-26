package johnkilf.cardschedulingservice.remote.learnerservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "learner", url = "${LEARNER_URI:http://localhost}:8000")
public interface LearnerServiceProxy {

    @GetMapping("/learners/{id}")
    LearnerDTO getLearner(@PathVariable long id);
}
