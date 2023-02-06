package com.example.accountbalance.controller;

import jakarta.validation.Valid;

import com.example.accountbalance.dto.InvoiceDto;
import com.example.accountbalance.service.GenericService;
import com.example.accountbalance.service.InvoiceService;

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
@RequestMapping("/invoices")
public class InvoiceController extends GenericController<InvoiceDto> {

    private final InvoiceService invoiceService;

    public InvoiceController(final GenericService<InvoiceDto> service, final InvoiceService invoiceService) {
        super(service);
        this.invoiceService = invoiceService;
    }

    @Override
    @PostMapping
    public ResponseEntity<InvoiceDto> create(@Valid @RequestBody final InvoiceDto created) {
        return super.create(created);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getOne(@PathVariable("id") final String id) {
        return super.getOne(id);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<InvoiceDto>> getPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return super.getPage(page, size);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> update(@PathVariable("id") final String id,
            @Valid @RequestBody final InvoiceDto updated) {
        return super.update(id, updated);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<InvoiceDto> delete(@PathVariable("id") final String id) {
        return super.delete(id);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable("id") String id,
            @RequestParam("date") LocalDate localDate) {
        return ResponseEntity.ok(invoiceService.getAccountBalance(id, localDate));
    }

}
