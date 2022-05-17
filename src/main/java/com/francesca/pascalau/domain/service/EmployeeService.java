package com.francesca.pascalau.domain.service;

import com.francesca.pascalau.data.entities.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void add(Employee employee) {
        log.info("Adding new employee in the primary dataSource");
        entityManager.persist(employee);
    }

    public List<Employee> findAllEmployees() {
        log.info("Retrieving all employees from the replica dataSource");
        return entityManager.createQuery("from employee", Employee.class).getResultList();
    }
}
