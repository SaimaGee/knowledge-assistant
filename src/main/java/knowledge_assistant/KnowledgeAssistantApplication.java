package knowledge_assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"knowledge_assistant", "com.saima.ai"})
public class KnowledgeAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgeAssistantApplication.class, args);
	}

}
