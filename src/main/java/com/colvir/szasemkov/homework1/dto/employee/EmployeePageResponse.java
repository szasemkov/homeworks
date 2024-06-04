package com.colvir.szasemkov.homework1.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeePageResponse {

    private List<EmployeeResponse> employee;
}
