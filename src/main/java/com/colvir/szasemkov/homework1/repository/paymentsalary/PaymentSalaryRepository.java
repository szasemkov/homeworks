package com.colvir.szasemkov.homework1.repository.paymentsalary;

import com.colvir.szasemkov.homework1.model.paymentsalary.PaymentSalary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentSalaryRepository {
    private final Set<PaymentSalary> paymentSalaries = new HashSet<>();

    public PaymentSalary save(PaymentSalary employee) {
        paymentSalaries.add(employee);
        return employee;
    }

    public List<PaymentSalary> findAll() {
        return new ArrayList<>(paymentSalaries);
    }


    public Optional<PaymentSalary> findById(Integer id) {
        return paymentSalaries.stream()
                .filter(paymentSalary -> paymentSalary.getId().equals(id))
                .findFirst();
    }

    public PaymentSalary update(PaymentSalary paymentSalaryForUpdate) {
        for (PaymentSalary paymentSalary : paymentSalaries) {
            if (paymentSalary.getId().equals(paymentSalaryForUpdate.getId())) {
                paymentSalary.setEmployeeId(paymentSalaryForUpdate.getEmployeeId());
                paymentSalary.setAmount(paymentSalaryForUpdate.getAmount());
                paymentSalary.setDate(paymentSalaryForUpdate.getDate());
            }
        }
        return paymentSalaryForUpdate;
    }

    public PaymentSalary delete(Integer id) {
        PaymentSalary paymentSalaryForDelete = paymentSalaries.stream()
                .filter(paymentSalary -> paymentSalary.getId().equals(id))
                .findFirst().get();
        paymentSalaries.remove(paymentSalaryForDelete);
        return paymentSalaryForDelete;
    }
}
