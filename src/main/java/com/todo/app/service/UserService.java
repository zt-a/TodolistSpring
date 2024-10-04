package com.todo.app.service;

import com.todo.app.dto.ReadUserDTO;
import com.todo.app.entity.UserEntity;
import com.todo.app.exception.UserAlreadyExistException;
import com.todo.app.exception.UserNotFoundException;
import com.todo.app.model.User;
import com.todo.app.repository.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Создание пользователя", description = "Создает нового пользователя в системе.")
    public User createUser(@Parameter(description = "Данные пользователя") UserEntity user) throws UserAlreadyExistException {
        UserEntity existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUser = userRepo.save(user);
        return User.toModel(savedUser);
    }


    @Operation(summary = "Получение пользователя", description = "Возвращает пользователя по идентификатору.")
    public User getUser(@Parameter(description = "Идентификатор пользователя") Long id) throws UserNotFoundException {
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        return User.toModel(userEntity);
    }

    @Operation(summary = "Обновление пользователя", description = "Обновляет информацию о пользователе.")
    public User updateUser(
            @Parameter(description = "Данные обновленного пользователя") UserEntity updatedUserData,
            @Parameter(description = "Идентификатор пользователя") Long id
    ) throws UserNotFoundException {
        return userRepo.findById(id).map(user -> {
                    user.setUsername(updatedUserData.getUsername());
                    if (updatedUserData.getPassword() != null && !updatedUserData.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(updatedUserData.getPassword()));
                    }
                    UserEntity updatedUser = userRepo.save(user);
                    return User.toModel(updatedUser);
                })
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    @Operation(summary = "Получение пользователей", description = "Возвращает всех пользователей")
    public List<User> getAll() {
        try {
            return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                    .map(User::toModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Удаление пользователя", description = "Удаляет пользователя по идентификатору.")
    public String deleteUser(@Parameter(description = "Идентификатор пользователя") Long id) {
        userRepo.deleteById(id);
        return "Пользователь с ID " + id + " удален";
    }

    public boolean checkPassword(String username, String rawPassword) {
        UserEntity userEntity = userRepo.findByUsername(username);
        if (userEntity == null) {
            return false; // Пользователь не найден
        }
        return passwordEncoder.matches(rawPassword, userEntity.getPassword()); // Проверка пароля
    }

}
