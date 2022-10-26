package johnkilf.cardschedulingservice.scheduling;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class RevisionResult {




    @Min(value = 1)
    private long learnerId;

    @Min(value = 1)
    private long deckId;

    @Min(value = 1)
    private long cardId;

    private boolean success;

}
