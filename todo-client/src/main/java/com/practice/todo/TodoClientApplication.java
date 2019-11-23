package com.practice.todo;

import com.practice.todo.domain.ToDo;
import com.practice.todo.service.ToDoRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoClientApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TodoClientApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	private Logger log = LoggerFactory.getLogger(TodoClientApplication.class);

	@Bean
	public CommandLineRunner process(ToDoRestClient client) {
		return args -> {
			Iterable<ToDo> toDos = client.findAll();
			assert toDos!=null;
			toDos.forEach(todo -> {
				log.info(todo.toString());
			});


			log.info("Adding new to do...");
			ToDo newToDo = client.upsert(new ToDo("Get some sleep dude"));
			assert newToDo!=null;
			log.info(newToDo.toString());

			log.info("Finding newly created to do...");
			ToDo toDo = client.findById(newToDo.getId());
			assert toDo!=null;
			log.info(toDo.toString());

			log.info("Completing to do...");
			ToDo completed = client.setCompleted(newToDo.getId());
			assert completed.isCompleted();
			log.info(completed.toString());

			log.info("Deleting to do...");
			client.delete(newToDo.getId());
			assert client.findById(newToDo.getId())==null;
		};
	}

}
