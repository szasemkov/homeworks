package com.colvir.szasemkov.homework1.dto.paymentsalary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(setterPrefix = "set")
@AllArgsConstructor
public class PaymentSalaryPageResponse {
    private List<PaymentSalaryResponse> paymentSalaries;
}
