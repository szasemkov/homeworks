package com.colvir.szasemkov.homework1.mapper.employee;

import com.colvir.szasemkov.homework1.dto.employee.CreateEmployeeResponse;
import com.colvir.szasemkov.homework1.dto.employee.EmployeePageResponse;
import com.colvir.szasemkov.homework1.dto.employee.EmployeeResponse;
import com.colvir.szasemkov.homework1.dto.employee.UpdateEmployeeRequest;
import com.colvir.szasemkov.homework1.model.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmployeeMapper {

    CreateEmployeeResponse employeeToCreatedEmployeeResponse(Employee employee);

    EmployeeResponse employeeToEmployeeResponse(Employee employee);

    List<EmployeeResponse> employeesToEmployeeResponse(List<Employee> employees);

    Employee updateEmployeeRequestToEmployee(UpdateEmployeeRequest request);

    default EmployeePageResponse employeeToEmployeePageResponse(List<Employee> employees) {
        List<EmployeeResponse> employeeResponses = employeesToEmployeeResponse(employees);
        return new EmployeePageResponse(employeeResponses);
    }
}
