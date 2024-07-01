package com.colvir.szasemkov.homework1.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(setterPrefix = "set")
@AllArgsConstructor
public class EmployeePageResponse {

    private List<EmployeeResponse> employee;
}
