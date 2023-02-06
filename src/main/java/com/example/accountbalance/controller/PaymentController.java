package com.example.accountbalance.controller;

import jakarta.validation.Valid;

import com.example.accountbalance.dto.PaymentDto;
import com.example.accountbalance.service.GenericService;

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
@RequestMapping("/payments")
public class PaymentController extends GenericController<PaymentDto> {

    public PaymentController(final GenericService<PaymentDto> service) {
        super(service);
    }

    @Override
    @PostMapping
    public ResponseEntity<PaymentDto> create(@Valid @RequestBody final PaymentDto created) {
        return super.create(created);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getOne(@PathVariable("id") final String id) {
        return super.getOne(id);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<PaymentDto>> getPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return super.getPage(page, size);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> update(@PathVariable("id") final String id,
            @Valid @RequestBody final PaymentDto updated) {
        return super.update(id, updated);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> delete(@PathVariable("id") final String id) {
        return super.delete(id);
    }
}
