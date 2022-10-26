package johnkilf.cardschedulingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CardSchedulingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardSchedulingServiceApplication.class, args);
	}

}
