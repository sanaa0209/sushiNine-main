package com.groupnine.sushi9.services.interfaces;

import com.groupnine.sushi9.model.Customer;
import com.groupnine.sushi9.model.Order;
import java.util.List;

/**
 * Interface for managing {@link Customer} entities.
 * Provides CRUD operations and business logic for customers.
 */
public interface CustomerService {

    /**
     * Creates a new customer.
     *
     * @param customer the customer to create
     * @return the created customer
     * @throws IllegalArgumentException if customer data is invalid
     */
    Customer createCustomer(Customer customer) throws IllegalArgumentException;

    /**
     * Finds a customer by taxCode.
     *
     * @param taxCode the tax code of the customer
     * @return the customer with the specified taxCode
     * @throws IllegalArgumentException if taxCode is invalid or customer not found
     */
    Customer getCustomerById(String taxCode) throws IllegalArgumentException;

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    List<Customer> getAllCustomers();

    /**
     * Updates an existing customer.
     *
     * @param taxCode the tax code of the customer to update
     * @param customerDetails the updated customer details
     * @return the updated customer
     * @throws IllegalArgumentException if taxCode is invalid or customer not found
     */
    Customer updateCustomer(String taxCode, Customer customerDetails) throws IllegalArgumentException;

    /**
     * Deletes a customer by taxCode.
     *
     * @param taxCode the tax code of the customer to delete
     * @throws IllegalArgumentException if taxCode is invalid or customer not found
     */
    void deleteCustomer(String taxCode) throws IllegalArgumentException;

    /**
     * Finds a customer by email.
     *
     * @param email the email to search for
     * @return the customer with the specified email
     * @throws IllegalArgumentException if email is null or empty
     */
    Customer findByEmail(String email) throws IllegalArgumentException;

    /**
     * Finds a customer by phone number.
     *
     * @param phoneNumber the phone number to search for
     * @return the customer with the specified phone number
     * @throws IllegalArgumentException if phoneNumber is null or empty
     */
    Customer findByPhoneNumber(String phoneNumber) throws IllegalArgumentException;

    /**
     * Retrieves all orders placed by a specific customer.
     *
     * @param taxCode the tax code of the customer
     * @return a list of orders placed by the customer
     * @throws IllegalArgumentException if taxCode is invalid or customer not found
     */
    List<Order> getCustomerOrders(String taxCode) throws IllegalArgumentException;

    /**
     * Finds customers who have orders managed by a specific employee.
     *
     * @param employeeTaxCode the tax code of the employee
     * @return a list of customers with orders managed by the employee
     * @throws IllegalArgumentException if employeeTaxCode is invalid
     */
    List<Customer> findCustomersWithOrdersManagedByEmployee(String employeeTaxCode) throws IllegalArgumentException;

    /**
     * Finds customers by first name (case-insensitive).
     *
     * @param firstName the first name to search for
     * @return a list of customers with the specified first name
     * @throws IllegalArgumentException if firstName is null or empty
     */
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName) throws IllegalArgumentException;

    /**
     * Finds customers by last name (case-insensitive).
     *
     * @param lastName the last name to search for
     * @return a list of customers with the specified last name
     * @throws IllegalArgumentException if lastName is null or empty
     */
    List<Customer> findByLastNameContainingIgnoreCase(String lastName) throws IllegalArgumentException;

    /**
     * Finds customers who placed orders in a specific date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of customers who placed orders in the date range
     * @throws IllegalArgumentException if dates are invalid
     */
    List<Customer> findCustomersWithOrdersBetweenDates(java.time.LocalDate startDate, java.time.LocalDate endDate) throws IllegalArgumentException;

    /**
     * Checks if a customer exists by taxCode.
     *
     * @param taxCode the tax code to check
     * @return true if customer exists, false otherwise
     */
    boolean existsById(String taxCode);

    /**
     * Counts the total number of customers.
     *
     * @return the total number of customers
     */
    long count();

    /**
     * Finds the customer with the most orders.
     *
     * @return the customer with the highest number of orders
     */
    Customer findCustomerWithMostOrders();
}