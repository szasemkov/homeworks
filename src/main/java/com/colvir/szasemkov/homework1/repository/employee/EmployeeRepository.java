package com.colvir.szasemkov.homework1.repository.employee;

import com.colvir.szasemkov.homework1.model.employee.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EmployeeRepository {
    private final Set<Employee> employees = new HashSet<>();

    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }


    public Optional<Employee> findById(Integer id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    public Employee update(Employee employeeForUpdate) {
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeForUpdate.getId())) {
                employee.setFirstName(employeeForUpdate.getFirstName());
                employee.setLastName(employeeForUpdate.getLastName());
                employee.setSalary(employeeForUpdate.getSalary());
                employee.setDepartment(employeeForUpdate.getDepartment());
            }
        }
        return employeeForUpdate;
    }

    public Employee delete(Integer id) {
        Employee employeeForDelete = employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst().get();
        employees.remove(employeeForDelete);
        return employeeForDelete;
    }

}
