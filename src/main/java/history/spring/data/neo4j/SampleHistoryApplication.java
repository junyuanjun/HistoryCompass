package history.spring.data.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 */
@SpringBootApplication
@EntityScan("history.spring.data.neo4j.domain")
public class SampleHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleHistoryApplication.class, args);
	}
}
