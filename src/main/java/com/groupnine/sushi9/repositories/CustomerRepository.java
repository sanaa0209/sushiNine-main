package com.groupnine.sushi9.repositories;

import com.groupnine.sushi9.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, String> {
    //altre query?
    Customer findByEmail(String email);
    Customer findByPhoneNumber(String phoneNumber);

    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);

    @Query("SELECT DISTINCT c FROM Customer c " +
            "JOIN c.orders o " +
            "JOIN o.employee e " +
            "WHERE e.taxCode = :employeeTaxCode")
    List<Customer> findCustomersWithOrdersManagedByEmployee(@Param("employeeTaxCode") String employeeTaxCode);

    @Query("SELECT DISTINCT c FROM Customer c " +
            "JOIN c.orders o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Customer> findCustomersWithOrdersBetweenDates(@Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT c, COUNT(o) as orderCount FROM Customer c " +
            "LEFT JOIN c.orders o " +
            "GROUP BY c " +
            "ORDER BY orderCount DESC")
    List<Object[]> findCustomersOrderByOrderCount();


    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
