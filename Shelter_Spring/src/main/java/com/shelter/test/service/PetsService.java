package com.shelter.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shelter.test.model.Pets;
import com.shelter.test.repostiry.PetsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PetsService {

	@Autowired
	private PetsRepository petsRepo;

	public boolean exists(String id) {
		if (id != null) {
			return petsRepo.existsById(id);
		}
		return false;
	}
	
	public boolean existsBySpecies(String species) {
		if (species != null) {
			return petsRepo.existsBySpecies(species);
		}
		return false;
	}
	
	public List<Pets> findAll(){
		return petsRepo.findAll();
	}
	
	public Pets findById(String id) {
		if (id != null) {
			return petsRepo.findById(id).orElse(null);
		}
		return null;
	}
	
	public List<Pets> findBySpecies(String species) {
		if (species != null) {
			return petsRepo.findBySpecies(species);
		}
		return null;
	}

	public Pets addPet(Pets pets) {
		if (pets != null) {
			return petsRepo.save(pets);
		}
		return null;
	}

	public Pets editPet(Pets pets) {
		if (pets != null) {
			return petsRepo.save(pets);
		}
		return null;
	}

	public boolean deletePetById(String id) {
		if (id != null) {
			Optional<Pets> emp = petsRepo.findById(id);
			if (emp.isPresent()) {
				petsRepo.deleteById(id);
			}
		}
		return false;
	}
	
	public Pets findPhotoById(String id) {
		if(id!=null) {
			Optional<Pets> optional = petsRepo.findById(id);
			if(optional.isPresent()) {
				return optional.get();				
			}
		}
		return null;
	}

}
