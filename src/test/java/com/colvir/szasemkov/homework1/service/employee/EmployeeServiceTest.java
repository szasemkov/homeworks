package com.colvir.szasemkov.homework1.service.employee;

import com.colvir.szasemkov.homework1.config.TestConfig;
import com.colvir.szasemkov.homework1.dto.employee.*;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.mapper.employee.EmployeeMapper;
import com.colvir.szasemkov.homework1.model.employee.Employee;
import com.colvir.szasemkov.homework1.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.colvir.szasemkov.homework1.generator.EmployeeDtoGenerator.*;
import static com.colvir.szasemkov.homework1.generator.EmployeeGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        EmployeeService.class,
        EmployeeMapper.class
})
@SpringBootTest(classes = {TestConfig.class})
class EmployeeServiceTest {

    public static final Integer WRONG_EMPLOYEE_ID = 1;
    public static final String WRONG_EMPLOYEE_MESSAGE = String.format("Employee with id = %s is not found", WRONG_EMPLOYEE_ID);

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;


    @Test
     void createEmployee() {
        //given
        CreateEmployeeRequest request = createEmployeeRequestBuilder().build();

        when(employeeRepository.save(any())).thenReturn(any());

        //when
        CreateEmployeeResponse actualResponse = employeeService.createEmployee(request);

        //then
        CreateEmployeeResponse expectedResponse = createEmployeeResponseBuilder().build();

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
        Employee employee1 = generateEmployee1().build();
        Employee employee2 = generateEmployee2().build();

        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));

        //when
        EmployeePageResponse actualResponse = employeeService.getAll();

        //then
        EmployeeResponse employeeResponse1 = employee1ResponseBuilder().build();
        EmployeeResponse employeeResponse2 = employee2ResponseBuilder().build();

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
        Employee employee = generateEmployeeAfterCreateBuilder().build();

        Integer employeeId = employee.getId();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        //when
        EmployeeResponse actualResponse = employeeService.getById(employeeId);

        //then
        EmployeeResponse expectedEmployeeResponse = employeeResponseBuilder().build();

        assertThat(actualResponse).isEqualTo(expectedEmployeeResponse);

        verify(employeeRepository).findById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void getById_employeeNotFound() {
        //given
        when(employeeRepository.findById(WRONG_EMPLOYEE_ID)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.getById(WRONG_EMPLOYEE_ID));
        assertEquals(WRONG_EMPLOYEE_MESSAGE, exception.getMessage());

        verify(employeeRepository).findById(WRONG_EMPLOYEE_ID);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void update() {
        //given
        UpdateEmployeeRequest request = updateEmployeeRequestBuilder().build();

        Integer requestEmployeeId = request.getId();

        Employee employee = generateEmployeeAfterCreateBuilder().build();

        when(employeeRepository.findById(requestEmployeeId)).thenReturn(Optional.of(employee));

        //when
        EmployeeResponse actualResponse = employeeService.update(request);

        //then
        EmployeeResponse expectedEmployeeResponse = employeeResponseBuilder().build();
        assertThat(actualResponse).isEqualTo(expectedEmployeeResponse);

        verify(employeeRepository).findById(requestEmployeeId);
        verify(employeeRepository).save(employee);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void update_employeeNotFound() {
        //given
        UpdateEmployeeRequest request = updateEmployeeRequestBuilder().build();

        Integer requestEmployeeId = request.getId();

        when(employeeRepository.findById(requestEmployeeId)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.update(request));
        String expectedMessage = String.format("Employee with id = %s is not found", requestEmployeeId);
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(requestEmployeeId);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void delete() {
        //given
        Employee employee = generateEmployeeAfterCreateBuilder().build();

        Integer employeeId = employee.getId();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        //when
        EmployeeResponse actualResponse = employeeService.delete(employeeId);

        //then
        EmployeeResponse expectedEmployeeResponse = employeeResponseBuilder().build();
        assertThat(actualResponse).isEqualTo(expectedEmployeeResponse);

        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository).deleteById(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }


    @Test
    void delete_employeeNotFound() {
        //given
        when(employeeRepository.findById(WRONG_EMPLOYEE_ID)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.delete(WRONG_EMPLOYEE_ID));
        assertEquals(WRONG_EMPLOYEE_MESSAGE, exception.getMessage());

        verify(employeeRepository).findById(WRONG_EMPLOYEE_ID);
        verifyNoMoreInteractions(employeeRepository);
    }

}