package com.colvir.szasemkov.homework1.dto.paymentsalary;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(setterPrefix = "set")
public class PaymentSalaryResponse {
    private Integer id;

    private Integer employeeId;

    private Integer amount;

    private Date date;

    @Builder.Default
    private Boolean status = Boolean.FALSE;
}
