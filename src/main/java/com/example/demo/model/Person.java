package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "person")
public class Person {
	
	private long id;
	private String firstName;
	private String lastName;
	private String dob;
	private String address;
	//private Set<Pets> pets = new HashSet<Pets>(0);
	
	public Person() {
		
	}
	
	public Person(String firstName, String lastName, String dob,String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.address=address;
	}
	
	

   
		 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "dob", nullable = false)
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Column(name = "address", nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	/*
	 * @OneToMany(fetch = FetchType.EAGER) public Set<Pets> getPets() { return pets;
	 * }
	 * 
	 * public void setPets(Set<Pets> pets) { this.pets = pets; }
	 */

	
	
	
	
	
	
	

}
