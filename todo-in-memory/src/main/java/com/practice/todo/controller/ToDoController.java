package com.practice.todo.controller;

import com.practice.todo.domain.ToDo;
import com.practice.todo.domain.ToDoBuilder;
import com.practice.todo.repository.CommonRepository;
import com.practice.todo.validation.ToDoValidationError;
import com.practice.todo.validation.ToDoValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    private CommonRepository<ToDo> repository;

    @Autowired
    public ToDoController(CommonRepository<ToDo> repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Iterable<ToDo>> getToDos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id) {
        ToDo result = repository.findById(id);
        result.setCompleted(true);
        repository.save(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.ok()
                .header("Location", location.toString())
                .build();
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createTodo(@Valid @RequestBody ToDo toDo, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }

        ToDo result = repository.save(toDo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return  ResponseEntity.created(location).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id) {
        repository.delete(ToDoBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo) {
        repository.delete(toDo);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }
}
