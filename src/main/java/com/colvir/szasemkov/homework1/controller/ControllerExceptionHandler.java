package com.colvir.szasemkov.homework1.controller;

import com.colvir.szasemkov.homework1.dto.ErrorResponse;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.PaymentSalaryNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.WrongSalaryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.colvir.szasemkov.homework1.model.InternalErrorStatus.EMPLOYEE_DOES_NOT_EXIST;
import static com.colvir.szasemkov.homework1.model.InternalErrorStatus.PAYMENT_SALARY_DOES_NOT_EXIST;
import static com.colvir.szasemkov.homework1.model.InternalErrorStatus.WRONG_SALARY;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLinkNotFoundException(EmployeeNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(EMPLOYEE_DOES_NOT_EXIST, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentSalaryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentSalaryNotFoundException(PaymentSalaryNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(PAYMENT_SALARY_DOES_NOT_EXIST, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongSalaryException.class)
    public ResponseEntity<ErrorResponse> handleWrongSalaryException(WrongSalaryException e) {
        ErrorResponse errorResponse = new ErrorResponse(WRONG_SALARY, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
