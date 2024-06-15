package com.colvir.szasemkov.homework1.repository.paymentsalary;

import com.colvir.szasemkov.homework1.model.paymentsalary.PaymentSalary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class PaymentSalaryRepository {
    private final SessionFactory sessionFactory;

    public PaymentSalary save(PaymentSalary employee) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(employee);

        return employee;
    }

    public List<PaymentSalary> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select t from PaymentSalary t", PaymentSalary.class)
                .getResultList();
    }


    public Optional<PaymentSalary> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select t from PaymentSalary t where t.id = :id", PaymentSalary.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    public PaymentSalary update(PaymentSalary paymentSalaryForUpdate) {
        Session session = sessionFactory.getCurrentSession();

        PaymentSalary updatedPpaymentSalary = session.get(PaymentSalary.class, paymentSalaryForUpdate.getId());

        updatedPpaymentSalary.setEmployeeId(paymentSalaryForUpdate.getEmployeeId());
        updatedPpaymentSalary.setAmount(paymentSalaryForUpdate.getAmount());
        updatedPpaymentSalary.setDate(paymentSalaryForUpdate.getDate());
        updatedPpaymentSalary.setStatus(paymentSalaryForUpdate.getStatus());

        return updatedPpaymentSalary;
    }

    public PaymentSalary delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        PaymentSalary paymentSalaryForDelete = session.get(PaymentSalary.class, id);

        session.remove(paymentSalaryForDelete);

        return paymentSalaryForDelete;
    }
}
