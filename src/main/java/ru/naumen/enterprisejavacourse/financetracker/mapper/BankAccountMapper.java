package ru.naumen.enterprisejavacourse.financetracker.mapper;

import org.mapstruct.Mapper;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.dto.BankAccountDto;

/**
 * Маппер DTO для BankAccount
 */
@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    /**
     * Конвертирует сущность BankAccount в BankAccountDto
     *
     * @param bankAccount сущность BankAccount
     * @return BankAccountDto на основе сущности BankAccount
     */
    BankAccountDto convertToDTO(BankAccount bankAccount);

}
