package com.colvir.szasemkov.homework1.model.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Integer id;

    private String firstName;

    private String lastName;

    private Integer salary;

    private String department;

}
