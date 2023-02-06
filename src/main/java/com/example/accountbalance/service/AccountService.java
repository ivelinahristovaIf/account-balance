package com.example.accountbalance.service;

import lombok.extern.log4j.Log4j2;

import com.example.accountbalance.convertor.AccountMapper;
import com.example.accountbalance.dto.AccountDto;
import com.example.accountbalance.model.Account;
import com.example.accountbalance.repository.AccountRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class AccountService implements GenericService<AccountDto> {

    private final AccountRepository accountRepository;

    private final AccountMapper mapper;

    public AccountService(final AccountRepository repository, final AccountMapper accountMapper) {
        this.accountRepository = repository;
        this.mapper = accountMapper;
    }

    @Override
    public Page<AccountDto> getPage(final Pageable pageable) {
        return accountRepository.findAll(pageable).map(mapper::sourceToDestination);
    }

    @Override
    public AccountDto getById(final String id) {
        return accountRepository.findById(id).map(mapper::sourceToDestination).orElseThrow(() -> {
            log.warn("Entity with id {} Not Found!", id);
            return new NoSuchElementException("No entity with id: " + id);
        });
    }

    @Override
    @Transactional
    public AccountDto update(final String id, final AccountDto updated) {
        Account account = findByIdOrThrow(id);
        Account destinationToSource = mapper.destinationToSource(updated);
        destinationToSource.setInvoices(account.getInvoices());
        destinationToSource.setUid(account.getUid());
        return mapper.sourceToDestination(accountRepository.save(destinationToSource));
    }

    @Override
    @Transactional
    public AccountDto create(final AccountDto newDomain) {
        Account account = mapper.destinationToSource(newDomain);
        account.setInvoices(new HashSet<>());
        final Account save = accountRepository.save(account);
        return mapper.sourceToDestination(save);
    }

    @Override
    @Transactional
    public AccountDto delete(final String id) {
        //check if object with this id exists
        Account entity = findByIdOrThrow(id);
        accountRepository.deleteById(id);
        return mapper.sourceToDestination(entity);
    }

    private Account findByIdOrThrow(final String id) {
        return accountRepository.findById(id).orElseThrow(() -> {
            log.warn("Entity with id {} Not Found!", id);
            return new NoSuchElementException("No entity with id: " + id);
        });
    }

}
