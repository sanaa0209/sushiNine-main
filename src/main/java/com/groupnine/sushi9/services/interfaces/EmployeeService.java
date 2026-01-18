package com.groupnine.sushi9.services.interfaces;

import com.groupnine.sushi9.model.Employee;
import com.groupnine.sushi9.model.Order;
import java.util.List;

/**
 * Interface for managing {@link Employee} entities.
 * Provides CRUD operations and business logic for employees.
 */
public interface EmployeeService {

    /**
     * Creates a new employee.
     *
     * @param employee the employee to create
     * @return the created employee
     * @throws IllegalArgumentException if employee data is invalid
     */
    Employee createEmployee(Employee employee) throws IllegalArgumentException;

    /**
     * Finds an employee by taxCode.
     *
     * @param taxCode the tax code of the employee
     * @return the employee with the specified taxCode
     * @throws IllegalArgumentException if taxCode is invalid or employee not found
     */
    Employee getEmployeeById(String taxCode) throws IllegalArgumentException;

    /**
     * Retrieves all employees.
     *
     * @return a list of all employees
     */
    List<Employee> getAllEmployees();

    /**
     * Updates an existing employee.
     *
     * @param taxCode the tax code of the employee to update
     * @param employeeDetails the updated employee details
     * @return the updated employee
     * @throws IllegalArgumentException if taxCode is invalid or employee not found
     */
    Employee updateEmployee(String taxCode, Employee employeeDetails) throws IllegalArgumentException;

    /**
     * Deletes an employee by taxCode.
     *
     * @param taxCode the tax code of the employee to delete
     * @throws IllegalArgumentException if taxCode is invalid or employee not found
     */
    void deleteEmployee(String taxCode) throws IllegalArgumentException;

    /**
     * Finds employees by job title.
     *
     * @param jobTitle the job title to search for
     * @return a list of employees with the specified job title
     * @throws IllegalArgumentException if jobTitle is null or empty
     */
    List<Employee> findEmployeesByJobTitle(String jobTitle) throws IllegalArgumentException;

    /**
     * Retrieves all orders managed by a specific employee.
     *
     * @param taxCode the tax code of the employee
     * @return a list of orders managed by the employee
     * @throws IllegalArgumentException if taxCode is invalid or employee not found
     */
    List<Order> getOrdersManagedByEmployee(String taxCode) throws IllegalArgumentException;

    /**
     * Finds employees who have managed orders for a specific customer.
     *
     * @param customerTaxCode the tax code of the customer
     * @return a list of employees who managed orders for the customer
     * @throws IllegalArgumentException if customerTaxCode is invalid
     */
    List<Employee> findEmployeesWhoManagedOrdersForCustomer(String customerTaxCode) throws IllegalArgumentException;

    /**
     * Checks if an employee exists by taxCode.
     *
     * @param taxCode the tax code to check
     * @return true if employee exists, false otherwise
     */
    boolean existsById(String taxCode);

    /**
     * Counts the total number of employees.
     *
     * @return the total number of employees
     */
    long count();

    /**
     * Finds employees hired after a specific date.
     *
     * @param date the date to compare
     * @return a list of employees hired after the specified date
     */
    List<Employee> findEmployeesHiredAfter(java.time.LocalDate date);

    /**
     * Finds the employee with the highest salary.
     *
     * @return the employee with the highest salary
     */
    Employee findEmployeeWithHighestSalary();

    /**
     * Finds employees with salary greater than specified amount.
     *
     * @param salary the salary threshold
     * @return a list of employees with salary greater than the specified amount
     * @throws IllegalArgumentException if salary is invalid
     */
    List<Employee> findBySalaryGreaterThan(float salary) throws IllegalArgumentException;

}