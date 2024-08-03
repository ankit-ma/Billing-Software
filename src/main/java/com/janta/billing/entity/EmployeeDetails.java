package com.janta.billing.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmployeeDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	RoleMaster role;
	String employeeName;
	String phoneNumber;
	String password;
	String email;
	String designation;
	@Column(name = "logged_date",nullable = false,updatable = false)
	LocalDateTime loggedDate;
	@Column(name = "last_updated_on", nullable = false)
	LocalDateTime lastUpdatedOn;
	Integer rowstate;
	
	@PrePersist
    protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
        this.loggedDate = now;
        this.lastUpdatedOn = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedOn = LocalDateTime.now();
    }
}
