package ru.naumen.enterprisejavacourse.financetracker.exception;

/**
 * Исключение, указывающее на ошибку при удалении категории
 */
public class CategoryDeletionException extends RuntimeException {

    /**
     * Создает новое исключение с заданным сообщением
     *
     * @param message сообщение об ошибке
     */
    public CategoryDeletionException(String message) { super(message); }
}
