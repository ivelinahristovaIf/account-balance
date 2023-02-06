package com.example.accountbalance.convertor.impl;

import com.example.accountbalance.dto.InvoiceDto;
import com.example.accountbalance.model.Account;
import com.example.accountbalance.model.Invoice;
import com.example.accountbalance.model.Payment;
import com.example.accountbalance.model.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvoiceMapperImplTest {

    @InjectMocks
    private InvoiceMapperImpl mapper;

    @Test
    void sourceToDestination() {
        LocalDateTime issuedDate = LocalDateTime.now().minusDays(3);
        Invoice invoiceOne = Invoice
                .builder()
                .uid("uid")
                .issueDate(issuedDate)
                .account(Account.builder().uid("accountId").build())
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
        InvoiceDto invoiceDto = mapper.sourceToDestination(invoiceOne);
        assertNotNull(invoiceDto);
        assertEquals("uid", invoiceDto.getUid());
        assertEquals("accountId", invoiceDto.getAccountId());
        assertEquals(BigDecimal.valueOf(10.35), invoiceDto.getAmount());
        assertEquals(issuedDate, invoiceDto.getIssueDate());
    }

    @Test
    void destinationToSource() {
        LocalDateTime issuedDate = LocalDateTime.now();
        Invoice invoice = mapper.destinationToSource(InvoiceDto
                .builder()
                .accountId("accountId")
                .issueDate(issuedDate)
                .amount(BigDecimal.valueOf(15.96))
                .build());
        assertNotNull(invoice);
        assertNull(invoice.getUid());
        assertEquals(issuedDate, invoice.getIssueDate());
        assertEquals(BigDecimal.valueOf(15.96), invoice.getAmount());
    }
}