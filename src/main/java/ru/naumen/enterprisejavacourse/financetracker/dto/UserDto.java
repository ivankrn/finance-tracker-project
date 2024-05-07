package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Value;


/**
 * DTO пользователя
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    @NotBlank(message = "Поле \"Имя пользователя\" не должно быть пустым")
    @Size(min = 4, max = 15, message = "Имя пользователя должно содержать от 4 до 15 символов")
    @EqualsAndHashCode.Include
    String username;

    @NotBlank(message = "Поле \"Пароль\" не должно быть пустым")
    @Size(min = 5, max = 15, message = "Пароль должен содержать от 5 до 15 символов")
    @Pattern(
            regexp = "^(?=.[A-Z])(?=.\\d)[a-zA-Z\\d]{5,15}$",
            message = "Ваш пароль должен быть от 5 до 15 символов, " +
                    "включая хотя бы одну заглавную букву и одну цифру")
    String password;
}
