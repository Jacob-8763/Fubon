package com.shelter.test.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shelter.test.model.Pets;
import com.shelter.test.service.PetsService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/pets")
@CrossOrigin
public class PetsController {

	@Autowired
	private PetsService petsService;
	
	@GetMapping
	public String home() {
		return "index.html";
	}
	
	// 新增動物資訊
	@PostMapping
	public ResponseEntity<?> addPet(@RequestParam(name = "file", required = false) MultipartFile photo,
			@ModelAttribute Pets pets) {

		try {
			if (photo != null && !photo.isEmpty()) {
				byte[] photofile = photo.getBytes();
				pets.setPhoto(photofile);
			}
			
			if (pets.getId() == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("請輸入動物編號");
			}
			
			if (petsService.exists(pets.getId())) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("動物編號已存在");
			}
			
			if (pets.getSpecies() == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("動物種類不能為空");
			}
			
			if (pets.getStatus() == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("狀態不能為空");
			}

			Pets pet = petsService.addPet(pets);
			return ResponseEntity.ok(pet);
			
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("圖片處理失敗");
		}
	}

	// 修改動物資訊
	@PutMapping
	public ResponseEntity<?> modifyPet(@RequestParam(name = "file", required = false) MultipartFile photo,
			@ModelAttribute Pets pets) {
		
		try {
			Pets existingPet = petsService.findById(pets.getId());
	        if (existingPet == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到該動物資訊");
	        }
			
	        if (photo != null && !photo.isEmpty()) {
	        	byte[] photofile = photo.getBytes();
	        	existingPet.setPhoto(photofile);
	        }
	        
	        existingPet.setSpecies(pets.getSpecies());
	        existingPet.setBreed(pets.getBreed());
	        existingPet.setColor(pets.getColor());
	        existingPet.setGender(pets.getGender());
	        existingPet.setStatus(pets.getStatus());
	        existingPet.setArrivalDate(pets.getArrivalDate());
	        existingPet.setIntakeReason(pets.getIntakeReason());
	        existingPet.setDepartureDate(pets.getDepartureDate());
	        existingPet.setAdopterName(pets.getAdopterName());
	        existingPet.setNotes(pets.getNotes());
			Pets pet = petsService.editPet(existingPet);
			return ResponseEntity.ok(pet);
			
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("圖片處理失敗");
		}
	}

	// 刪除動物資訊
	@DeleteMapping("/{pk}")
	public ResponseEntity<?> deletePet(@PathVariable("pk") String id) {

		if (id != null && petsService.exists(id)) {
			petsService.deletePetById(id);
			return ResponseEntity.status(HttpStatus.OK).body("動物已刪除");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("動物不存在");
	}

	// 查詢單筆動物資訊
	@GetMapping("/find/{pk}")
	public ResponseEntity<?> findByid(@PathVariable("pk") String id) {

		if (id != null && petsService.exists(id)) {
			Pets pets = petsService.findById(id);
			return ResponseEntity.ok(pets);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("動物不存在");
	}
	
	// 查詢動物花色
	@PostMapping("/findSpecies")
	public ResponseEntity<?> findByColor(@RequestBody String species) {

		System.err.println(species);
		species = species.replace("\"", "").trim();
		System.err.println(species);
		if (species != null && petsService.existsBySpecies(species)) {
			List<Pets> pets = petsService.findBySpecies(species);
			return ResponseEntity.ok(pets);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("查無該種類動物");
	}

	// 查詢全部動物資訊
	@GetMapping("/find")
	public ResponseEntity<?> getAllPet() {

		List<Pets> pets = petsService.findAll();
		if (!pets.isEmpty()) {
			return ResponseEntity.ok(pets);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("尚無動物資料");
	}
	
	// 圖片顯示
	@GetMapping(path = "/photo/{id}", produces = { MediaType.IMAGE_JPEG_VALUE })
	public @ResponseBody byte[] findPhotoByPhotoId(@PathVariable String id) {
		Pets pets = petsService.findPhotoById(id);
		byte[] result = this.photo;
		System.out.println(result);
		if (pets != null) {
			byte[] detailPhoto = pets.getPhoto();
			if (detailPhoto != null) {
				result = detailPhoto;
			}
		}
		return result;
	}

	private byte[] photo = null;
	
	
	// 無圖片時自動顯示
	@PostConstruct
	public void initialize() throws IOException {
		byte[] buffer = new byte[8192];

		ClassLoader classLoader = getClass().getClassLoader();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedInputStream is = new BufferedInputStream(classLoader.getResourceAsStream("static/images/no-image.jpg"));
		int len = is.read(buffer);
		while (len != -1) {
			os.write(buffer, 0, len);
			len = is.read(buffer);
		}
		is.close();
		this.photo = os.toByteArray();
	}

}
