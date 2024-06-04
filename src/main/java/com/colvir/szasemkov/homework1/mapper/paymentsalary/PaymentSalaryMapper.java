package com.colvir.szasemkov.homework1.mapper.paymentsalary;

import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryPageResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.UpdatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.model.paymentsalary.PaymentSalary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface PaymentSalaryMapper {
    CreatePaymentSalaryResponse paymentSalaryToCreatedPaymentSalaryResponse(PaymentSalary paymentSalary);

    PaymentSalaryResponse paymentSalaryToPaymentSalaryResponse(PaymentSalary paymentSalary);

    List<PaymentSalaryResponse> paymentSalarysToPaymentSalaryResponse(List<PaymentSalary> paymentSalarys);

    @Mapping(target = "status", ignore = true)
    PaymentSalary updatePaymentSalaryRequestToPaymentSalary(UpdatePaymentSalaryRequest request);

    default PaymentSalaryPageResponse paymentSalaryToPaymentSalaryPageResponse(List<PaymentSalary> paymentSalarys) {
        List<PaymentSalaryResponse> paymentSalaryResponses = paymentSalarysToPaymentSalaryResponse(paymentSalarys);
        return new PaymentSalaryPageResponse(paymentSalaryResponses);
    }
}
