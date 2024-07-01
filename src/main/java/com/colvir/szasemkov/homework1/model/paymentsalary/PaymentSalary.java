package com.colvir.szasemkov.homework1.model.paymentsalary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Table(name = "payments")
@Entity
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_payment")
    @SequenceGenerator(name = "seq_payment", sequenceName = "seq_payment", allocationSize = 1)
    private Integer id;

    private Integer employeeId;

    private Integer amount;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Builder.Default
    private Boolean status = Boolean.FALSE;

    public PaymentSalary(Integer employeeId, Integer amount, Date date, Boolean status) {
        this.employeeId = employeeId;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }
}
