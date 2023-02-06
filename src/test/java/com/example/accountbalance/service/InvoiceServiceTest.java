package com.example.accountbalance.service;

import com.example.accountbalance.model.Account;
import com.example.accountbalance.model.Invoice;
import com.example.accountbalance.model.Payment;
import com.example.accountbalance.model.PaymentType;
import com.example.accountbalance.repository.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @InjectMocks
    private InvoiceService service;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Test
    void getAccountBalance() {
        Invoice invoiceOne = Invoice
                .builder()
                .uid("uid")
                .issueDate(LocalDateTime.now().minusDays(3))
                .account(Account.builder().build())
                .amount(BigDecimal.valueOf(10.35))
                .build();
        invoiceOne.addPayment(Payment
                .builder()
                .date(LocalDateTime.now())
                .type(PaymentType.PARTIAL)
                .amount(BigDecimal.valueOf(2.46))
                .invoice(invoiceOne)
                .uid("uid")
                .build());
        invoiceOne.addPayment(Payment
                .builder()
                .date(LocalDateTime.now().minusDays(1))
                .type(PaymentType.PARTIAL)
                .amount(BigDecimal.valueOf(4.85))
                .invoice(invoiceOne)
                .uid("uid")
                .build());
        Mockito
                .when(invoiceRepository.getAllInvoicesForDate(Mockito.anyString(), Mockito.any()))
                .thenReturn(List.of(invoiceOne));

        BigDecimal accountBalance = service.getAccountBalance("accountId", LocalDate.now());

        assertNotNull(accountBalance);
        Assertions.assertEquals(BigDecimal.valueOf(3.04), accountBalance);

    }

    @Test
    void givenNoInvoices_whenGetAccountBalance_thenReturnZero() {
        Mockito
                .when(invoiceRepository.getAllInvoicesForDate(Mockito.anyString(), Mockito.any()))
                .thenReturn(Collections.emptyList());

        BigDecimal accountBalance = service.getAccountBalance("accountId", LocalDate.now());

        assertNotNull(accountBalance);
        Assertions.assertEquals(BigDecimal.ZERO, accountBalance);

    }

}