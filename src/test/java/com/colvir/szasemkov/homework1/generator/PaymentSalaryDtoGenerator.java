package com.colvir.szasemkov.homework1.generator;

import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryPageResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.UpdatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.model.paymentsalary.PaymentSalary;

import java.util.List;

public class PaymentSalaryDtoGenerator {

    public static CreatePaymentSalaryRequest.CreatePaymentSalaryRequestBuilder createPaymentSalaryRequestBuilder() {
        PaymentSalary paymentSalary = PaymentSalaryGenerator.generatePaymentSalaryAfterCreateBuilder()
                .build();

        return CreatePaymentSalaryRequest.builder()
                .setEmployeeId(paymentSalary.getEmployeeId())
                .setAmount(paymentSalary.getAmount())
                .setDate(paymentSalary.getDate());

    }

    public static CreatePaymentSalaryResponse.CreatePaymentSalaryResponseBuilder createPaymentSalaryResponseBuilder() {
        PaymentSalary paymentSalary = PaymentSalaryGenerator.generatePaymentSalaryAfterCreateBuilder()
                .build();

        return  CreatePaymentSalaryResponse.builder()
                .setId(paymentSalary.getId())
                .setEmployeeId(paymentSalary.getEmployeeId())
                .setAmount(paymentSalary.getAmount())
                .setDate(paymentSalary.getDate());
    }

    public static PaymentSalaryResponse.PaymentSalaryResponseBuilder paymentSalaryResponseBuilder() {
        PaymentSalary paymentSalary = PaymentSalaryGenerator.generatePaymentSalaryAfterCreateBuilder()
                .build();

        return  PaymentSalaryResponse.builder()
                .setId(paymentSalary.getId())
                .setEmployeeId(paymentSalary.getEmployeeId())
                .setAmount(paymentSalary.getAmount())
                .setDate(paymentSalary.getDate())
                .setStatus(paymentSalary.getStatus());
    }

    public static UpdatePaymentSalaryRequest.UpdatePaymentSalaryRequestBuilder updatePaymentSalaryRequestBuilder() {
        PaymentSalary paymentSalary = PaymentSalaryGenerator.generatePaymentSalaryAfterCreateBuilder()
                .build();

        return  UpdatePaymentSalaryRequest.builder()
                .setId(paymentSalary.getId())
                .setEmployeeId(paymentSalary.getEmployeeId())
                .setAmount(paymentSalary.getAmount())
                .setDate(paymentSalary.getDate());
    }

    public static PaymentSalaryResponse.PaymentSalaryResponseBuilder paymentSalary1ResponseBuilder() {
        PaymentSalary paymentSalary = PaymentSalaryGenerator.generatePaymentSalary1()
                .build();

        return  PaymentSalaryResponse.builder()
                .setId(paymentSalary.getId())
                .setEmployeeId(paymentSalary.getEmployeeId())
                .setAmount(paymentSalary.getAmount())
                .setDate(paymentSalary.getDate())
                .setStatus(paymentSalary.getStatus());
    }

    public static PaymentSalaryResponse.PaymentSalaryResponseBuilder paymentSalary2ResponseBuilder() {
        PaymentSalary paymentSalary = PaymentSalaryGenerator.generatePaymentSalary2()
                .build();

        return  PaymentSalaryResponse.builder()
                .setId(paymentSalary.getId())
                .setEmployeeId(paymentSalary.getEmployeeId())
                .setAmount(paymentSalary.getAmount())
                .setDate(paymentSalary.getDate())
                .setStatus(paymentSalary.getStatus());
    }

    public static PaymentSalaryPageResponse.PaymentSalaryPageResponseBuilder paymentSalaryPageResponseBuilder(){
        PaymentSalaryResponse paymentSalaryResponse1 = paymentSalary1ResponseBuilder().build();
        PaymentSalaryResponse paymentSalaryResponse2 = paymentSalary2ResponseBuilder().build();

        return  PaymentSalaryPageResponse.builder()
                .setPaymentSalaries(List.of(paymentSalaryResponse1, paymentSalaryResponse2));
    }

}
