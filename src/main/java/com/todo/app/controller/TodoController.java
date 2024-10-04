package com.todo.app.controller;

import com.todo.app.dto.TodoDTO;
import com.todo.app.entity.TodoEntity;
import com.todo.app.exception.UserAlreadyExistException;
import com.todo.app.exception.UserNotFoundException;
import com.todo.app.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  // Указывает, что этот класс является REST-контроллером
@RequestMapping("/todos")  // Указывает базовый URL для всех методов контроллера
@Tag(name = "TodoList", description = "API для управления todo")  // Аннотация для группы методов, связанных с пользователями
public class TodoController {

    @Autowired  // Инъекция зависимости TodoService
    private TodoService todoService;

    @Operation(summary = "Создание новой задачи", description = "Добавление новой задачи в систему")  // Описание метода для Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно сохранена"),
            @ApiResponse(responseCode = "409", description = "Пользователь уже существует"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })  // Возможные коды ответов
    @PostMapping
    public ResponseEntity<String> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            TodoEntity todoEntity = new TodoEntity();
            todoEntity.setTitle(todoDTO.getTitle());
            todoEntity.setDescription(todoDTO.getDescription());
            todoEntity.setCompleted(todoDTO.getCompleted());
            todoService.createTodo(todoEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Задача успешно сохранена");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Получение одной задачи", description = "Получение задачи по её ID")  // Описание метода для Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })  // Возможные коды ответов
    @GetMapping("/{id}")
    public ResponseEntity getOneTodo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(todoService.getOne(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Получение всех задач", description = "Получить список всех задач")  // Описание метода для Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })  // Возможные коды ответов
    @GetMapping
    public ResponseEntity getAllTodos() {
        try {
            return ResponseEntity.ok(todoService.getAll());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Обновление задачи", description = "Обновить задачу по её ID")  // Описание метода для Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })  // Возможные коды ответов
    @PutMapping("/{id}")
    public ResponseEntity updateTodo(@PathVariable Long id, @RequestBody TodoDTO todoDTO) {
        try {
            TodoEntity todoEntity = new TodoEntity();
            todoEntity.setTitle(todoDTO.getTitle());
            todoEntity.setDescription(todoDTO.getDescription());
            todoEntity.setCompleted(todoDTO.getCompleted());
            return ResponseEntity.ok(todoService.updateTodo(todoEntity, id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Удаление задачи", description = "Удаление задачи по её ID")  // Описание метода для Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })  // Возможные коды ответов
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(todoService.deleteTodo(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
        }
    }
}
