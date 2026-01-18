package com.groupnine.sushi9.services;

import com.groupnine.sushi9.model.Customer;
import com.groupnine.sushi9.model.Employee;
import com.groupnine.sushi9.model.Order;
import com.groupnine.sushi9.services.interfaces.CustomerService;
import com.groupnine.sushi9.services.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Facade class that provides a unified interface to the restaurant system.
 * Simplifies client interactions by hiding the complexity of underlying services.
 */
@Service
public class RestaurantFacade {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    // Altri servizi verranno aggiunti in seguito (OrderService, MenuService, etc.)

    // ============= EMPLOYEE OPERATIONS =============

    /**
     * Creates a new employee
     */
    public Employee createEmployee(Employee employee) {
        return employeeService.createEmployee(employee);
    }

    /**
     * Gets employee by tax code
     */
    public Employee getEmployee(String taxCode) {
        return employeeService.getEmployeeById(taxCode);
    }

    /**
     * Gets all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * Updates an employee
     */
    public Employee updateEmployee(String taxCode, Employee employeeDetails) {
        return employeeService.updateEmployee(taxCode, employeeDetails);
    }

    /**
     * Deletes an employee
     */
    public void deleteEmployee(String taxCode) {
        employeeService.deleteEmployee(taxCode);
    }

    /**
     * Finds employees by job title
     */
    public List<Employee> findEmployeesByJobTitle(String jobTitle) {
        return employeeService.findEmployeesByJobTitle(jobTitle);
    }

    /**
     * Finds employees hired after a specific date
     */
    public List<Employee> findEmployeesHiredAfter(LocalDate date) {
        return employeeService.findEmployeesHiredAfter(date);
    }

    /**
     * Gets the employee with the highest salary
     */
    public Employee getEmployeeWithHighestSalary() {
        return employeeService.findEmployeeWithHighestSalary();
    }

    /**
     * Finds employees with salary greater than specified amount
     */
    public List<Employee> findEmployeesByMinSalary(float minSalary) {
        return employeeService.findBySalaryGreaterThan(minSalary);
    }

    /**
     * Gets orders managed by a specific employee
     */
    public List<Order> getOrdersManagedByEmployee(String employeeTaxCode) {
        return employeeService.getOrdersManagedByEmployee(employeeTaxCode);
    }

    /**
     * Finds employees who managed orders for a specific customer
     */
    public List<Employee> findEmployeesForCustomer(String customerTaxCode) {
        return employeeService.findEmployeesWhoManagedOrdersForCustomer(customerTaxCode);
    }

    // ============= CUSTOMER OPERATIONS =============

    /**
     * Creates a new customer
     */
    public Customer createCustomer(Customer customer) {
        return customerService.createCustomer(customer);
    }

    /**
     * Gets customer by tax code
     */
    public Customer getCustomer(String taxCode) {
        return customerService.getCustomerById(taxCode);
    }

    /**
     * Gets all customers
     */
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Updates a customer
     */
    public Customer updateCustomer(String taxCode, Customer customerDetails) {
        return customerService.updateCustomer(taxCode, customerDetails);
    }

    /**
     * Deletes a customer
     */
    public void deleteCustomer(String taxCode) {
        customerService.deleteCustomer(taxCode);
    }

    /**
     * Finds customer by email
     */
    public Customer findCustomerByEmail(String email) {
        return customerService.findByEmail(email);
    }

    /**
     * Finds customer by phone number
     */
    public Customer findCustomerByPhone(String phoneNumber) {
        return customerService.findByPhoneNumber(phoneNumber);
    }

    /**
     * Gets orders for a specific customer
     */
    public List<Order> getCustomerOrders(String taxCode) {
        return customerService.getCustomerOrders(taxCode);
    }

    /**
     * Finds customers with orders managed by a specific employee
     */
    public List<Customer> findCustomersForEmployee(String employeeTaxCode) {
        return customerService.findCustomersWithOrdersManagedByEmployee(employeeTaxCode);
    }

}