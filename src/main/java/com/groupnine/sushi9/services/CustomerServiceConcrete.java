package com.groupnine.sushi9.services;

import com.groupnine.sushi9.model.Customer;
import com.groupnine.sushi9.model.Order;
import com.groupnine.sushi9.repositories.CustomerRepository;
import com.groupnine.sushi9.services.interfaces.CustomerService;
import com.groupnine.sushi9.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Concrete implementation of the {@link CustomerService} interface.
 * Provides business logic for managing Customer entities.
 */
@Service
@Transactional
public class CustomerServiceConcrete implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Patterns per validazione
    private static final Pattern TAXCODE_PATTERN = Pattern.compile(Constants.TAXCODE_PATTERN);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_PATTERN);
    private static final Pattern PHONE_PATTERN = Pattern.compile(Constants.PHONE_PATTERN);

    protected CustomerServiceConcrete() { }

    @Override
    public Customer createCustomer(Customer customer) throws IllegalArgumentException {
        // Validazione base
        if (customer == null) {
            throw new IllegalArgumentException(Constants.NULL_OR_EMPTY_PARAMETER);
        }

        // Validazione taxCode
        if (customer.getTaxCode() == null || customer.getTaxCode().isEmpty()) {
            throw new IllegalArgumentException("Tax code cannot be null or empty");
        }

        if (!TAXCODE_PATTERN.matcher(customer.getTaxCode()).matches()) {
            throw new IllegalArgumentException("Invalid tax code format");
        }

        // Validazione email
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (!EMAIL_PATTERN.matcher(customer.getEmail()).matches()) {
            throw new IllegalArgumentException(Constants.INVALID_EMAIL);
        }

        // Validazione telefono (opzionale ma se presente deve essere valido)
        if (customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty() &&
                !PHONE_PATTERN.matcher(customer.getPhoneNumber()).matches()) {
            throw new IllegalArgumentException(Constants.INVALID_PHONE);
        }

        // Controllo duplicati
        if (customerRepository.existsById(customer.getTaxCode())) {
            throw new IllegalArgumentException(Constants.CUSTOMER_ALREADY_EXISTS + customer.getTaxCode());
        }

        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Customer with email " + customer.getEmail() + " already exists");
        }

        if (customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty() &&
                customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            throw new IllegalArgumentException("Customer with phone number " + customer.getPhoneNumber() + " already exists");
        }

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(String taxCode) throws IllegalArgumentException {
        if (taxCode == null || taxCode.isEmpty()) {
            throw new IllegalArgumentException("Tax code cannot be null or empty");
        }

        Optional<Customer> customer = customerRepository.findById(taxCode);
        if (customer.isEmpty()) {
            throw new IllegalArgumentException(Constants.NO_CUSTOMER_WITH_TAXCODE + taxCode);
        }

        return customer.get();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(String taxCode, Customer customerDetails) throws IllegalArgumentException {
        if (taxCode == null || taxCode.isEmpty()) {
            throw new IllegalArgumentException("Tax code cannot be null or empty");
        }

        if (customerDetails == null) {
            throw new IllegalArgumentException(Constants.NULL_OR_EMPTY_PARAMETER);
        }

        // Recupera customer esistente
        Customer existingCustomer = getCustomerById(taxCode);

        // Non permettere cambio di taxCode
        if (!taxCode.equals(customerDetails.getTaxCode())) {
            throw new IllegalArgumentException("Cannot change customer tax code");
        }

        // Validazione email se è cambiata
        if (!existingCustomer.getEmail().equals(customerDetails.getEmail())) {
            if (!EMAIL_PATTERN.matcher(customerDetails.getEmail()).matches()) {
                throw new IllegalArgumentException(Constants.INVALID_EMAIL);
            }
            if (customerRepository.existsByEmail(customerDetails.getEmail())) {
                throw new IllegalArgumentException("Email " + customerDetails.getEmail() + " is already in use");
            }
        }

        // Validazione telefono se è cambiato
        if (customerDetails.getPhoneNumber() != null &&
                !customerDetails.getPhoneNumber().isEmpty() &&
                (existingCustomer.getPhoneNumber() == null ||
                        !existingCustomer.getPhoneNumber().equals(customerDetails.getPhoneNumber()))) {

            if (!PHONE_PATTERN.matcher(customerDetails.getPhoneNumber()).matches()) {
                throw new IllegalArgumentException(Constants.INVALID_PHONE);
            }
            if (customerRepository.existsByPhoneNumber(customerDetails.getPhoneNumber())) {
                throw new IllegalArgumentException("Phone number " + customerDetails.getPhoneNumber() + " is already in use");
            }
        }

        // Aggiorna campi consentiti
        existingCustomer.setFirstName(customerDetails.getFirstName());
        existingCustomer.setLastName(customerDetails.getLastName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(String taxCode) throws IllegalArgumentException {
        if (taxCode == null || taxCode.isEmpty()) {
            throw new IllegalArgumentException("Tax code cannot be null or empty");
        }

        Customer customer = getCustomerById(taxCode);

        // Controlla se il customer ha ordini
        if (customer.getOrders() != null && !customer.getOrders().isEmpty()) {
            // Opzione 1: Impedisci cancellazione
            throw new IllegalArgumentException("Cannot delete customer with existing orders");

            // Opzione 2: Rimuovi associazioni (commenta se vuoi questa opzione)
            // for (Order order : customer.getOrders()) {
            //     order.getCustomers().remove(customer);
            // }
            // customer.getOrders().clear();
        }

        customerRepository.delete(customer);
    }

    @Override
    public Customer findByEmail(String email) throws IllegalArgumentException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("No customer found with email: " + email);
        }

        return customer;
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
        if (customer == null) {
            throw new IllegalArgumentException("No customer found with phone number: " + phoneNumber);
        }

        return customer;
    }

    @Override
    public List<Order> getCustomerOrders(String taxCode) throws IllegalArgumentException {
        Customer customer = getCustomerById(taxCode);
        return customer.getOrders() != null ? customer.getOrders() : List.of();
    }

    @Override
    public List<Customer> findCustomersWithOrdersManagedByEmployee(String employeeTaxCode) throws IllegalArgumentException {
        if (employeeTaxCode == null || employeeTaxCode.isEmpty()) {
            throw new IllegalArgumentException("Employee tax code cannot be null or empty");
        }

        return customerRepository.findCustomersWithOrdersManagedByEmployee(employeeTaxCode);
    }

    @Override
    public List<Customer> findByFirstNameContainingIgnoreCase(String firstName) throws IllegalArgumentException {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        return customerRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    @Override
    public List<Customer> findByLastNameContainingIgnoreCase(String lastName) throws IllegalArgumentException {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }

        return customerRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @Override
    public List<Customer> findCustomersWithOrdersBetweenDates(LocalDate startDate, LocalDate endDate) throws IllegalArgumentException {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        return customerRepository.findCustomersWithOrdersBetweenDates(startDate, endDate);
    }

    @Override
    public boolean existsById(String taxCode) {
        return customerRepository.existsById(taxCode);
    }

    @Override
    public long count() {
        return customerRepository.count();
    }

    @Override
    public Customer findCustomerWithMostOrders() {
        List<Object[]> results = customerRepository.findCustomersOrderByOrderCount();

        if (results.isEmpty()) {
            throw new IllegalArgumentException("No customers found");
        }

        return (Customer) results.get(0)[0];
    }

    // Metodo helper per verificare se un customer ha ordini
    public boolean hasOrders(String taxCode) {
        Customer customer = getCustomerById(taxCode);
        return customer.getOrders() != null && !customer.getOrders().isEmpty();
    }
}