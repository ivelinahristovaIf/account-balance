package com.example.accountbalance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable, GenericEntity<Invoice> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {@Parameter(name =
            "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
    private String uid;

    private LocalDateTime issueDate;

    private BigDecimal amount;

    //    private Period servicePeriod;

    @ManyToOne
    @JoinColumn(name = "account_uid")
    private Account account;

    @OneToMany
    private Set<Payment> payments;

    @Override
    public String getUid() {return uid;}

    public void addPayment(final Payment payment) {
        if (this.payments == null) {
            this.payments = new HashSet<>();
        }
        this.payments.add(payment);
    }

    public void removePayment(final Payment payment) {
        if (this.payments == null)
            return;
        this.payments.remove(payment);
    }
}
