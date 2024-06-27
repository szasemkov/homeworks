package com.colvir.szasemkov.homework1.generator;

import com.colvir.szasemkov.homework1.model.paymentsalary.PaymentSalary;

import java.util.Calendar;
import java.util.Date;

public class PaymentSalaryGenerator {

    private static final Integer PAYMENT_SALARY_ID = 1;
    private static final Integer EMPLOYEE_ID = 1;
    private static final Integer AMOUNT = 300;
    private static final Date DATE = new Date(2024, Calendar.JUNE, 4);

    private static final Integer PAYMENT_SALARY_ID1 = 1;
    private static final Integer EMPLOYEE_ID1 = 11;
    private static final Integer AMOUNT1 = 500;
    private static final Date DATE1 = new Date(2024, Calendar.JUNE, 4);

    private static final Integer PAYMENT_SALARY_ID2 = 1;
    private static final Integer EMPLOYEE_ID2 = 12;
    private static final Integer AMOUNT2 = 400;
    private static final Date DATE2 = new Date(2024, Calendar.JUNE, 26);


    public static PaymentSalary.PaymentSalaryBuilder generatePaymentSalaryAfterCreateBuilder() {
        return PaymentSalary.builder()
                .setId(PAYMENT_SALARY_ID)
                .setEmployeeId(EMPLOYEE_ID)
                .setAmount(AMOUNT)
                .setDate(DATE)
                .setStatus(Boolean.FALSE);
    }

    public static PaymentSalary.PaymentSalaryBuilder generatePaymentSalary1() {
        return PaymentSalary.builder()
                .setId(PAYMENT_SALARY_ID1)
                .setEmployeeId(EMPLOYEE_ID1)
                .setAmount(AMOUNT1)
                .setDate(DATE1)
                .setStatus(Boolean.TRUE);
    }

    public static PaymentSalary.PaymentSalaryBuilder generatePaymentSalary2() {
        return PaymentSalary.builder()
                .setId(PAYMENT_SALARY_ID2)
                .setEmployeeId(EMPLOYEE_ID2)
                .setAmount(AMOUNT2)
                .setDate(DATE2)
                .setStatus(Boolean.FALSE);
    }

}
