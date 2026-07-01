package knowledge_assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.saima.ai.config.QdrantProperties;

@SpringBootApplication
@ComponentScan(basePackages = {"knowledge_assistant", "com.saima.ai"})
@EnableConfigurationProperties(QdrantProperties.class)
public class KnowledgeAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgeAssistantApplication.class, args);
	}

}
