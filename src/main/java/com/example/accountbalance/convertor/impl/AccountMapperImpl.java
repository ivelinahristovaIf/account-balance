package com.example.accountbalance.convertor.impl;

import com.example.accountbalance.convertor.AccountMapper;
import com.example.accountbalance.dto.AccountDto;
import com.example.accountbalance.model.Account;

import org.springframework.stereotype.Component;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto sourceToDestination(final Account source) {
        return AccountDto.builder().uid(source.getUid()).name(source.getName()).build();
    }

    @Override
    public Account destinationToSource(final AccountDto destination) {
        return Account.builder().uid(destination.getUid()).name(destination.getName()).build();
    }
}
