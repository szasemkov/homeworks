package com.colvir.szasemkov.homework1.exception.paymentsalary;

public class WrongSalaryException  extends RuntimeException{

    public WrongSalaryException() {
        super("Salary in payment is not correspond the employee's salary");
    }
}
