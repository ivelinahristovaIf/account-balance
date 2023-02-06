package com.example.accountbalance.service;

import com.example.accountbalance.convertor.InvoiceMapper;
import com.example.accountbalance.dto.InvoiceDto;
import com.example.accountbalance.model.Account;
import com.example.accountbalance.model.Invoice;
import com.example.accountbalance.model.Payment;
import com.example.accountbalance.repository.AccountRepository;
import com.example.accountbalance.repository.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class InvoiceService implements GenericService<InvoiceDto> {

    private final InvoiceRepository invoiceRepository;

    private final AccountRepository accountRepository;

    private final InvoiceMapper mapper;

    public InvoiceService(final InvoiceRepository repository, final AccountRepository accountRepository,
            final InvoiceMapper invoiceMapper) {
        this.invoiceRepository = repository;
        this.accountRepository = accountRepository;
        this.mapper = invoiceMapper;
    }

    public BigDecimal getAccountBalance(final String accountId, final LocalDate dateTime) {
        List<Invoice> allInvoicesForDate = invoiceRepository.getAllInvoicesForDate(accountId, dateTime);
        BigDecimal invoicesSum = allInvoicesForDate
                .stream()
                .map(Invoice::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal paymentsSum = allInvoicesForDate
                .stream()
                .map(Invoice::getPayments)
                .flatMap(Collection::stream)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return invoicesSum.subtract(paymentsSum);

    }

    @Override
    public Page<InvoiceDto> getPage(final Pageable pageable) {
        return invoiceRepository.findAll(pageable).map(mapper::sourceToDestination);
    }

    @Override
    public InvoiceDto getById(final String accountId) {
        Invoice invoice = findByIdOrThrow(accountId);
        return mapper.sourceToDestination(invoice);
    }

    @Override
    public InvoiceDto create(final InvoiceDto body) {
        Account account = accountRepository
                .findById(body.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found!"));
        Invoice invoice = mapper.destinationToSource(body);
        invoice.setAccount(account);
        account.addInvoice(invoice);
        return mapper.sourceToDestination(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceDto update(final String uid, final InvoiceDto body) {
        Invoice invoice = findByIdOrThrow(uid);
        invoice.setAmount(body.getAmount());
        invoice.setIssueDate(body.getIssueDate());

        return mapper.sourceToDestination(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceDto delete(final String accountId) {
        Invoice invoice = findByIdOrThrow(accountId);
        invoiceRepository.deleteById(accountId);
        return mapper.sourceToDestination(invoice);
    }

    private Invoice findByIdOrThrow(final String id) {
        return invoiceRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Payment with id: " + id));
    }
}
