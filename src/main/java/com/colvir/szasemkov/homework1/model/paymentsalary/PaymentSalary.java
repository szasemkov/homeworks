package com.colvir.szasemkov.homework1.model.paymentsalary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Table(name = "payments")
@Entity
@NoArgsConstructor
public class PaymentSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "seq", allocationSize = 1)
    private Integer id;

    private Integer employeeId;

    private Integer amount;

    private Date date;

    private Boolean status = Boolean.FALSE;

    public PaymentSalary(Integer employeeId, Integer amount, Date date, Boolean status) {
        this.employeeId = employeeId;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }
}
