package ru.naumen.enterprisejavacourse.financetracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;
import ru.naumen.enterprisejavacourse.financetracker.dto.TransactionDto;

/**
 * Интерфейс маппера для конвертации сущностей Transaction в объекты TransactionDto
 */
@Mapper
@Component
public interface TransactionMapper {

    /**
     * Метод конвертации сущности Transaction в объект TransactionDto
     *
     * @param transaction сущность Transaction
     * @return TransactionDto на основе сущности Transaction
     */
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "bankAccount.id", target = "bankAccountId")
    TransactionDto transactionToTransactionDto(Transaction transaction);
}
