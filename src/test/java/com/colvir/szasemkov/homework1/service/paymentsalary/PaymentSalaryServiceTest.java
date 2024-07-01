package com.colvir.szasemkov.homework1.service.paymentsalary;

import com.colvir.szasemkov.homework1.config.TestConfig;
import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.dto.paymentsalary.CreatePaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryPageResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.PaymentSalaryResponse;
import com.colvir.szasemkov.homework1.dto.paymentsalary.UpdatePaymentSalaryRequest;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.PaymentSalaryNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.WrongSalaryException;
import com.colvir.szasemkov.homework1.mapper.paymentsalary.PaymentSalaryMapper;
import com.colvir.szasemkov.homework1.model.employee.Employee;
import com.colvir.szasemkov.homework1.model.paymentsalary.PaymentSalary;
import com.colvir.szasemkov.homework1.repository.employee.EmployeeRepository;
import com.colvir.szasemkov.homework1.repository.paymentsalary.PaymentSalaryRepository;
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

import static com.colvir.szasemkov.homework1.generator.EmployeeGenerator.generateEmployee1;
import static com.colvir.szasemkov.homework1.generator.EmployeeGenerator.generateEmployeeAfterCreateBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.createPaymentSalaryRequestBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.createPaymentSalaryResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.paymentSalary1ResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.paymentSalary2ResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.paymentSalaryResponseBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryDtoGenerator.updatePaymentSalaryRequestBuilder;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryGenerator.generatePaymentSalary1;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryGenerator.generatePaymentSalary2;
import static com.colvir.szasemkov.homework1.generator.PaymentSalaryGenerator.generatePaymentSalaryAfterCreateBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PaymentSalaryService.class,
        PaymentSalaryMapper.class
})
@SpringBootTest(classes = {TestConfig.class})
class PaymentSalaryServiceTest {

    public static final Integer WRONG_PAYMENT_SALARY_ID = 500;
    public static final String WRONG_PAYMENT_SALARY_MESSAGE = String.format("PaymentSalary with id = %s is not found", WRONG_PAYMENT_SALARY_ID);

    @Autowired
    private PaymentSalaryService paymentSalaryService;

    @MockBean
    private PaymentSalaryRepository paymentSalaryRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void createPaymentSalary_success() {
        //given
        CreatePaymentSalaryRequest request = createPaymentSalaryRequestBuilder().build();
        Integer requestEmployeeId = request.getEmployeeId();

        Employee employee = generateEmployeeAfterCreateBuilder().build();

        when(employeeRepository.save(any())).thenReturn(any());
        when(employeeRepository.findById(requestEmployeeId)).thenReturn(Optional.of(employee));

        //when
        CreatePaymentSalaryResponse actualResponse = paymentSalaryService.createPaymentSalary(request);

        //then
        CreatePaymentSalaryResponse expectedResponse = createPaymentSalaryResponseBuilder().build();

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedResponse);

