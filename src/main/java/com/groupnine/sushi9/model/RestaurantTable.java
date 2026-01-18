package com.groupnine.sushi9.model;


import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@SequenceGenerator(name = "TABLE_SEQUENCE", sequenceName = "TAB_SEQ")
public class RestaurantTable {

	@Id
	@Column(name = "TABLE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_SEQUENCE")
	private long id_table;
	private int num_seats;
	private String state;
	
	private List<Customer> clients;
	
	@OneToMany(mappedBy = "table")
	private List<Order> orders;
	
	public RestaurantTable() {}
	
	public RestaurantTable(long id, int num_seats, String state) {
		this.id_table = id;
		this.num_seats = num_seats;
		this.state = state;
		this.clients = new ArrayList<>();
		this.orders = new ArrayList<>();
		
	}
	
	public long getId() {
		return id_table;
	}
	
	public int getNumSeats() {
		return num_seats;
	}
	
	public String getstate() {
		return state;
	}
	

    public List<Customer> getClients(){
 		return clients;	
    }
 	
    public List<Order> getOrders(){
  		return orders;
    }
 
	
	public void setId(long id) {
		this.id_table = id;
	}
	
	public void setNumSeats(int num_seats) {
		this.num_seats = num_seats;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	

    public void setClients(List<Customer> clients){
  		this.clients = clients;
    }
 	
    public void setOrders(List<Order> orders){
  		this.orders = orders;
    }
 
}
