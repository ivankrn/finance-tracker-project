package ru.naumen.enterprisejavacourse.financetracker.exception;

/**
 * Исключение, указывающее на то, что транзакция не была найдена
 */
public class TransactionNotFoundException extends RuntimeException {

    /**
     * Создает новое исключение с заданным сообщением
     *
     * @param message сообщение об ошибке
     */
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