        verify(employeeRepository).findById(requestEmployeeId);
        verify(paymentSalaryRepository).save(any());
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void createPaymentSalary_employeeNotFound() {
        //given
        CreatePaymentSalaryRequest request = createPaymentSalaryRequestBuilder().build();

        Integer requestEmployeeId = request.getEmployeeId();

        when(employeeRepository.findById(requestEmployeeId)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> paymentSalaryService.createPaymentSalary(request));
        String expectedMessage = String.format("Employee with id = %s is not found", requestEmployeeId);
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(requestEmployeeId);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void getAll() {
        //given
        PaymentSalary paymentSalary1 = generatePaymentSalary1().build();
        PaymentSalary paymentSalary2 = generatePaymentSalary2().build();

        when(paymentSalaryRepository.findAll()).thenReturn(List.of(paymentSalary1, paymentSalary2));

        //when
        PaymentSalaryPageResponse actualResponse = paymentSalaryService.getAll();

        //then
        PaymentSalaryResponse paymentSalaryResponse1 = paymentSalary1ResponseBuilder().build();
        PaymentSalaryResponse paymentSalaryResponse2 = paymentSalary2ResponseBuilder().build();

        List<PaymentSalaryResponse> expectedPaymentSalaries = new ArrayList<>();
        expectedPaymentSalaries.add(paymentSalaryResponse1);
        expectedPaymentSalaries.add(paymentSalaryResponse2);
        PaymentSalaryPageResponse expectedResponse = new PaymentSalaryPageResponse(expectedPaymentSalaries);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(paymentSalaryRepository).findAll();
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void getById_success() {
        //given
        PaymentSalary paymentSalary = generatePaymentSalary1().build();
        Integer paymentSalaryId = paymentSalary.getId();

        when(paymentSalaryRepository.findById(paymentSalaryId)).thenReturn(Optional.of(paymentSalary));

        //when
        PaymentSalaryResponse actualResponse = paymentSalaryService.getById(paymentSalaryId);

        //then
        PaymentSalaryResponse expectedPaymentSalaryResponse = paymentSalary1ResponseBuilder().build();

        assertThat(actualResponse).isEqualTo(expectedPaymentSalaryResponse);

        verify(paymentSalaryRepository).findById(paymentSalaryId);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void getById_paymentSalaryNotFound() {
        //given
        when(paymentSalaryRepository.findById(WRONG_PAYMENT_SALARY_ID)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(PaymentSalaryNotFoundException.class, () -> paymentSalaryService.getById(WRONG_PAYMENT_SALARY_ID));
        assertEquals(WRONG_PAYMENT_SALARY_MESSAGE, exception.getMessage());

        verify(paymentSalaryRepository).findById(WRONG_PAYMENT_SALARY_ID);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void update_success() {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder().build();

        Integer requestId = request.getId();
        Integer requestEmployeeId = request.getEmployeeId();

        PaymentSalary paymentSalary = generatePaymentSalaryAfterCreateBuilder().build();

        when(paymentSalaryRepository.findById(requestId)).thenReturn(Optional.of(paymentSalary));

        Employee employee = generateEmployeeAfterCreateBuilder().build();

        when(employeeRepository.findById(requestEmployeeId)).thenReturn(Optional.of(employee));

        //when
        PaymentSalaryResponse actualResponse = paymentSalaryService.update(request);

        //then
        PaymentSalaryResponse expectedResponse = paymentSalaryResponseBuilder().build();

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(employeeRepository).findById(requestEmployeeId);
        verify(paymentSalaryRepository).findById(requestId);
        verify(paymentSalaryRepository).save(any());
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void update_employeeNotFound() {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder().build();

        Integer requestEmployeeId = request.getEmployeeId();

        when(employeeRepository.findById(requestEmployeeId)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> paymentSalaryService.update(request));
        String expectedMessage = String.format("Employee with id = %s is not found", requestEmployeeId);
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(requestEmployeeId);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void update_paymentSalaryNotFound() {
        //given
        UpdatePaymentSalaryRequest request = updatePaymentSalaryRequestBuilder().build();

        Integer requestId = request.getId();
        Integer requestEmployeeId = request.getEmployeeId();

        Employee employee = generateEmployeeAfterCreateBuilder().build();

        when(paymentSalaryRepository.findById(requestId)).thenReturn(Optional.empty());
        when(employeeRepository.findById(requestEmployeeId)).thenReturn(Optional.of(employee));

        //when & then
        Exception exception = assertThrows(PaymentSalaryNotFoundException.class, () -> paymentSalaryService.update(request));
        String expectedMessage = String.format("PaymentSalary with id = %s is not found", requestId);
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(requestEmployeeId);
        verify(paymentSalaryRepository).findById(requestId);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void delete() {
        //given
        PaymentSalary paymentSalary = generatePaymentSalaryAfterCreateBuilder().build();

        Integer paymentSalaryId = paymentSalary.getId();

        when(paymentSalaryRepository.findById(paymentSalaryId)).thenReturn(Optional.of(paymentSalary));

        //when
        PaymentSalaryResponse actualResponse = paymentSalaryService.delete(paymentSalaryId);

        //then
        PaymentSalaryResponse expectedResponse = paymentSalaryResponseBuilder().build();

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(paymentSalaryRepository).findById(paymentSalaryId);
        verify(paymentSalaryRepository).deleteById(paymentSalaryId);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void delete_paymentSalaryNotFound() {
        //given
        when(paymentSalaryRepository.findById(WRONG_PAYMENT_SALARY_ID)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(PaymentSalaryNotFoundException.class, () -> paymentSalaryService.delete(WRONG_PAYMENT_SALARY_ID));
        assertEquals(WRONG_PAYMENT_SALARY_MESSAGE, exception.getMessage());

        verify(paymentSalaryRepository).findById(WRONG_PAYMENT_SALARY_ID);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void paySalary() {
        //given
        PaymentSalary paymentSalary = generatePaymentSalary1().build();

        Integer paymentSalaryId = paymentSalary.getId();
        Integer paymentSalaryEmployeeId = paymentSalary.getEmployeeId();

        Employee employee = generateEmployee1().build();

        when(paymentSalaryRepository.findById(paymentSalaryId)).thenReturn(Optional.of(paymentSalary));
        when(employeeRepository.findById(paymentSalaryEmployeeId)).thenReturn(Optional.of(employee));

        //when
        PaymentSalaryResponse actualResponse = paymentSalaryService.paySalary(paymentSalaryId);

        //then
        PaymentSalaryResponse expectedPaymentSalaryResponse = paymentSalary1ResponseBuilder().build();
        assertThat(actualResponse).isEqualTo(expectedPaymentSalaryResponse);

        verify(employeeRepository).findById(paymentSalaryEmployeeId);
        verify(paymentSalaryRepository).findById(paymentSalaryId);
        verify(paymentSalaryRepository).save(paymentSalary);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void paySalary_paymentSalaryNotFound() {
        //given
        Integer wrongPaymentSalaryId = 1;
        when(paymentSalaryRepository.findById(wrongPaymentSalaryId)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(PaymentSalaryNotFoundException.class, () -> paymentSalaryService.paySalary(wrongPaymentSalaryId));
        String expectedMessage = String.format("PaymentSalary with id = %s is not found", wrongPaymentSalaryId);
        assertEquals(expectedMessage, exception.getMessage());

        verify(paymentSalaryRepository).findById(wrongPaymentSalaryId);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void paySalary_wrongSalary() {
        //given
        PaymentSalary paymentSalary = generatePaymentSalaryAfterCreateBuilder().build();

        Integer paymentSalaryId = paymentSalary.getId();
        Integer paymentSalaryEmployeeId = paymentSalary.getEmployeeId();

        Employee employee = generateEmployeeAfterCreateBuilder().build();

        when(paymentSalaryRepository.findById(paymentSalaryId)).thenReturn(Optional.of(paymentSalary));
        when(employeeRepository.findById(paymentSalaryEmployeeId)).thenReturn(Optional.of(employee));

        //when & then
        Exception exception = assertThrows(WrongSalaryException.class, () -> paymentSalaryService.paySalary(paymentSalaryId));
        String expectedMessage = "Salary in payment is not correspond the employee's salary";
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(paymentSalaryEmployeeId);
        verify(paymentSalaryRepository).findById(paymentSalaryId);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }



}