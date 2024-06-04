package com.colvir.szasemkov.homework1.dto.paymentsalary;

import lombok.Data;

import java.util.Date;

@Data
public class CreatePaymentSalaryResponse {
    private Integer id;

    private Integer employeeId;

    private Integer amount;

    private Date date;
}
