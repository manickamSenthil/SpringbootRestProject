package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pets;

@Repository
public interface PetsRepository extends JpaRepository<Pets, Long> {

	List<Pets> findByPersonId(Long personId);
	Optional<Pets> findByIdAndPersonId(Long id, Long personId);
}
