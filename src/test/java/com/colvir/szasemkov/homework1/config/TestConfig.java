package com.colvir.szasemkov.homework1.config;

import com.colvir.szasemkov.homework1.mapper.employee.EmployeeMapper;
import com.colvir.szasemkov.homework1.mapper.paymentsalary.PaymentSalaryMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public EmployeeMapper getEmployeeMapper() {
        return Mappers.getMapper(EmployeeMapper.class);
    }

    @Bean
    public PaymentSalaryMapper getPaymentSalaryMapper() {
        return Mappers.getMapper(PaymentSalaryMapper.class);
    }
}
