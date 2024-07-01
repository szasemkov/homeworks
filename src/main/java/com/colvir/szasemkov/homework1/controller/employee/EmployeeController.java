package com.colvir.szasemkov.homework1.controller.employee;

import com.colvir.szasemkov.homework1.dto.employee.CreateEmployeeRequest;
import com.colvir.szasemkov.homework1.dto.employee.CreateEmployeeResponse;
import com.colvir.szasemkov.homework1.dto.employee.EmployeePageResponse;
import com.colvir.szasemkov.homework1.dto.employee.EmployeeResponse;
import com.colvir.szasemkov.homework1.dto.employee.UpdateEmployeeRequest;
import com.colvir.szasemkov.homework1.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("create")
    public CreateEmployeeResponse createEmployee(@RequestBody CreateEmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

    @GetMapping
    public EmployeePageResponse getAll() {
        return employeeService.getAll();
    }


    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable("id") Integer id) {
        return employeeService.getById(id);
    }

    @PutMapping
    public EmployeeResponse update(@RequestBody UpdateEmployeeRequest request) {
        return employeeService.update(request);
    }

    @DeleteMapping("/{id}")
    public EmployeeResponse delete(@PathVariable("id") Integer id) {
        return employeeService.delete(id);
    }
}
