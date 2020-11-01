package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;

/***
 * 
 * @author Senthil
 *
 */
@RestController
@RequestMapping("/api/v1")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@GetMapping("/persons")
	public List<Person> getAllPersons() {
		return personRepository.findAll();
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long personId)
			throws ResourceNotFoundException {
		Person Person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));
		return ResponseEntity.ok().body(Person);
	}

	@PostMapping("/persons")
	public Person createPerson(@Valid @RequestBody Person Person) throws ResourceNotFoundException{
		if(Person.getFirstName().equals(Person.getLastName()))
			throw new ResourceNotFoundException("Person's first name and last name should not be same :: "+Person.getFirstName());
		return personRepository.save(Person);
	}

	@PutMapping("/persons/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long PersonId,
			@Valid @RequestBody Person PersonDetails) throws ResourceNotFoundException {
		Person Person = personRepository.findById(PersonId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + PersonId));

		Person.setDob(PersonDetails.getDob());
		Person.setLastName(PersonDetails.getLastName());
		Person.setFirstName(PersonDetails.getFirstName());
		//Person.setAddress(PersonDetails.getAddress());
		
		if(Person.getFirstName().equals(Person.getLastName()))
			throw new ResourceNotFoundException("Person's first name and last name should not be same :: "+Person.getFirstName());
		
		
		final Person updatedPerson = personRepository.save(Person);
		return ResponseEntity.ok(updatedPerson);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Person> updateAdminPerson(@PathVariable(value = "id") Long PersonId,
			@Valid @RequestBody Person PersonDetails) throws ResourceNotFoundException {
		Person Person = personRepository.findById(PersonId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + PersonId));

		Person.setDob(PersonDetails.getDob());
		Person.setLastName(PersonDetails.getLastName());
		Person.setFirstName(PersonDetails.getFirstName());
		Person.setAddress(PersonDetails.getAddress());
		
		if(Person.getFirstName().equals(Person.getLastName()))
			throw new ResourceNotFoundException("Person's first name and last name should not be same :: "+Person.getFirstName());
		
		final Person updatedPerson = personRepository.save(Person);
		return ResponseEntity.ok(updatedPerson);
	}
	

	@DeleteMapping("/persons/{id}")
	public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long PersonId)
			throws ResourceNotFoundException {
		Person Person = personRepository.findById(PersonId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + PersonId));

		personRepository.delete(Person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@GetMapping("/persons/name/{name}")
	public List<Person> getPersonByName(@PathVariable(value = "name") String name)
			throws ResourceNotFoundException {
		List<Person> byfirstName = personRepository.findByFirstName(name);
		List<Person> byLastName = personRepository.findByLastName(name);
	    byfirstName.addAll(byLastName);
	  return byfirstName;
	}
	
	@GetMapping("/persons/firstname/{firstname}")
	public List<Person> getPersonByFirstName(@PathVariable(value = "firstname") String firstname)
			throws ResourceNotFoundException {		
	  return personRepository.findByFirstName(firstname);
	}
	
	
	@GetMapping("/persons/lastname/{lastname}")
	public List<Person> getPersonByLastName(@PathVariable(value = "lastname") String lastname)
			throws ResourceNotFoundException {		
	  return personRepository.findByLastName(lastname);
	}
}
