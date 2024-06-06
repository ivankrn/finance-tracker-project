package ru.naumen.enterprisejavacourse.financetracker.exception;

/**
 * Исключение, указывающее на ошибку при удалении банковского счета
 */
public class BankAccountDeletionException extends RuntimeException {

    /**
     * Создает новое исключение с заданным сообщением
     *
     * @param message сообщение об ошибке
     */
    public BankAccountDeletionException(String message) { super(message); }
}
