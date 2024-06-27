package com.colvir.szasemkov.homework1.controller;


import com.colvir.szasemkov.homework1.controller.paymentsalary.PaymentSalaryController;
import com.colvir.szasemkov.homework1.dto.ErrorResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryPageResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.UpdatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.PaymentSalaryNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.WrongSalaryException;
import com.colvir.szasemkov.homework1.service.paymentsalary.PaymentSalaryService;
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

import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.createPaymentSalaryRequestBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.createPaymentSalaryResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.paymentSalary1ResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.paymentSalary2ResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.paymentSalaryPageResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.paymentSalaryResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.updatePaymentSalaryRequestBuilder;
import static com.colvir.szasemkov.homework1.model.InternalErrorStatus.EMPLOYEE_DOES_NOT_EXIST;
import static com.colvir.szasemkov.homework1.model.InternalErrorStatus.PAYMENT_SALARY_DOES_NOT_EXIST;
import static com.colvir.szasemkov.homework1.model.InternalErrorStatus.WRONG_SALARY;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentSalaryController.class)
@WithMockUser(username = "user", roles = "USER")
public class PaymentSalaryControllerTest {

    private static final String MESSAGE_WRONG_SALARY ="Salary in payment is not correspond the employee's salary";
    private static final String EMPLOYEE_IS_NOT_FOUND = "Employee with id = %s is not found";
    private static final String PAYMENT_SALARY_NOT_FOUND = "PaymentSalary with id = %s is not found";

    public static final Integer WRONG_PAYMENT_SALARY_ID = 500;
    public static final String WRONG_PAYMENT_SALARY_MESSAGE = String.format("PaymentSalary with id = %s is not found", WRONG_PAYMENT_SALARY_ID);

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PaymentSalaryController paymentSalaryController;

    @MockBean
    private PaymentSalaryService paymentSalaryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createPaymentSalary_success() throws Exception {
        //given
        CreatePaymentSalaryRequest request = createPaymentSalaryRequestBuilder()
                .build();

        String stringRequest = objectMapper.writeValueAsString(request);

        CreatePaymentSalaryResponse response = createPaymentSalaryResponseBuilder()
                .build();

        when(paymentSalaryService.createPaymentSalary(request)).thenReturn(response);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/payment-salary/create") //send
                        .with(csrf())
                        .content(stringRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(response.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(response.getAmount()));

        verify(paymentSalaryService).createPaymentSalary(request);
        verifyNoMoreInteractions(paymentSalaryService);

    }

    @Test
    void createPaymentSalary_employeeNotFound() throws Exception {
        //given
        CreatePaymentSalaryRequest request = createPaymentSalaryRequestBuilder()
                .build();

        String stringRequest = objectMapper.writeValueAsString(request);

        when(paymentSalaryService.createPaymentSalary(request)).thenThrow(
                new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, request.getEmployeeId())));

        //when
        ErrorResponse errorResponse = new ErrorResponse(EMPLOYEE_DOES_NOT_EXIST, String.format(EMPLOYEE_IS_NOT_FOUND, request.getEmployeeId()));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/payment-salary/create") //send
                        .with(csrf())
                        .content(stringRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(paymentSalaryService).createPaymentSalary(request);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void getAll() throws Exception {
        //given
        PaymentSalaryResponse paymentSalaryResponse1 = paymentSalary1ResponseBuilder().build();
        PaymentSalaryResponse paymentSalaryResponse2 = paymentSalary2ResponseBuilder().build();

        PaymentSalaryPageResponse paymentSalaryPageResponse = paymentSalaryPageResponseBuilder().build();

        when(paymentSalaryService.getAll()).thenReturn(paymentSalaryPageResponse);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/payment-salary") //send
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSalaries.[0].id").value(paymentSalaryResponse1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSalaries.[0].employeeId").value(paymentSalaryResponse1.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSalaries.[0].amount").value(paymentSalaryResponse1.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSalaries.[0].status").value(paymentSalaryResponse1.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSalaries.[1].id").value(paymentSalaryResponse1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSalaries.[1].employeeId").value(paymentSalaryResponse2.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSalaries.[1].amount").value(paymentSalaryResponse2.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSalaries.[1].status").value(paymentSalaryResponse2.getStatus()));

