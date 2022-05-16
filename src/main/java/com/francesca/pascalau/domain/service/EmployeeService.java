package com.francesca.pascalau.domain.service;

import com.francesca.pascalau.data.entities.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void add(Employee employee) {
        entityManager.persist(employee);
    }

    public List<Employee> findAllEmployees() {
        return entityManager.createQuery("from Employee").getResultList();
    }
}
