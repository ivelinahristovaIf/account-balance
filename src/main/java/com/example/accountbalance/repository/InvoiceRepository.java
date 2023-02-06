package com.example.accountbalance.repository;

import com.example.accountbalance.model.Invoice;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends GenericRepository<Invoice> {

    default List<Invoice> getAllInvoicesForDate(final String accountId, final LocalDate localDateTime) {
        return findAll()
                .stream()
                .filter(invoice -> accountId.equals(invoice.getAccount().getUid()))
                .filter(isInvoiceWithDate(localDateTime))
                .toList();
    }

    private Predicate<Invoice> isInvoiceWithDate(final LocalDate localDateTime) {
        return invoice -> invoice.getIssueDate().getDayOfMonth() == localDateTime.getDayOfMonth();
    }
}