        verify(paymentSalaryService).getAll();
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void getById_success() throws Exception {
        //given
        PaymentSalaryResponse paymentSalaryResponse = paymentSalaryResponseBuilder().build();

        Integer paymentSalaryResponseId = paymentSalaryResponse.getId();

        when(paymentSalaryService.getById(paymentSalaryResponseId)).thenReturn(paymentSalaryResponse);

        //when & then
        PaymentSalaryResponse expectedPaymentSalaryResponse = paymentSalaryResponseBuilder().build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/payment-salary/" + paymentSalaryResponseId) //send
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedPaymentSalaryResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(expectedPaymentSalaryResponse.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(expectedPaymentSalaryResponse.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(expectedPaymentSalaryResponse.getStatus()));

        verify(paymentSalaryService).getById(paymentSalaryResponseId);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void getById_paymentSalaryNotFound() throws Exception {
        //given

        when(paymentSalaryService.getById(WRONG_PAYMENT_SALARY_ID)).thenThrow(new PaymentSalaryNotFoundException(String.format(WRONG_PAYMENT_SALARY_MESSAGE, WRONG_PAYMENT_SALARY_ID)));

        //when & then
        ErrorResponse errorResponse = new ErrorResponse(PAYMENT_SALARY_DOES_NOT_EXIST, String.format(WRONG_PAYMENT_SALARY_MESSAGE, WRONG_PAYMENT_SALARY_ID));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/payment-salary/" + WRONG_PAYMENT_SALARY_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(paymentSalaryService).getById(WRONG_PAYMENT_SALARY_ID);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void delete_success() throws Exception {
        //given
        PaymentSalaryResponse paymentSalaryResponse = paymentSalaryResponseBuilder().build();

        Integer paymentSalaryId = paymentSalaryResponse.getId();

        when(paymentSalaryService.delete(paymentSalaryId)).thenReturn(paymentSalaryResponse);

        //when & then
        PaymentSalaryResponse expectedPaymentSalaryResponse = paymentSalaryResponseBuilder().build();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/payment-salary/" + paymentSalaryId)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedPaymentSalaryResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(expectedPaymentSalaryResponse.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(expectedPaymentSalaryResponse.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(expectedPaymentSalaryResponse.getStatus()));


        verify(paymentSalaryService).delete(paymentSalaryId);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void delete_paymentSalaryNotFound() throws Exception {
        //given
        when(paymentSalaryService.delete(WRONG_PAYMENT_SALARY_ID)).thenThrow(
                new PaymentSalaryNotFoundException(String.format(WRONG_PAYMENT_SALARY_MESSAGE, WRONG_PAYMENT_SALARY_ID)));

        //when & then
        ErrorResponse errorResponse =
                new ErrorResponse(PAYMENT_SALARY_DOES_NOT_EXIST, String.format(WRONG_PAYMENT_SALARY_MESSAGE, WRONG_PAYMENT_SALARY_ID));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/payment-salary/" + WRONG_PAYMENT_SALARY_ID)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(paymentSalaryService).delete(WRONG_PAYMENT_SALARY_ID);
        verifyNoMoreInteractions(paymentSalaryService);

    }

    @Test
    void update_success() throws Exception {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder().build();

        String stringReq = objectMapper.writeValueAsString(request);

        PaymentSalaryResponse paymentSalaryResponse = paymentSalaryResponseBuilder().build();

        when(paymentSalaryService.update(request)).thenReturn(paymentSalaryResponse);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/payment-salary")
                        .with(csrf())
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(paymentSalaryResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(paymentSalaryResponse.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(paymentSalaryResponse.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(paymentSalaryResponse.getStatus()));

        verify(paymentSalaryService).update(request);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void update_EmployeeNotFound() throws Exception {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder().build();

        String stringReq = objectMapper.writeValueAsString(request);

        when(paymentSalaryService.update(request)).thenThrow(new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, request.getEmployeeId())));

        ErrorResponse errorResponse =  new ErrorResponse(EMPLOYEE_DOES_NOT_EXIST,String.format(EMPLOYEE_IS_NOT_FOUND, request.getEmployeeId()));

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/payment-salary")
                        .with(csrf())
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(paymentSalaryService).update(request);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void update_PaymentSalaryNotFound() throws Exception {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder().build();

        String stringReq = objectMapper.writeValueAsString(request);

        when(paymentSalaryService.update(request)).thenThrow(new PaymentSalaryNotFoundException(String.format(PAYMENT_SALARY_NOT_FOUND, request.getEmployeeId())));

        ErrorResponse errorResponse =  new ErrorResponse(PAYMENT_SALARY_DOES_NOT_EXIST,String.format(PAYMENT_SALARY_NOT_FOUND, request.getEmployeeId()));

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/payment-salary")
                        .with(csrf())
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(paymentSalaryService).update(request);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void paySalary_success() throws Exception {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder()
                .build();

        Integer requestId = request.getId();

        String stringRequest = objectMapper.writeValueAsString(request);

        PaymentSalaryResponse expectedPaymentSalaryResponse = paymentSalaryResponseBuilder()
                .build();

        when(paymentSalaryService.paySalary(requestId)).thenReturn(expectedPaymentSalaryResponse);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/payment-salary/" + requestId)
                        .with(csrf())
                        .content(stringRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedPaymentSalaryResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(expectedPaymentSalaryResponse.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(expectedPaymentSalaryResponse.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(expectedPaymentSalaryResponse.getStatus()));

        verify(paymentSalaryService).paySalary(requestId);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void paySalary_PaymentSalaryNotFound() throws Exception {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder()
                .build();

        Integer requestId = request.getId();

        String stringRequest = objectMapper.writeValueAsString(request);

        when(paymentSalaryService.paySalary(requestId)).thenThrow(
                new PaymentSalaryNotFoundException(String.format(PAYMENT_SALARY_NOT_FOUND, requestId)));

        ErrorResponse errorResponse = new ErrorResponse(PAYMENT_SALARY_DOES_NOT_EXIST,String.format(PAYMENT_SALARY_NOT_FOUND, requestId));

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/payment-salary/" + requestId)
                        .with(csrf())
                        .content(stringRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(paymentSalaryService).paySalary(requestId);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void paySalary_EmployeeNotFound() throws Exception {
        //PaymentSalaryNotFoundException
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder()
                .build();

        Integer requestId = request.getId();

        String stringRequest = objectMapper.writeValueAsString(request);

        when(paymentSalaryService.paySalary(requestId)).thenThrow(
                new EmployeeNotFoundException(String.format(EMPLOYEE_IS_NOT_FOUND, requestId)));

        ErrorResponse errorResponse = new ErrorResponse(EMPLOYEE_DOES_NOT_EXIST,String.format(EMPLOYEE_IS_NOT_FOUND, requestId));

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/payment-salary/" + requestId)
                        .with(csrf())
                        .content(stringRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(paymentSalaryService).paySalary(requestId);
        verifyNoMoreInteractions(paymentSalaryService);
    }

    @Test
    void paySalary_wrongSalary() throws Exception {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder()
                .build();

        Integer requestId = request.getId();

        String stringRequest = objectMapper.writeValueAsString(request);

        when(paymentSalaryService.paySalary(requestId)).thenThrow(
                new WrongSalaryException());

        ErrorResponse errorResponse = new ErrorResponse(WRONG_SALARY,MESSAGE_WRONG_SALARY);

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/payment-salary/" + requestId) //send
                        .with(csrf())
                        .content(stringRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(errorResponse.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorResponse.getMessage()));

        verify(paymentSalaryService).paySalary(requestId);
        verifyNoMoreInteractions(paymentSalaryService);
    }

}
