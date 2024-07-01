package com.colvir.szasemkov.homework1.controller.paymentsalary;

import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryPageResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.UpdatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.service.paymentsalary.PaymentSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment-salary")
@RequiredArgsConstructor
public class PaymentSalaryController {
    private final PaymentSalaryService paymentSalaryService;

    @PostMapping("create")
    public CreatePaymentSalaryResponse createPaymentSalary(@RequestBody CreatePaymentSalaryRequest request) {
        return paymentSalaryService.createPaymentSalary(request);
    }

    @GetMapping
    public PaymentSalaryPageResponse getAll() {
        return paymentSalaryService.getAll();
    }


    @GetMapping("/{id}")
    public PaymentSalaryResponse getById(@PathVariable("id") Integer id) {
        return paymentSalaryService.getById(id);
    }

    @PutMapping
    public PaymentSalaryResponse update(@RequestBody UpdatePaymentSalaryRequest request) {
        return paymentSalaryService.update(request);
    }

    @DeleteMapping("/{id}")
    public PaymentSalaryResponse delete(@PathVariable("id") Integer id) {
        return paymentSalaryService.delete(id);
    }

    @PutMapping("/{id}")
    public PaymentSalaryResponse paySalary(@PathVariable("id") Integer id) {
        return paymentSalaryService.paySalary(id);
    }
}
