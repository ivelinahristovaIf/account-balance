package com.example.accountbalance.convertor.impl;

import com.example.accountbalance.dto.AccountDto;
import com.example.accountbalance.model.Account;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountMapperImplTest {

    @InjectMocks
    private AccountMapperImpl accountMapper;

    @Test
    void sourceToDestination() {
        AccountDto accountDto = accountMapper.sourceToDestination(
                Account.builder().uid("123").name("Ivan").invoices(Collections.emptySet()).build());
        assertNotNull(accountDto);
        assertEquals("123", accountDto.getUid());
        assertEquals("Ivan", accountDto.getName());
    }

    @Test
    void destinationToSource() {
        Account account = accountMapper.destinationToSource(AccountDto.builder().name("Ivan").build());
        assertNotNull(account);
        assertNull(account.getUid());
        assertEquals("Ivan", account.getName());
    }
}