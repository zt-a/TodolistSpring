package com.todo.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для создания задачи")
public class TodoDTO {

    @Schema(description = "Название задачи", example = "Купить молоко", required = true)
    private String title;

    @Schema(description = "Описание задачи", example = "Купить молоко на завтрак")
    private String description;

    @Schema(description = "Статус завершенности задачи", example = "false")
    private Boolean completed;

    // Геттеры и сеттеры
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
}
