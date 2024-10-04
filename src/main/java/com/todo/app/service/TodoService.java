package com.todo.app.service;

import com.todo.app.entity.TodoEntity;
import com.todo.app.exception.TodoNotFoundException;
import com.todo.app.model.Todo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import com.todo.app.repository.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Operation(summary = "Создание задачи", description = "Создает новую задачу и сохраняет её в системе.")
    public Todo createTodo(@Parameter(description = "Информация о задаче") TodoEntity todo) {
        try {
            return Todo.toModel(todoRepo.save(todo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Получение задачи", description = "Возвращает задачу по её идентификатору.")
    public Todo getOne(@Parameter(description = "Идентификатор задачи") Long id) {
        TodoEntity todoEntity = todoRepo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("В списке дел не найдена такая задача"));
        return Todo.toModel(todoEntity);
    }

    @Operation(summary = "Получение всех задач", description = "Возвращает список всех задач.")
    public Iterable<TodoEntity> getAll() {
        try {
            return todoRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Обновление задачи", description = "Обновляет информацию о задаче по её идентификатору.")
    public Todo updateTodo(
            @Parameter(description = "Информация об обновляемой задаче") TodoEntity updatedTodo,
            @Parameter(description = "Идентификатор задачи") Long id
    ) throws TodoNotFoundException {
        return todoRepo.findById(id).map(todo -> {
            todo.setId(updatedTodo.getId());
            todo.setTitle(updatedTodo.getTitle());
            todo.setCompleted(updatedTodo.getCompleted());
            todo.setDescription(updatedTodo.getDescription());
            todo.setUser((updatedTodo.getUser()));
            TodoEntity savedTodo = todoRepo.save(todo);
            return Todo.toModel(savedTodo);
        }).orElseThrow(() -> new TodoNotFoundException("Такая задача в списке дел не найдена"));
    }

    @Operation(summary = "Удаление задачи", description = "Удаляет задачу по её идентификатору.")
    public String deleteTodo(@Parameter(description = "Идентификатор задачи") Long id) {
        todoRepo.deleteById(id);
        return "Задача удалена";
    }
}
