package com.example.accountbalance.convertor;

import com.example.accountbalance.dto.PaymentDto;
import com.example.accountbalance.model.Payment;

public interface PaymentMapper {

    PaymentDto sourceToDestination(Payment source);

    Payment destinationToSource(PaymentDto destination);

}
