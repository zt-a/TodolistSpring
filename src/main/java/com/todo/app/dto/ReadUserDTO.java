package com.todo.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для пользователя")
public class ReadUserDTO {
    @Schema(description = "ID пользователя", example = "1", required = true)
    private Long id;

    @Schema(description = "Имя пользователя", example = "user", required = true)
    private String username;
    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
