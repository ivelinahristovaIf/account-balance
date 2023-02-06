package com.example.accountbalance.service;

import lombok.extern.log4j.Log4j2;

import com.example.accountbalance.convertor.PaymentMapper;
import com.example.accountbalance.dto.PaymentDto;
import com.example.accountbalance.model.Invoice;
import com.example.accountbalance.model.Payment;
import com.example.accountbalance.model.PaymentType;
import com.example.accountbalance.repository.InvoiceRepository;
import com.example.accountbalance.repository.PaymentRepository;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaymentService implements GenericService<PaymentDto> {

    private final PaymentRepository paymentRepository;

    private final InvoiceRepository invoiceRepository;

    private final PaymentMapper mapper;

    public PaymentService(final PaymentRepository paymentRepository, final InvoiceRepository invoiceRepository,
            final PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        mapper = paymentMapper;
    }

    @Override
    public Page<PaymentDto> getPage(final Pageable pageable) {
        return paymentRepository.findAll(pageable).map(mapper::sourceToDestination);
    }

    @Override
    public PaymentDto getById(final String id) {
        return mapper.sourceToDestination(findByIdOrThrow(id));
    }

    @Override
    public PaymentDto update(final String id, final PaymentDto updated) {
        Payment payment = findByIdOrThrow(id);
        payment.setAmount(updated.getAmount());
        payment.setDate(updated.getDate());
        payment.setType(PaymentType.valueOf(updated.getType()));
        return mapper.sourceToDestination(paymentRepository.save(payment));
    }

    @Override
    public PaymentDto create(final PaymentDto newDomain) {
        Payment payment = mapper.destinationToSource(newDomain);
        Invoice invoice = invoiceRepository
                .findById(newDomain.getInvoiceUid())
                .orElseThrow(() -> new NoSuchElementException("No invoice with id: " + newDomain.getInvoiceUid()));
        payment.setInvoice(invoice);
        invoice.addPayment(payment);
        return mapper.sourceToDestination(paymentRepository.save(payment));
    }

    @Override
    public PaymentDto delete(final String id) {
        Payment payment = findByIdOrThrow(id);
        paymentRepository.deleteById(id);
        Invoice invoice = payment.getInvoice();
        invoice.removePayment(payment);

        return mapper.sourceToDestination(payment);
    }

    private Payment findByIdOrThrow(final String id) {
        return paymentRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Payment with id: " + id));
    }
}
