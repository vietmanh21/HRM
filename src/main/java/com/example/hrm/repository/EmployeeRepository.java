package com.example.hrm.repository;

import com.example.hrm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT * FROM employee WHERE role = 'DEVELOPER'", nativeQuery = true)
    List<Employee> findAllDeveloper();

    @Query(value = "SELECT * FROM employee WHERE role = 'MANAGER'", nativeQuery = true)
    List<Employee> findAllManager();

    @Query("select e FROM Employee e where e.username = :x")
    Employee findByUsername(String username);
}
