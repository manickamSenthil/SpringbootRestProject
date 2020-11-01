package com.example.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.PetsRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Pets;


@RestController
@RequestMapping("/api/v1")
public class PetsController {
    
	@Autowired
    private PetsRepository petsRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/persons/{personId}/pets")
    public List <Pets> getPetssByInstructor(@PathVariable(value = "personId") Long personId) {
        return petsRepository.findByPersonId(personId);
    }

    @PostMapping("/persons/{personId}/pets")
    public Pets createPets(@PathVariable(value = "personId") Long personId,
        @Valid @RequestBody Pets pets) throws ResourceNotFoundException {
        return personRepository.findById(personId).map(person -> {
            pets.setPerson(person);
            return petsRepository.save(pets);
        }).orElseThrow(() -> new ResourceNotFoundException("person not found"));
    }

    @PutMapping("/persons/{personId}/pets/{petsId}")
    public Pets updatePets(@PathVariable(value = "personId") Long personId,
        @PathVariable(value = "petsId") Long petsId, @Valid @RequestBody Pets petsRequest)
    throws ResourceNotFoundException {
        if (!personRepository.existsById(personId)) {
            throw new ResourceNotFoundException("personId not found");
        }

        return petsRepository.findById(petsId).map(pets -> {
        	pets.setPetName(petsRequest.getPetName());
        	pets.setPetAge(petsRequest.getPetAge());
            return petsRepository.save(pets);
        }).orElseThrow(() -> new ResourceNotFoundException("Pets id not found"));
    }

    @DeleteMapping("/persons/{personId}/pets/{petsId}")
    public Map<String, Boolean> deletePets(@PathVariable(value = "personId") Long personId,
        @PathVariable(value = "petsId") Long petsId) throws ResourceNotFoundException {
         return petsRepository.findByIdAndPersonId(petsId, personId).map(pets -> {
            petsRepository.delete(pets);
            Map<String, Boolean> response = new HashMap<>();
     		response.put("deleted", Boolean.TRUE);
     		return response;
        }).orElseThrow(() -> new ResourceNotFoundException(
            "Pets not found with pet id " + petsId + " and personId " + personId));
    }
	
}
