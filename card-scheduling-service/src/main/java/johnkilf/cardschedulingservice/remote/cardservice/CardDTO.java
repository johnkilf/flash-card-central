package johnkilf.cardschedulingservice.remote.cardservice;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDTO {
    private long id;
    private String front;
    private String back;
}
