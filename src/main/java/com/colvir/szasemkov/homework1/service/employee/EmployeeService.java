package com.colvir.szasemkov.homework1.service.employee;

import com.colvir.szasemkov.homework1.dto.employee.*;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.mapper.employee.EmployeeMapper;
import com.colvir.szasemkov.homework1.model.employee.Employee;
import com.colvir.szasemkov.homework1.repository.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private static final String EMPLOYEE_IS_NOT_FOUND = "Employee with id = %s is not found";
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public CreateEmployeeResponse createEmployee(CreateEmployeeRequest request) {

        Employee employee =
                new Employee(request.getFirstName(), request.getLastName(), request.getSalary(),
                        request.getDepartment());

        employeeRepository.save(employee);

        return  employeeMapper.employeeToCreatedEmployeeResponse(employee);
    }

    public EmployeePageResponse getAll() {
        List<Employee> allEmployee = employeeRepository.findAll();
        return employeeMapper.employeeToEmployeePageResponse(allEmployee);
    }


    public EmployeeResponse getById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, id)));
        return employeeMapper.employeeToEmployeeResponse(employee);
    }

    public EmployeeResponse update(UpdateEmployeeRequest request) {
        Integer employeeId = request.getId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, employeeId)));

        Employee updatedEmployee = employeeMapper.updateEmployeeRequestToEmployee(request);

        employeeRepository.update(updatedEmployee);

        return employeeMapper.employeeToEmployeeResponse(updatedEmployee);
    }

    public EmployeeResponse delete(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, id)));

        employeeRepository.delete(id);

        return employeeMapper.employeeToEmployeeResponse(employee);
    }
}
