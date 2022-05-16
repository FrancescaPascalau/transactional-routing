package com.francesca.pascalau.data.repositories;

import com.francesca.pascalau.data.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
