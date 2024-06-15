package com.colvir.szasemkov.homework1.dto.employee;

import lombok.Data;

@Data
public class CreateEmployeeRequest {

    private String firstName;

    private String lastName;

    private Integer salary;

    private String department;
}
