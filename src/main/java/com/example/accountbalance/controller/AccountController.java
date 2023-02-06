package com.example.accountbalance.controller;

import jakarta.validation.Valid;

import com.example.accountbalance.dto.AccountDto;
import com.example.accountbalance.service.AccountService;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController extends GenericController<AccountDto> {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        super(accountService);
        this.accountService = accountService;
    }

    @Override
    @PostMapping
    public ResponseEntity<AccountDto> create(@Valid @RequestBody final AccountDto created) {
        return super.create(created);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getOne(@PathVariable("id") final String id) {
        return super.getOne(id);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AccountDto>> getPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return super.getPage(page, size);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable("id") final String id,
            @Valid @RequestBody final AccountDto updated) {
        return super.update(id, updated);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDto> delete(@PathVariable("id") final String id) {
        return super.delete(id);
    }

}
