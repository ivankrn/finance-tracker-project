package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Объект пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "Поле \"Имя пользователя\" не должно быть пустым")
    @Size(min = 4, max = 15, message = "Имя пользователя должно содержать от 4 до 15 символов")
    private String username;

    @NotBlank(message = "Поле \"Пароль\" не должно быть пустым")
    @Size(min = 5, max = 15, message = "Пароль должен содержать от 5 до 15 символов")
    @Pattern(
            regexp = "^(?=.[A-Z])(?=.\\d)[a-zA-Z\\d]{5,15}$",
            message = "Ваш пароль должен быть от 5 до 15 символов, " +
                    "включая хотя бы одну заглавную букву и одну цифру")
    private String password;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserDto userDto = (UserDto) object;
        return Objects.equals(username, userDto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
