package com.study.accounts.mappers;

import com.study.accounts.dtos.AccountDto;
import com.study.accounts.entities.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDto(Account accountEntity, AccountDto accountDto) {

        accountDto.setAccountNumber(accountEntity.getAccountNumber());
        accountDto.setAccountType(accountEntity.getAccountType());
        accountDto.setBranchAddress(accountEntity.getBranchAddress());

        return accountDto;
    }

    public static Account mapToAccountEntity(AccountDto accountDto, Account accountEntity) {

        accountEntity.setAccountNumber(accountDto.getAccountNumber());
        accountEntity.setAccountType(accountDto.getAccountType());
        accountEntity.setBranchAddress(accountDto.getBranchAddress());

        return accountEntity;
    }
}
