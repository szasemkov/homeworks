package com.colvir.szasemkov.homework1.service.paymentsalary;

import com.colvir.szasemkov.homework1.dto.employee.*;
import com.colvir.szasemkov.homework1.dto.paymentsalary.*;
import com.colvir.szasemkov.homework1.exception.employee.EmployeeNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.PaymentSalaryNotFoundException;
import com.colvir.szasemkov.homework1.exception.paymentsalary.WrongSalaryException;
import com.colvir.szasemkov.homework1.mapper.paymentsalary.PaymentSalaryMapperImpl;
import com.colvir.szasemkov.homework1.model.employee.Employee;
import com.colvir.szasemkov.homework1.model.paymentsalary.PaymentSalary;
import com.colvir.szasemkov.homework1.repository.employee.EmployeeRepository;
import com.colvir.szasemkov.homework1.repository.paymentsalary.PaymentSalaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PaymentSalaryService.class,
        PaymentSalaryMapperImpl.class
})
class PaymentSalaryServiceTest {
    @Autowired
    private PaymentSalaryService paymentSalaryService;

    @MockBean
    private PaymentSalaryRepository paymentSalaryRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void createPaymentSalary() {
        //given
        CreatePaymentSalaryRequest request = new CreatePaymentSalaryRequest();
        request.setEmployeeId(1);
        request.setAmount(300);
        Date date = new Date(2024, Calendar.JUNE, 4);
        request.setDate(date);

        when(employeeRepository.save(any())).thenReturn(any());

        Employee employee = new Employee("Ivan", "Ivanov", 500, "Department1");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //when
        CreatePaymentSalaryResponse actualResponse = paymentSalaryService.createPaymentSalary(request);

        //then
        CreatePaymentSalaryResponse expectedResponse = new CreatePaymentSalaryResponse();
        expectedResponse.setEmployeeId(1);
        expectedResponse.setAmount(300);
        expectedResponse.setDate(date);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedResponse);

        verify(employeeRepository).findById(1);
        verify(paymentSalaryRepository).save(any());
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void createPaymentSalary_employeeNotFound() {
        //given
        CreatePaymentSalaryRequest request = new CreatePaymentSalaryRequest();
        request.setEmployeeId(1);
        request.setAmount(300);
        Date date = new Date(2024, Calendar.JUNE, 4);
        request.setDate(date);

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> paymentSalaryService.createPaymentSalary(request));
        String expectedMessage = "Employee with id = 1 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(1);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void getAll() {
        //given
        Date date1 = new Date(2024, Calendar.JUNE, 4);
        Date date2 = new Date(2024, Calendar.MAY, 26);
        PaymentSalary paymentSalary1 = new PaymentSalary(11, 500, date1, true);
        PaymentSalary paymentSalary2 = new PaymentSalary(12, 400, date2, false);

        when(paymentSalaryRepository.findAll()).thenReturn(List.of(paymentSalary1, paymentSalary2));

        //when
        PaymentSalaryPageResponse actualResponse = paymentSalaryService.getAll();

