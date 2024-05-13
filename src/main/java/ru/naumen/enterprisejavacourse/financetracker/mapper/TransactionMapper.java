package ru.naumen.enterprisejavacourse.financetracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;
import ru.naumen.enterprisejavacourse.financetracker.dto.TransactionDto;

/**
 * Интерфейс маппера для конвертации сущностей Transaction в объекты TransactionDto
 */
@Mapper
public interface TransactionMapper {

    /**
     * Экземпляр маппера
     */
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    /**
     * Метод конвертации сущности Transaction в объект TransactionDto
     *
     * @param transaction сущность Transaction
     * @return TransactionDto на основе сущности Transaction
     */
    @Mapping(source = "category.id", target = "categoryId")
    TransactionDto transactiontoTransactionDto(Transaction transaction);
}
