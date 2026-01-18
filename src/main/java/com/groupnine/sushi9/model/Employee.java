package com.groupnine.sushi9.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Employee extends Person {

    private double salary;
    private String jobTitle;
    private LocalDate hireDate;

    public Employee() {}

    public Employee(String taxCode, String firstName, String lastName,
                    double salary, String jobTitle, LocalDate hireDate) {
        super(taxCode, firstName, lastName);
        this.salary = salary;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
    }

    @OneToMany(mappedBy = "employee")
    private List<Order> managedOrders;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public List<Order> getManagedOrders() {
        return managedOrders;
    }

    public void setManagedOrders(List<Order> managedOrders) {
        this.managedOrders = managedOrders;
    }

    public void addManagedOrder(Order order) {
        this.managedOrders.add(order);
        order.setEmployee(this);
    }

    public void removeManagedOrder(Order order) {
        this.managedOrders.remove(order);
        order.setEmployee(null);
    }

}
