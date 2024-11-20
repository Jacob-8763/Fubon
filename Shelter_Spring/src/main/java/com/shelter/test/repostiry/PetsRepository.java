package com.shelter.test.repostiry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shelter.test.model.Pets;

@Repository
public interface PetsRepository extends JpaRepository<Pets, String> {

	Optional<Pets> findByColor(String color);

	boolean existsById(String id);
	
	boolean existsBySpecies(String species);

	List<Pets> findBySpecies(String species);
	
}
