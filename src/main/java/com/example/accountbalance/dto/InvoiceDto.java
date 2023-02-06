package com.example.accountbalance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvoiceDto {

    private String uid;

    @NotBlank(message = "Account id is required for payment.")
    private String accountId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotBlank(message = "Issued date is required.")
    @PastOrPresent(message = "Issued Date should be now of in the past")
    private LocalDateTime issueDate;

    @NotBlank(message = "Invoice amount is required.")
    private BigDecimal amount;

    //    private Period servicePeriod;

}
