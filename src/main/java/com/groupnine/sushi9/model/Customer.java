package com.groupnine.sushi9.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Customer extends Person {

    private String phoneNumber;
    private String email;

    public Customer() {}

    public Customer(String taxCode, String firstName, String lastName,
                    String phoneNumber, String email) {
        super(taxCode, firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.getClients().add(this);  // Usa getClients() se è così che si chiama
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
        order.getClients().remove(this);
    }
}
