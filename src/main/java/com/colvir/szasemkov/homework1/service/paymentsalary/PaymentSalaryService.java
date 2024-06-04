package com.colvir.szasemkov.homework1.service.paymentsalary;

import com.colvir.szasemkov.homework1.dto.paymentsalary.*;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.PaymentSalaryNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.WrongSalaryException;
import com.colvir.szasemkov.homework1.mapper.paymentsalary.PaymentSalaryMapper;
import com.colvir.szasemkov.homework1.model.employee.Employee;
import com.colvir.szasemkov.homework1.model.paymentsalary.PaymentSalary;
import com.colvir.szasemkov.homework1.repository.employee.EmployeeRepository;
import com.colvir.szasemkov.homework1.repository.paymentsalary.PaymentSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentSalaryService {
    private static final String EMPLOYEE_IS_NOT_FOUND = "Employee with id = %s is not found";
    private static final String PAYMENT_SALARY_NOT_FOUND = "PaymentSalary with id = %s is not found";

    private final PaymentSalaryMapper paymentSalaryMapper;
    private final PaymentSalaryRepository paymentSalaryRepository;
    private final EmployeeRepository employeeRepository;
    private Random random = new Random();

    public CreatePaymentSalaryResponse createPaymentSalary(CreatePaymentSalaryRequest request) {
        Integer employeeId = request.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, employeeId)));
        PaymentSalary paymentSalary =
                new PaymentSalary(random.nextInt(), employeeId, request.getAmount(), request.getDate(),false);

        paymentSalaryRepository.save(paymentSalary);

        return  paymentSalaryMapper.paymentSalaryToCreatedPaymentSalaryResponse(paymentSalary);
    }

    public PaymentSalaryPageResponse getAll() {
        List<PaymentSalary> allPaymentSalary = paymentSalaryRepository.findAll();
        return paymentSalaryMapper.paymentSalaryToPaymentSalaryPageResponse(allPaymentSalary);
    }


    public PaymentSalaryResponse getById(Integer id) {
        PaymentSalary paymentSalary = paymentSalaryRepository.findById(id)
                .orElseThrow(() -> new PaymentSalaryNotFoundException(String.format(PAYMENT_SALARY_NOT_FOUND, id)));
        return paymentSalaryMapper.paymentSalaryToPaymentSalaryResponse(paymentSalary);
    }

    public PaymentSalaryResponse update(UpdatePaymentSalaryRequest request) {

        Integer employeeId = request.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, employeeId)));

        Integer paymentSalaryId = request.getId();
        PaymentSalary paymentSalary = paymentSalaryRepository.findById(paymentSalaryId)
                .orElseThrow(() -> new PaymentSalaryNotFoundException(String.format(PAYMENT_SALARY_NOT_FOUND, paymentSalaryId)));

        PaymentSalary updatedPaymentSalary = paymentSalaryMapper.updatePaymentSalaryRequestToPaymentSalary(request);

        paymentSalaryRepository.update(updatedPaymentSalary);

        return paymentSalaryMapper.paymentSalaryToPaymentSalaryResponse(updatedPaymentSalary);
    }

    public PaymentSalaryResponse delete(Integer id) {
        PaymentSalary paymentSalary = paymentSalaryRepository.findById(id)
                .orElseThrow(() -> new PaymentSalaryNotFoundException(String.format(PAYMENT_SALARY_NOT_FOUND, id)));

        paymentSalaryRepository.delete(id);

        return paymentSalaryMapper.paymentSalaryToPaymentSalaryResponse(paymentSalary);
    }

    public PaymentSalaryResponse paySalary(Integer id) {

        PaymentSalary paymentSalary = paymentSalaryRepository.findById(id)
                .orElseThrow(() -> new PaymentSalaryNotFoundException(String.format(PAYMENT_SALARY_NOT_FOUND, id)));

        Integer employeeId = paymentSalary.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, employeeId)));

        Integer salary = paymentSalary.getAmount();

        if(!salary.equals(employee.getSalary())) {
            throw new WrongSalaryException();
        }

        paymentSalary.setStatus(true);

        paymentSalaryRepository.update(paymentSalary);

        return paymentSalaryMapper.paymentSalaryToPaymentSalaryResponse(paymentSalary);
    }
}
