package com.groupnine.sushi9.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private double price;

    private int quantity;

    // Relationship with Ingredients (Many-to-Many). 
    // A dish can have multiple ingredients. Multiple dishes can share the same ingredient.
    @ManyToMany
    @JoinTable(
        name = "dish_ingredient",
        joinColumns = @JoinColumn(name = "dish_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    // Direct Many-to-Many relationship with Order 
    @ManyToMany(mappedBy = "dishes")
    private List<Order> orders = new ArrayList<>();

    public Dish() {} // Default constructor
    
    public Dish(int quantity) {
        this.quantity = quantity;
    }

    public Dish(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addIngredient(Ingredient ingredient) {
    this.ingredients.add(ingredient);
    // Ensure the bidirectional link is established
    if (ingredient.getDishes() == null) {
        ingredient.setDishes(new ArrayList<>());
    }
    ingredient.getDishes().add(this);
}
}