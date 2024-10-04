package com.todo.app.controller;

import com.todo.app.dto.UserDTO;
import com.todo.app.entity.UserEntity;
import com.todo.app.exception.UserAlreadyExistException;
import com.todo.app.exception.UserNotFoundException;
import com.todo.app.model.User;
import com.todo.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Пользователи", description = "API для управления пользователями")  // Аннотация для группы методов, связанных с пользователями
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Регистрация пользователя", description = "Создание нового пользователя")  // Описание метода в Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "409", description = "Пользователь уже существует"),
            @ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера")
    })  // Описание возможных ответов
    @PostMapping
    public ResponseEntity registration(@RequestBody UserDTO userDTO) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userDTO.getUsername());
            userEntity.setPassword(userDTO.getPassword()); // Не забудьте хешировать пароль перед сохранением
            userService.createUser(userEntity);
            ResponseEntity.ok(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно сохранен");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Получить пользователя", description = "Получить данные одного пользователя по ID")  // Описание метода в Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера")
    })  // Описание возможных ответов
    @GetMapping("/{id}")
    public ResponseEntity getOneUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Получить всех пользователей", description = "Получить список всех пользователей")  // Описание метода в Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей получен"),
            @ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера")
    })  // Описание возможных ответов
    @GetMapping
    public ResponseEntity getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Обновить пользователя", description = "Обновление данных пользователя по ID")  // Описание метода в Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера")
    })  // Описание возможных ответов
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            UserEntity updatedUserEntity = new UserEntity();
            updatedUserEntity.setUsername(userDTO.getUsername());
            updatedUserEntity.setPassword(userDTO.getPassword()); // Не забудьте хешировать пароль перед обновлением
            User updatedUser = userService.updateUser(updatedUserEntity, id);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Удалить пользователя", description = "Удаление пользователя по ID")  // Описание метода в Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера")
    })  // Описание возможных ответов
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }
}
