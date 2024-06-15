package com.colvir.szasemkov.homework1.dto.paymentsalary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentSalaryPageResponse {
    private List<PaymentSalaryResponse> paymentSalaries;
}
