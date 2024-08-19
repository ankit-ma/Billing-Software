package com.janta.billing.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class DueRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@OneToOne
	@JsonIgnore
	CustomerDetails customer;
	
	Double dueAmount;
	Double lastDueAmount;
	
	
	@Column(name = "logged_by",nullable = false,updatable = false)
	Long loggedBy;
	@Column(name = "logged_date",nullable = false,updatable = false)
	LocalDateTime loggedDate;
	@Column(name = "last_updated_on",nullable = false)
	LocalDateTime lastUpdatedOn;
	@Column(name = "last_updated_by",nullable = false)
	Long lastUpdatedBy;
	Integer rowstate;
	
	@PrePersist
    protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
        this.loggedDate = now;
        this.lastUpdatedOn = now;
        this.rowstate=1;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedOn = LocalDateTime.now();
        this.rowstate=1;
    }
}
