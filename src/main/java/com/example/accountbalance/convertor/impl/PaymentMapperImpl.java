package com.example.accountbalance.convertor.impl;

import com.example.accountbalance.convertor.PaymentMapper;
import com.example.accountbalance.dto.PaymentDto;
import com.example.accountbalance.model.Payment;
import com.example.accountbalance.model.PaymentType;

import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDto sourceToDestination(final Payment source) {
        return PaymentDto
                .builder()
                .amount(source.getAmount())
                .date(source.getDate())
                .invoiceUid(source.getUid())
                .type(source.getType().toString())
                .build();
    }

    @Override
    public Payment destinationToSource(final PaymentDto destination) {
        return Payment
                .builder()
                .amount(destination.getAmount())
                .date(destination.getDate())
                .uid(destination.getUid())
                .type(PaymentType.valueOf(destination.getType()))
                .build();
    }
}
