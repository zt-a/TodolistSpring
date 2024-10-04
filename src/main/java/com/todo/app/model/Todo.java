package com.todo.app.model;

import com.todo.app.entity.TodoEntity;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Идентификатор задачи", example = "1")
    private Long id;  // Идентификатор задачи

    @Schema(description = "Заголовок задачи", example = "Сделать домашку")
    private String title;  // Заголовок задачи

    @Schema(description = "Описание задачи", example = "Необходимо выполнить домашнее задание по математике")
    private String description;  // Описание задачи

    @Schema(description = "Статус выполнения задачи", example = "false")
    private Boolean completed;  // Статус выполнения задачи

    public static Todo toModel(TodoEntity entity) {
        Todo model = new Todo();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setDescription(entity.getDescription());
        model.setCompleted(entity.getCompleted());
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Todo() {
    }
}
