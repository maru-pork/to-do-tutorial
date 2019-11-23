package com.practice.todo.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDo {

    private String id;
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

    @Override
    public String toString() {
        return "ToDo{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", completed=" + completed +
                '}';
    }
}
