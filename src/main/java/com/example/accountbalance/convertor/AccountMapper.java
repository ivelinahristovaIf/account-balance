package com.example.accountbalance.convertor;

import com.example.accountbalance.dto.AccountDto;
import com.example.accountbalance.model.Account;

public interface AccountMapper {

    AccountDto sourceToDestination(Account source);

    Account destinationToSource(AccountDto destination);

}
