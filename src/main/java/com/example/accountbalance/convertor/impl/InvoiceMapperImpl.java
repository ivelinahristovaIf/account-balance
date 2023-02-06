package com.example.accountbalance.convertor.impl;

import com.example.accountbalance.convertor.InvoiceMapper;
import com.example.accountbalance.dto.InvoiceDto;
import com.example.accountbalance.model.Invoice;

import org.springframework.stereotype.Component;

@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Override
    public InvoiceDto sourceToDestination(final Invoice source) {
        return InvoiceDto
                .builder()
                .uid(source.getUid())
                .accountId(source.getAccount().getUid())
                .issueDate(source.getIssueDate())
                .amount(source.getAmount())
                //                .servicePeriod(source.getServicePeriod()) //TODO add period
                .build();
    }

    @Override
    public Invoice destinationToSource(final InvoiceDto destination) {
        return Invoice
                .builder()
                .uid(destination.getUid())
                .issueDate(destination.getIssueDate())
                .amount(destination.getAmount())
                //                .servicePeriod(destination.getServicePeriod())
                .build();
    }
}
