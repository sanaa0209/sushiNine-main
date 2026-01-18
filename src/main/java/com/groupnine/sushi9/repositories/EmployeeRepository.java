package com.groupnine.sushi9.repositories;

import com.groupnine.sushi9.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, String> {

    List<Employee> findByJobTitle(String jobTitle);

    List<Employee> findByHireDateAfter(LocalDate date);

    List<Employee> findBySalaryGreaterThan(double salary);

    Optional<Employee> findFirstByOrderBySalaryDesc();


    @Query("SELECT DISTINCT e FROM Employee e " +
            "JOIN e.managedOrders o " +
            "JOIN o.customers c " +
            "WHERE c.taxCode = :customerTaxCode")
    List<Employee> findEmployeesWhoManagedOrdersForCustomer(@Param("customerTaxCode") String customerTaxCode);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN e.managedOrders o " +
            "GROUP BY e " +
            "ORDER BY COUNT(o) DESC")
    List<Employee> findEmployeesOrderByOrderCount();

    boolean existsByTaxCode(String taxCode);
}
