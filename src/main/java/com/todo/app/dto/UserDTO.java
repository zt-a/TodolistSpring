package com.todo.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для пользователя")
public class UserDTO {

    @Schema(description = "Имя пользователя", example = "user", required = true)
    private String username;

    @Schema(description = "Пароль пользователя", example = "pass", required = true)
    private String password;

    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
