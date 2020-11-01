package net.guides.springboot2.springboot2jpacrudexample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.Application;
import com.example.demo.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllPersons() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/Persons",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetPersonById() {
		Person Person = restTemplate.getForObject(getRootUrl() + "/Persons/1", Person.class);
		System.out.println(Person.getFirstName());
		assertNotNull(Person);
	}

	@Test
	public void testCreatePerson() {
		Person Person = new Person();
		Person.setDob("12/12/1993");
		Person.setFirstName("admin");
		Person.setLastName("admin");
		Person.setAddress("madurai");

		ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/Persons", Person, Person.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePerson() {
		int id = 1;
		Person Person = restTemplate.getForObject(getRootUrl() + "/Persons/" + id, Person.class);
		Person.setFirstName("admin1");
		Person.setLastName("admin2");

		restTemplate.put(getRootUrl() + "/Persons/" + id, Person);

		Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/Persons/" + id, Person.class);
		assertNotNull(updatedPerson);
	}

	@Test
	public void testDeletePerson() {
		int id = 2;
		Person Person = restTemplate.getForObject(getRootUrl() + "/Persons/" + id, Person.class);
		assertNotNull(Person);

		restTemplate.delete(getRootUrl() + "/Persons/" + id);

		try {
			Person = restTemplate.getForObject(getRootUrl() + "/Persons/" + id, Person.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
