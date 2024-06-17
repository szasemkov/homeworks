package com.colvir.szasemkov.homework1.service.employee;

import com.colvir.szasemkov.homework1.dto.employee.*;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.mapper.employee.EmployeeMapperImpl;
import com.colvir.szasemkov.homework1.model.employee.Employee;
import com.colvir.szasemkov.homework1.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        EmployeeService.class,
        EmployeeMapperImpl.class
})
class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;


    @Test
    void createEmployee() {
        //given
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("Ivan");
        request.setLastName("Ivanov");
        request.setSalary(500);
        request.setDepartment("Department1");

        when(employeeRepository.save(any())).thenReturn(any());

        //when
        CreateEmployeeResponse actualResponse = employeeService.createEmployee(request);

        //then
        CreateEmployeeResponse expectedResponse = new CreateEmployeeResponse();
        expectedResponse.setFirstName("Ivan");
        expectedResponse.setLastName("Ivanov");
        expectedResponse.setSalary(500);
        expectedResponse.setDepartment("Department1");

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedResponse);

        verify(employeeRepository).save(any());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void getAll() {
        //given
        Employee employee1 = new Employee("Ivan", "Ivanov", 500, "Department1");
        Employee employee2 = new Employee("Petr", "Petrov", 400, "Department2");

        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));

        //when
        EmployeePageResponse actualResponse = employeeService.getAll();

        //then
        EmployeeResponse employeeResponse1 = new EmployeeResponse();
        employeeResponse1.setFirstName("Ivan");
        employeeResponse1.setLastName("Ivanov");
        employeeResponse1.setSalary(500);
        employeeResponse1.setDepartment("Department1");
        EmployeeResponse employeeResponse2 = new EmployeeResponse();
        employeeResponse2.setFirstName("Petr");
        employeeResponse2.setLastName("Petrov");
        employeeResponse2.setSalary(400);
        employeeResponse2.setDepartment("Department2");
        List<EmployeeResponse> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(employeeResponse1);
        expectedEmployees.add(employeeResponse2);
        EmployeePageResponse expectedResponse = new EmployeePageResponse(expectedEmployees);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(employeeRepository).findAll();
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void getById() {
        //given
        Employee employee = new Employee("Ivan", "Ivanov", 500, "Department1");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //when
        EmployeeResponse actualResponse = employeeService.getById(1);

        //then
        EmployeeResponse expectedEmployeeResponse = new EmployeeResponse();
        expectedEmployeeResponse.setFirstName("Ivan");
        expectedEmployeeResponse.setLastName("Ivanov");
        expectedEmployeeResponse.setSalary(500);
        expectedEmployeeResponse.setDepartment("Department1");

        assertThat(actualResponse).isEqualTo(expectedEmployeeResponse);

        verify(employeeRepository).findById(1);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void getById_employeeNotFound() {
        //given
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.getById(1));
        String expectedMessage = "Employee with id = 1 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(1);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void update() {
        //given
        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setId(1);
        request.setFirstName("Ivan");
        request.setLastName("Ivanov");
        request.setSalary(500);
        request.setDepartment("Department1");

        Employee employee = new Employee("Ivan", "Ivanov", 500, "Department1");
        employee.setId(1);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //when
        EmployeeResponse actualResponse = employeeService.update(request);

        //then
        EmployeeResponse expectedEmployeeResponse = new EmployeeResponse();
        expectedEmployeeResponse.setId(1);
        expectedEmployeeResponse.setFirstName("Ivan");
        expectedEmployeeResponse.setLastName("Ivanov");
        expectedEmployeeResponse.setSalary(500);
        expectedEmployeeResponse.setDepartment("Department1");

        assertThat(actualResponse).isEqualTo(expectedEmployeeResponse);

        verify(employeeRepository).findById(1);
        verify(employeeRepository).update(employee);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void update_employeeNotFound() {
        //given
        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setId(1);
        request.setFirstName("Ivan");
        request.setLastName("Ivanov");
        request.setSalary(500);
        request.setDepartment("Department1");

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.update(request));
        String expectedMessage = "Employee with id = 1 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(1);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void delete() {
        //given
        Employee employee1 = new Employee("Ivan", "Ivanov", 500, "Department1");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee1));

        //when
        EmployeeResponse actualResponse = employeeService.delete(1);

        //then
        EmployeeResponse expectedEmployeeResponse = new EmployeeResponse();
        expectedEmployeeResponse.setFirstName("Ivan");
        expectedEmployeeResponse.setLastName("Ivanov");
        expectedEmployeeResponse.setSalary(500);
        expectedEmployeeResponse.setDepartment("Department1");

        assertThat(actualResponse).isEqualTo(expectedEmployeeResponse);

        verify(employeeRepository).findById(1);
        verify(employeeRepository).delete(1);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void delete_employeeNotFound() {
        //given
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.delete(1));
        String expectedMessage = "Employee with id = 1 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(1);
        verifyNoMoreInteractions(employeeRepository);
    }

}