package com.shelter.test.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "shelter_animals")
public class Pets {
	
	@Id
	@Column(name = "pet_id")
	private String id;
	
	@Column(name = "photo", columnDefinition = "image")
	private byte[] photo;

	@Column(name = "species")
	private String species;
	
	@Column(name = "breed")
	private String breed;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "gender", columnDefinition = "char")
	private String gender;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "arrival_date")
	private Date arrivalDate;
	
	@Column(name = "intake_reason")
	private String intakeReason;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "departure_date")
	private Date departureDate;
	
	@Column(name = "adopter_name")
	private String adopterName;
	
	@Column(name = "notes")
	private String notes;
	
    // 設定在新增前自動填入日期
    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
    }

}
