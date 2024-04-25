package ru.naumen.enterprisejavacourse.financetracker.service;


/**
 * Сервис для управления пользователями
 */
public interface UserService {

    /**
     * Регистрирует нового пользователя
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    void register(String username, String password);

}
