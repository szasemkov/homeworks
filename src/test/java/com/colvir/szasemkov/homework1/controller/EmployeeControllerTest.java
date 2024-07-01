package com.colvir.szasemkov.homework1.controller;

import com.colvir.szasemkov.homework1.controller.employee.EmployeeController;
import com.colvir.szasemkov.homework1.dto.ErrorResponse;
import com.colvir.szasemkov.homework1.dto.employee.CreateEmployeeRequest;
import com.colvir.szasemkov.homework1.dto.employee.CreateEmployeeResponse;
import com.colvir.szasemkov.homework1.dto.employee.EmployeePageResponse;
import com.colvir.szasemkov.homework1.dto.employee.EmployeeResponse;
import com.colvir.szasemkov.homework1.dto.employee.UpdateEmployeeRequest;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.service.employee.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.colvir.szasemkov.homework1.generator.EmployeeDtoGenerator.createEmployeeRequestBuilder;
import static com.colvir.szasemkov.homework1.generator.EmployeeDtoGenerator.createEmployeeResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.EmployeeDtoGenerator.employee1ResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.EmployeeDtoGenerator.employee2ResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.EmployeeDtoGenerator.employeePageResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.EmployeeDtoGenerator.employeeResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.EmployeeDtoGenerator.updateEmployeeRequestBuilder;
import static com.colvir.szasemkov.homework1.model.InternalErrorStatus.EMPLOYEE_DOES_NOT_EXIST;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@WithMockUser(username = "user", roles = "USER")
public class EmployeeControllerTest {

    private static final String EMPLOYEE_IS_NOT_FOUND = "Employee with id = %s is not found";

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createEmployee_success() throws Exception {
        //given
        CreateEmployeeRequest request = createEmployeeRequestBuilder()
                .build();

        String stringReq = objectMapper.writeValueAsString(request);
        CreateEmployeeResponse response = createEmployeeResponseBuilder()
                .build();

        when(employeeService.createEmployee(request)).thenReturn(response);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee/create") //send
                        .with(csrf())
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(response.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(response.getDepartment()));

        verify(employeeService).createEmployee(request);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void getAll() throws Exception {
        //given
        EmployeeResponse employee1 = employee1ResponseBuilder().build();
        EmployeeResponse employee2 = employee2ResponseBuilder().build();

        EmployeePageResponse employeePageResponse = employeePageResponseBuilder().build();

        when(employeeService.getAll()).thenReturn(employeePageResponse);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee") //send
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[0].id").value(employee1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[0].firstName").value(employee1.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[0].lastName").value(employee1.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[0].salary").value(employee1.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[0].department").value(employee1.getDepartment()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[1].id").value(employee2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[1].firstName").value(employee2.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[1].lastName").value(employee2.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[1].salary").value(employee2.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.[1].department").value(employee2.getDepartment()));

        verify(employeeService).getAll();
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void getById_success() throws Exception {
        //given
        EmployeeResponse employee = employeeResponseBuilder().build();

        Integer employeeId = employee.getId();

        when(employeeService.getById(employeeId)).thenReturn(employee);

        //when & then
        EmployeeResponse expectedEmployeeResponse = employeeResponseBuilder().build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/" + employeeId) //send
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedEmployeeResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(expectedEmployeeResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(expectedEmployeeResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(expectedEmployeeResponse.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(expectedEmployeeResponse.getDepartment()));

        verify(employeeService).getById(employeeId);
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    void getById_employeeNotFound() throws Exception {
        //given

        Integer wrongEmployeeId = 500;

        when(employeeService.getById(wrongEmployeeId)).thenThrow(new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, wrongEmployeeId)));

        //when & then
        ErrorResponse errorResponse = new ErrorResponse(EMPLOYEE_DOES_NOT_EXIST, String.format(EMPLOYEE_IS_NOT_FOUND, wrongEmployeeId));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/" + wrongEmployeeId) //send
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(employeeService).getById(wrongEmployeeId);
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    void delete_success() throws Exception {
        //given
        EmployeeResponse employee = employeeResponseBuilder().build();

        Integer employeeId = employee.getId();

        when(employeeService.delete(employeeId)).thenReturn(employee);

        //when & then
        EmployeeResponse expectedEmployeeResponse = employeeResponseBuilder().build();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/employee/" + employeeId) //send
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedEmployeeResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(expectedEmployeeResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(expectedEmployeeResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(expectedEmployeeResponse.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(expectedEmployeeResponse.getDepartment()));

        verify(employeeService).delete(employeeId);
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    void delete_employeeNotFound() throws Exception {
        //given

        Integer wrongEmployeeId = 500;

        when(employeeService.delete(wrongEmployeeId)).thenThrow(new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, wrongEmployeeId)));

        //when & then
        ErrorResponse errorResponse = new ErrorResponse(EMPLOYEE_DOES_NOT_EXIST, String.format(EMPLOYEE_IS_NOT_FOUND, wrongEmployeeId));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/employee/" + wrongEmployeeId) //send
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(employeeService).delete(wrongEmployeeId);
        verifyNoMoreInteractions(employeeService);

    }

    @Test
    void update_success() throws Exception {
        //given
        UpdateEmployeeRequest request = updateEmployeeRequestBuilder()
                .build();

        String stringReq = objectMapper.writeValueAsString(request);
        EmployeeResponse response = employeeResponseBuilder()
                .build();

        when(employeeService.update(request)).thenReturn(response);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employee") //send
                        .with(csrf())
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(response.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(response.getDepartment()));

        verify(employeeService).update(request);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void update_employeeNotFound() throws Exception {
        //given
        UpdateEmployeeRequest request = updateEmployeeRequestBuilder()
                .build();

        Integer requestId = request.getId();

        String stringReq = objectMapper.writeValueAsString(request);

        when(employeeService.update(request)).thenThrow(new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, requestId)));

        //when
        ErrorResponse errorResponse = new ErrorResponse(EMPLOYEE_DOES_NOT_EXIST, String.format(EMPLOYEE_IS_NOT_FOUND, requestId));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employee") //send
                        .with(csrf())
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(employeeService).update(request);
        verifyNoMoreInteractions(employeeService);
    }

}