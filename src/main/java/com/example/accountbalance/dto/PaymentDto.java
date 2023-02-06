package com.example.accountbalance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDto {

    private String uid;

    @NotBlank(message = "Payment amount is required.")
    private BigDecimal amount;

    @NotBlank
    @PastOrPresent(message = "Payment date is required.")
    private LocalDateTime date;

    @NotBlank(message = "Payment type is required.")
    private String type;

    @NotBlank(message = "Invoice uid is required.")
    private String invoiceUid;

}
