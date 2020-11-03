package com.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.Application;
import com.example.demo.model.Person;
import com.example.demo.model.Pets;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class PersonControllerIntegrationTest {

	  
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}
  
	@Before
	public void startUp() {
		 restTemplate = new TestRestTemplate("user", "user");
	}
	
	
	
	@Test
	public void contextLoads() {

	}
	
	@Test
	public void securityTest() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/persons",
				HttpMethod.GET, entity, String.class);
		System.out.println(response.getBody());
		assertNotNull(response.getBody());
	}
	


	@Test
	public void testCreatePerson() {
	//	restTemplate = new TestRestTemplate("user", "user");	
		Person Person = new Person();
		Person.setDob("12/12/1993");
		Person.setFirstName("admin");
		Person.setLastName("admin");
		Person.setAddress("Madurai");
		ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/persons", Person, Person.class);
		assertNotNull(postResponse);	
		assertNotNull(postResponse.getBody());		
	}

	@Test
	public void testUpdatePerson() {
		//restTemplate = new TestRestTemplate("user", "user");
		testCreatePerson();
		int id = 1;
		Person person = restTemplate.getForObject(getRootUrl() + "/api/v1/persons/" + id, Person.class);
		person.setFirstName("userlogin");
		person.setLastName("user");
		person.setAddress("Covai");
		restTemplate.put(getRootUrl() + "/api/v1/persons/" + id, person);

		Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/api/v1/persons/" + id, Person.class);
		
		assertEquals("Madurai", updatedPerson.getAddress());
		assertNotNull(updatedPerson);
	}
	
	
	@Test
	public void testUpdateAddressPerson() {
		//restTemplate = new TestRestTemplate("user", "user");
		testCreatePerson();	
		int id = 1;
		Person person = restTemplate.getForObject(getRootUrl() + "/api/v1/persons/" + id, Person.class);
		person.setFirstName("admin1");
		person.setLastName("senthil");
		person.setAddress("Covai");
		restTemplate.put(getRootUrl() + "/api/v1/persons/" + id, person);
		
		Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/api/v1/persons/" + id, Person.class);
		assertEquals("Covai", updatedPerson.getAddress());
		assertNotNull(updatedPerson);
	}
	
	
	@Test
	public void testGetAllPersons() {
		testCreatePerson();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	//	 restTemplate = new TestRestTemplate("user", "user");

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/persons",
				HttpMethod.GET, entity, String.class);
		System.out.println(response.getBody());
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetPersonById() {
		testCreatePerson();
		// restTemplate = new TestRestTemplate("user", "user");
		Person Person = restTemplate.getForObject(getRootUrl() + "/api/v1/persons/1", Person.class);
		System.out.println(Person.getFirstName());
		assertNotNull(Person);
	}


	@Test
	public void testDeletePerson() {
		testCreatePerson();
		//restTemplate = new TestRestTemplate("user", "user");
		int id = 5;
		Person Person = restTemplate.getForObject(getRootUrl() + "/api/v1/persons/" + id, Person.class);
		assertNotNull(Person);

		restTemplate.delete(getRootUrl() + "/api/v1/persons/" + id);
		
		try {
			Person = restTemplate.getForObject(getRootUrl() + "/api/v1/persons/" + id, Person.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	

}