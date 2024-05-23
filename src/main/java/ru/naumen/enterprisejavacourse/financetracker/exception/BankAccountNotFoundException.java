package ru.naumen.enterprisejavacourse.financetracker.exception;

import com.vaadin.flow.router.NotFoundException;
import lombok.NoArgsConstructor;

/**
 * Исключение, указывающее на то, что банковский счет не был найден
 */
@NoArgsConstructor
public class BankAccountNotFoundException extends NotFoundException {}
