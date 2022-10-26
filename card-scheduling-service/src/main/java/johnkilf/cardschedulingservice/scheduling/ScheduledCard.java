package johnkilf.cardschedulingservice.scheduling;

import johnkilf.cardschedulingservice.remote.cardservice.CardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledCard {
    private CardDTO details;

    private LocalDateTime scheduledTime;

    private long newDurationInSecondsWhenIncorrect;
    private long newDurationInSecondsWhenCorrect;
}
