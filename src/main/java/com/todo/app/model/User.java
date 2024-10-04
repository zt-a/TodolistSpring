package com.todo.app.model;


import com.todo.app.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

public class User {

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;  // Идентификатор пользователя

    @Schema(description = "Имя пользователя", example = "john_doe")
    private String username;  // Имя пользователя

    @Schema(description = "Список задач, принадлежащих пользователю")
    private List<Todo> todos;  // Список задач пользователя

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setTodos(entity.getTodos()
                .stream().map(Todo::toModel)
                .collect(Collectors.toList()));
        return model;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public User() {
    }
}
