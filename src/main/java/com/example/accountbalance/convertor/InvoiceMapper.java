package com.example.accountbalance.convertor;

import com.example.accountbalance.dto.InvoiceDto;
import com.example.accountbalance.model.Invoice;

public interface InvoiceMapper {

    InvoiceDto sourceToDestination(Invoice source);

    Invoice destinationToSource(InvoiceDto destination);

}