        //then
        PaymentSalaryResponse paymentSalaryResponse1 = new PaymentSalaryResponse();
        paymentSalaryResponse1.setEmployeeId(11);
        paymentSalaryResponse1.setAmount(500);
        paymentSalaryResponse1.setDate(date1);
        paymentSalaryResponse1.setStatus(true);
        PaymentSalaryResponse paymentSalaryResponse2 = new PaymentSalaryResponse();
        paymentSalaryResponse2.setEmployeeId(12);
        paymentSalaryResponse2.setAmount(400);
        paymentSalaryResponse2.setDate(date2);
        paymentSalaryResponse2.setStatus(false);
        List<PaymentSalaryResponse> expectedPaymentSalaries = new ArrayList<>();
        expectedPaymentSalaries.add(paymentSalaryResponse1);
        expectedPaymentSalaries.add(paymentSalaryResponse2);
        PaymentSalaryPageResponse expectedResponse = new PaymentSalaryPageResponse(expectedPaymentSalaries);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(paymentSalaryRepository).findAll();
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void getById() {
        //given
        Date date = new Date(2024, Calendar.JUNE, 4);
        PaymentSalary paymentSalary = new PaymentSalary(11, 500, date, true);

        when(paymentSalaryRepository.findById(1)).thenReturn(Optional.of(paymentSalary));

        //when
        PaymentSalaryResponse actualResponse = paymentSalaryService.getById(1);

        //then
        PaymentSalaryResponse expectedPaymentSalaryResponse = new PaymentSalaryResponse();
        expectedPaymentSalaryResponse.setEmployeeId(11);
        expectedPaymentSalaryResponse.setAmount(500);
        expectedPaymentSalaryResponse.setDate(date);
        expectedPaymentSalaryResponse.setStatus(true);

        assertThat(actualResponse).isEqualTo(expectedPaymentSalaryResponse);

        verify(paymentSalaryRepository).findById(1);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void getById_paymentSalaryNotFound() {
        //given
        when(paymentSalaryRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(PaymentSalaryNotFoundException.class, () -> paymentSalaryService.getById(1));
        String expectedMessage = "PaymentSalary with id = 1 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(paymentSalaryRepository).findById(1);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void update() {
        //given
        UpdatePaymentSalaryRequest request = new UpdatePaymentSalaryRequest();
        request.setId(11);
        request.setEmployeeId(1);
        request.setAmount(300);
        Date date = new Date(2024, Calendar.JUNE, 4);
        request.setDate(date);

        PaymentSalary paymentSalary = new PaymentSalary(1, 300, date, false);

        when(paymentSalaryRepository.findById(11)).thenReturn(Optional.of(paymentSalary));

        Employee employee = new Employee("Ivan", "Ivanov", 500, "Department1");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //when
        PaymentSalaryResponse actualResponse = paymentSalaryService.update(request);

        //then
        PaymentSalaryResponse expectedResponse = new PaymentSalaryResponse();
        expectedResponse.setId(11);
        expectedResponse.setEmployeeId(1);
        expectedResponse.setAmount(300);
        expectedResponse.setDate(date);
        expectedResponse.setStatus(false);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(employeeRepository).findById(1);
        verify(paymentSalaryRepository).findById(11);
        verify(paymentSalaryRepository).update(any());
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void update_employeeNotFound() {
        //given
        UpdatePaymentSalaryRequest request = new UpdatePaymentSalaryRequest();
        request.setId(11);
        request.setEmployeeId(1);
        request.setAmount(300);
        Date date = new Date(2024, Calendar.JUNE, 4);
        request.setDate(date);

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> paymentSalaryService.update(request));
        String expectedMessage = "Employee with id = 1 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(1);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void update_paymentSalaryNotFound() {
        //given
        UpdatePaymentSalaryRequest request = new UpdatePaymentSalaryRequest();
        request.setId(11);
        request.setEmployeeId(1);
        request.setAmount(300);
        Date date = new Date(2024, Calendar.JUNE, 4);
        request.setDate(date);

        when(paymentSalaryRepository.findById(11)).thenReturn(Optional.empty());

        Employee employee = new Employee("Ivan", "Ivanov", 500, "Department1");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //when & then
        Exception exception = assertThrows(PaymentSalaryNotFoundException.class, () -> paymentSalaryService.update(request));
        String expectedMessage = "PaymentSalary with id = 11 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(1);
        verify(paymentSalaryRepository).findById(11);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void delete() {
        //given
        Date date = new Date(2024, Calendar.JUNE, 4);
        PaymentSalary paymentSalary = new PaymentSalary(1, 300, date, false);

        when(paymentSalaryRepository.findById(11)).thenReturn(Optional.of(paymentSalary));

        //when
        PaymentSalaryResponse actualResponse = paymentSalaryService.delete(11);

        //then
        PaymentSalaryResponse expectedResponse = new PaymentSalaryResponse();
        expectedResponse.setEmployeeId(1);
        expectedResponse.setAmount(300);
        expectedResponse.setDate(date);
        expectedResponse.setStatus(false);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(paymentSalaryRepository).findById(11);
        verify(paymentSalaryRepository).delete(11);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void delete_paymentSalaryNotFound() {
        //given
        when(paymentSalaryRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(PaymentSalaryNotFoundException.class, () -> paymentSalaryService.delete(1));
        String expectedMessage = "PaymentSalary with id = 1 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(paymentSalaryRepository).findById(1);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void paySalary() {
        //given
        Date date = new Date(2024, Calendar.JUNE, 4);
        PaymentSalary paymentSalary = new PaymentSalary(11, 500, date, false);

        when(paymentSalaryRepository.findById(1)).thenReturn(Optional.of(paymentSalary));

        Employee employee = new Employee("Ivan", "Ivanov", 500, "Department1");

        when(employeeRepository.findById(11)).thenReturn(Optional.of(employee));

        //when
        PaymentSalaryResponse actualResponse = paymentSalaryService.paySalary(1);

        //then
        PaymentSalaryResponse expectedPaymentSalaryResponse = new PaymentSalaryResponse();
        expectedPaymentSalaryResponse.setEmployeeId(11);
        expectedPaymentSalaryResponse.setAmount(500);
        expectedPaymentSalaryResponse.setDate(date);
        expectedPaymentSalaryResponse.setStatus(true);

        assertThat(actualResponse).isEqualTo(expectedPaymentSalaryResponse);

        verify(employeeRepository).findById(11);
        verify(paymentSalaryRepository).findById(1);
        verify(paymentSalaryRepository).update(paymentSalary);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }

    @Test
    void paySalary_paymentSalaryNotFound() {
        //given
        when(paymentSalaryRepository.findById(1)).thenReturn(Optional.empty());

        //when & then
        Exception exception = assertThrows(PaymentSalaryNotFoundException.class, () -> paymentSalaryService.paySalary(1));
        String expectedMessage = "PaymentSalary with id = 1 is not found";
        assertEquals(expectedMessage, exception.getMessage());

        verify(paymentSalaryRepository).findById(1);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }


    @Test
    void paySalary_wrongSalary() {
        //given
        Date date = new Date(2024, Calendar.JUNE, 4);
        PaymentSalary paymentSalary = new PaymentSalary(11, 300, date, false);

        when(paymentSalaryRepository.findById(1)).thenReturn(Optional.of(paymentSalary));

        Employee employee = new Employee("Ivan", "Ivanov", 500, "Department1");

        when(employeeRepository.findById(11)).thenReturn(Optional.of(employee));

        //when & then
        Exception exception = assertThrows(WrongSalaryException.class, () -> paymentSalaryService.paySalary(1));
        String expectedMessage = "Salary in payment not correspond the employee's salary";
        assertEquals(expectedMessage, exception.getMessage());

        verify(employeeRepository).findById(11);
        verify(paymentSalaryRepository).findById(1);
        verifyNoMoreInteractions(paymentSalaryRepository);
    }
}