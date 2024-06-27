package com.colvir.szasemkov.homework1.dto.employee;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class CreateEmployeeResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private Integer salary;

    private String department;
}
