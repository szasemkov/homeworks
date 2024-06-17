package com.colvir.szasemkov.homework1.repository.employee;

import com.colvir.szasemkov.homework1.model.employee.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class EmployeeRepository {
    private final SessionFactory sessionFactory;

    public Employee save(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(employee);

        return employee;
    }

    public List<Employee> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select t from Employee t", Employee.class)
                .getResultList();
    }


    public Optional<Employee> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select t from Employee t where t.id = :id", Employee.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    public Employee update(Employee employeeForUpdate) {
        Session session = sessionFactory.getCurrentSession();

        Employee updatedEmployee = session.get(Employee.class, employeeForUpdate.getId());

        updatedEmployee.setFirstName(employeeForUpdate.getFirstName());
        updatedEmployee.setLastName(employeeForUpdate.getLastName());
        updatedEmployee.setSalary(employeeForUpdate.getSalary());
        updatedEmployee.setDepartment(employeeForUpdate.getDepartment());

        return updatedEmployee;
    }

    public Employee delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Employee employeeForDelete = session.get(Employee.class, id);

        session.remove(employeeForDelete);

        return employeeForDelete;
    }

}
