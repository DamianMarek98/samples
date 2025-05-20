package com.example.mcp;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.bedrock.titan.autoconfigure.BedrockTitanEmbeddingAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication(exclude = {
		BedrockTitanEmbeddingAutoConfiguration.class
})
public class McpApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpApplication.class, args);
	}

	@Bean
	ChatClient singularity(ChatClient.Builder builder) {
		return builder.build();
	}
}

@Controller
@ResponseBody
class AdoptionsAssistantController {
	private final ChatClient singularity;

	AdoptionsAssistantController(ChatClient singularity) {
		this.singularity = singularity;
	}

	@GetMapping("/{user}/inquire")
	String inquire(@RequestParam String question, @PathVariable String user) {
		return singularity.prompt()
				.user(question)
				.call()
				.content();
	}
}


interface DogRepository extends ListCrudRepository<Dog, Integer> {}

record Dog (@Id int id, String name, String owner, String description) {}
