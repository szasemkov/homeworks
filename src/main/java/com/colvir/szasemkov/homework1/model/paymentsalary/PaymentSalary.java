package com.colvir.szasemkov.homework1.model.paymentsalary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSalary {
    private Integer id;

    private Integer employeeId;

    private Integer amount;

    private Date date;

    private Boolean status = Boolean.FALSE;
}
