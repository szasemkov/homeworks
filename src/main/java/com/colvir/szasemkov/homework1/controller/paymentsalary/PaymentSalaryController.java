package com.colvir.szasemkov.homework1.controller.paymentsalary;

import com.colvir.szasemkov.homework1.dto.paymentsalary.*;
import com.colvir.szasemkov.homework1.service.paymentsalary.PaymentSalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
