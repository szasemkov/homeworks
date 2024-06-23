package com.colvir.szasemkov.homework1.repository.employee;

import com.colvir.szasemkov.homework1.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
