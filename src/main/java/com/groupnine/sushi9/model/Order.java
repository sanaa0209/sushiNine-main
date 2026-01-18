package com.groupnine.sushi9.model;


import jakarta.persistence.*;



import java.util.ArrayList;
import java.util.List;


@Entity
@SequenceGenerator(name = "ORDER_SEQUENCE", sequenceName = "ORDER_SEQ")
public class Order {

	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQUENCE")
	private long id_order;
	
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "TABLE_ID")
	private RestaurantTable table;

    @ManyToMany
    private List<Dish> dishes;

    @ManyToMany
    private List<Customer> customers;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;
	
	
	public Order() {}
	
	public Order(long id, String state, RestaurantTable table) {
		this.id_order = id;
		this.state = state;
		this.table = table;
		this.dishes = new ArrayList<>();
		this.customers = new ArrayList<>();

        //Mai istanziare entity dentro un’altra entity
		this.employee = new Employee();	
	}
	
	public long getId() {
		return id_order;
	}
		
	public String getstate() {
		return state;
	}
	
	public RestaurantTable getTable() {
		return table;
	}
	
    public List<Dish> getPlates(){
  		return dishes;
    }
  
    public List<Customer> getClients(){
  		return customers;
    }
  
    public Employee getEmployee(){
  		return employee;
    }

	public void setId(long id) {
		this.id_order = id;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void setTable(RestaurantTable table) {
		this.table = table;
	}
	
    public void setCustomers(List<Customer> customers){
  		this.customers = customers;
    }
  
    public void setDishes(List<Dish> dishes){
  		this.dishes = dishes;
    }
  
    public void setEmployee(Employee employee){
  		this.employee = employee;
    }
 
}


