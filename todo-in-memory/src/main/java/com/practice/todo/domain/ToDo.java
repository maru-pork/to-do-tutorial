package com.practice.todo.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDo {

    @NotNull
    private String id;

    @NotNull
    @NotBlank
    private String description;

    private LocalDateTime created;

    private LocalDateTime modified;

    private boolean completed;

    public ToDo() {
        LocalDateTime now = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.created = now;
        this.modified = now;
    }

    public ToDo(String description) {
        this();
        this.description = description;
    }
}
