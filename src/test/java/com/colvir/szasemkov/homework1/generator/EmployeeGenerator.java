package com.colvir.szasemkov.homework1.generator;

import com.colvir.szasemkov.homework1.model.employee.Employee;

public class EmployeeGenerator {

    public static final Integer EMPLOYEE_ID = 1;
    public static final String EMPLOYEE_FIRST_NAME = "FIRST_NAME";
    public static final String EMPLOYEE_LAST_NAME = "LAST_NAME";
    private static final Integer EMPLOYEE_SALARY = 100;
    private static final String EMPLOYEE_DEPARTMENT = "DEP";

    public static final Integer EMPLOYEE_ID1 = 11;
    private static final String EMPLOYEE_FIRST_NAME1 = "Ivan";
    private static final String EMPLOYEE_LAST_NAME1 = "Ivanov";
    private static final Integer EMPLOYEE_SALARY1 = 500;
    private static final String EMPLOYEE_DEPARTMENT1 = "Department1";

    public static final Integer EMPLOYEE_ID2 = 12;
    private static final String EMPLOYEE_FIRST_NAME2 = "Petr";
    private static final String EMPLOYEE_LAST_NAME2 = "Petrov";
    private static final Integer EMPLOYEE_SALARY2 = 400;
    private static final String EMPLOYEE_DEPARTMENT2 = "Department2";


    public static Employee.EmployeeBuilder generateEmployeeAfterCreateBuilder() {
        return Employee.builder()
                .setId(EMPLOYEE_ID)
                .setFirstName(EMPLOYEE_FIRST_NAME)
                .setLastName(EMPLOYEE_LAST_NAME)
                .setSalary(EMPLOYEE_SALARY)
                .setDepartment(EMPLOYEE_DEPARTMENT);
    }

    public static Employee.EmployeeBuilder generateEmployee1() {
        return Employee.builder()
                .setId(EMPLOYEE_ID1)
                .setFirstName(EMPLOYEE_FIRST_NAME1)
                .setLastName(EMPLOYEE_LAST_NAME1)
                .setSalary(EMPLOYEE_SALARY1)
                .setDepartment(EMPLOYEE_DEPARTMENT1);
    }

    public static Employee.EmployeeBuilder generateEmployee2() {
        return Employee.builder()
                .setId(EMPLOYEE_ID2)
                .setFirstName(EMPLOYEE_FIRST_NAME2)
                .setLastName(EMPLOYEE_LAST_NAME2)
                .setSalary(EMPLOYEE_SALARY2)
                .setDepartment(EMPLOYEE_DEPARTMENT2);
    }

}
