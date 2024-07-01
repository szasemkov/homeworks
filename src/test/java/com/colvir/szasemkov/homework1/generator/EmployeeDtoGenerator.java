package com.colvir.szasemkov.homework1.generator;

import com.colvir.szasemkov.homework1.dto.employee.CreateEmployeeRequest;
import com.colvir.szasemkov.homework1.dto.employee.CreateEmployeeResponse;
import com.colvir.szasemkov.homework1.dto.employee.EmployeePageResponse;
import com.colvir.szasemkov.homework1.dto.employee.EmployeeResponse;
import com.colvir.szasemkov.homework1.dto.employee.UpdateEmployeeRequest;
import com.colvir.szasemkov.homework1.model.employee.Employee;

import java.util.List;

public class EmployeeDtoGenerator {

    public static CreateEmployeeRequest.CreateEmployeeRequestBuilder createEmployeeRequestBuilder() {
        Employee employee = EmployeeGenerator.generateEmployeeAfterCreateBuilder()
                .build();

        return CreateEmployeeRequest.builder()
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setSalary(employee.getSalary())
                .setDepartment(employee.getDepartment());
    }

    public static CreateEmployeeResponse.CreateEmployeeResponseBuilder createEmployeeResponseBuilder() {
        Employee employee = EmployeeGenerator.generateEmployeeAfterCreateBuilder()
                .build();

        return  CreateEmployeeResponse.builder()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setSalary(employee.getSalary())
                .setDepartment(employee.getDepartment());
    }

    public static EmployeeResponse.EmployeeResponseBuilder employeeResponseBuilder(){
        Employee employee = EmployeeGenerator.generateEmployeeAfterCreateBuilder()
                .build();

        return EmployeeResponse.builder()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setSalary(employee.getSalary())
                .setDepartment(employee.getDepartment());
    }

    public static EmployeeResponse.EmployeeResponseBuilder employee1ResponseBuilder(){
        Employee employee = EmployeeGenerator.generateEmployee1()
                .build();

        return EmployeeResponse.builder()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setSalary(employee.getSalary())
                .setDepartment(employee.getDepartment());
    }

    public static EmployeeResponse.EmployeeResponseBuilder employee2ResponseBuilder(){
        Employee employee = EmployeeGenerator.generateEmployee2()
                .build();

        return EmployeeResponse.builder()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setSalary(employee.getSalary())
                .setDepartment(employee.getDepartment());
    }

    public static UpdateEmployeeRequest.UpdateEmployeeRequestBuilder updateEmployeeRequestBuilder(){
        Employee employee = EmployeeGenerator.generateEmployeeAfterCreateBuilder()
                .build();

        return UpdateEmployeeRequest.builder()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setSalary(employee.getSalary())
                .setDepartment(employee.getDepartment());
    }

    public static EmployeePageResponse.EmployeePageResponseBuilder employeePageResponseBuilder(){
        EmployeeResponse employee1 = employee1ResponseBuilder().build();
        EmployeeResponse employee2 = employee2ResponseBuilder().build();

        return EmployeePageResponse.builder()
                .setEmployee(List.of(employee1,employee2));
    }

}
