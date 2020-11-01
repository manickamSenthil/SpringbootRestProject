package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Table(name = "pets")
public class Pets {
	
    private long id;
	private String petName;
	private String petAge;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	  public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	
	public Pets() {

    }

	private Person person;
	
	
	public Pets(String petName,String petAge,Person person) {
		this.petName=petName;
		this.petAge=petAge;
		this.person=person;

    }
	
	@Column(name = "pet_name", nullable = false)
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	
	@Column(name = "pet_age", nullable = false)
	public String getPetAge() {
		return petAge;
	}
	public void setPetAge(String petAge) {
		this.petAge = petAge;
	}
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	
	
	
}
