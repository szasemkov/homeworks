package com.colvir.szasemkov.homework1.controller.employee;

import com.colvir.szasemkov.homework1.dto.employee.*;
import com.colvir.szasemkov.homework1.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
