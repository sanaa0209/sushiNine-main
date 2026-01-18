package com.groupnine.sushi9.services;

import com.groupnine.sushi9.model.Employee;
import com.groupnine.sushi9.model.Order;
import com.groupnine.sushi9.repositories.EmployeeRepository;
import com.groupnine.sushi9.services.interfaces.EmployeeService;
import com.groupnine.sushi9.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Concrete implementation of the {@link EmployeeService} interface.
 * Provides business logic for managing Employee entities.
 */
@Service
@Transactional
public class EmployeeServiceConcrete implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Pattern for taxCode validation (Italian format)
    private static final Pattern TAXCODE_PATTERN = Pattern.compile(Constants.TAXCODE_PATTERN);

    protected EmployeeServiceConcrete() { }

    @Override
    public Employee createEmployee(Employee employee) throws IllegalArgumentException {
        // Validation
        if (employee == null) {
            throw new IllegalArgumentException(Constants.NULL_OR_EMPTY_PARAMETER);
        }

        if (employee.getTaxCode() == null || employee.getTaxCode().isEmpty()) {
            throw new IllegalArgumentException("Tax code cannot be null or empty");
        }

        // Validate taxCode format
        if (!TAXCODE_PATTERN.matcher(employee.getTaxCode()).matches()) {
            throw new IllegalArgumentException("Invalid tax code format");
        }

        // Check if employee already exists
        if (employeeRepository.existsById(employee.getTaxCode())) {
            throw new IllegalArgumentException(Constants.EMPLOYEE_ALREADY_EXISTS + employee.getTaxCode());
        }

        // Validate salary
        if (employee.getSalary() < 0) {
            throw new IllegalArgumentException(Constants.INVALID_SALARY);
        }

        // Validate hire date (cannot be in the future)
        if (employee.getHireDate() != null &&
                employee.getHireDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Hire date cannot be in the future");
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(String taxCode) throws IllegalArgumentException {
        if (taxCode == null || taxCode.isEmpty()) {
            throw new IllegalArgumentException("Tax code cannot be null or empty");
        }

        Optional<Employee> employee = employeeRepository.findById(taxCode);
        if (employee.isEmpty()) {
            throw new IllegalArgumentException(Constants.NO_EMPLOYEE_WITH_TAXCODE + taxCode);
        }

        return employee.get();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(String taxCode, Employee employeeDetails) throws IllegalArgumentException {
        if (taxCode == null || taxCode.isEmpty()) {
            throw new IllegalArgumentException("Tax code cannot be null or empty");
        }

        if (employeeDetails == null) {
            throw new IllegalArgumentException(Constants.NULL_OR_EMPTY_PARAMETER);
        }

        // Get existing employee
        Employee existingEmployee = getEmployeeById(taxCode);

        // Update allowed fields (cannot change taxCode)
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setSalary(employeeDetails.getSalary());
        existingEmployee.setJobTitle(employeeDetails.getJobTitle());
        existingEmployee.setHireDate(employeeDetails.getHireDate());

        // Validate salary
        if (existingEmployee.getSalary() < 0) {
            throw new IllegalArgumentException(Constants.INVALID_SALARY);
        }

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(String taxCode) throws IllegalArgumentException {
        if (taxCode == null || taxCode.isEmpty()) {
            throw new IllegalArgumentException("Tax code cannot be null or empty");
        }

        Employee employee = getEmployeeById(taxCode);

        // Check if employee has managed orders
        if (employee.getManagedOrders() != null && !employee.getManagedOrders().isEmpty()) {
            // Option 1: Prevent deletion
            throw new IllegalArgumentException("Cannot delete employee with assigned orders");

            // Option 2: Orphan removal
            // for (Order order : employee.getManagedOrders()) {
            //     order.setEmployee(null);
            // }
        }

        employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> findEmployeesByJobTitle(String jobTitle) throws IllegalArgumentException {
        if (jobTitle == null || jobTitle.isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be null or empty");
        }

        return employeeRepository.findByJobTitle(jobTitle);
    }

    @Override
    public List<Order> getOrdersManagedByEmployee(String taxCode) throws IllegalArgumentException {
        Employee employee = getEmployeeById(taxCode);
        return employee.getManagedOrders() != null ? employee.getManagedOrders() : List.of();
    }

    @Override
    public List<Employee> findEmployeesWhoManagedOrdersForCustomer(String customerTaxCode) throws IllegalArgumentException {
        if (customerTaxCode == null || customerTaxCode.isEmpty()) {
            throw new IllegalArgumentException("Customer tax code cannot be null or empty");
        }

        // Usa il metodo del repository che abbiamo creato
        return employeeRepository.findEmployeesWhoManagedOrdersForCustomer(customerTaxCode);
    }

    @Override
    public boolean existsById(String taxCode) {
        return employeeRepository.existsById(taxCode);
    }

    @Override
    public long count() {
        return employeeRepository.count();
    }

    @Override
    public List<Employee> findEmployeesHiredAfter(java.time.LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        return employeeRepository.findByHireDateAfter(date);
    }

    @Override
    public Employee findEmployeeWithHighestSalary() {
        return employeeRepository.findFirstByOrderBySalaryDesc()
                .orElseThrow(() -> new IllegalArgumentException("No employees found"));
    }

    @Override
    public List<Employee> findBySalaryGreaterThan(float salary) {
        if (salary < 0) {
            throw new IllegalArgumentException(Constants.INVALID_SALARY);
        }
        return employeeRepository.findBySalaryGreaterThan(salary);
    }
}