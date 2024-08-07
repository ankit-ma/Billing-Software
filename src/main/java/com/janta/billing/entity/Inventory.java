package com.janta.billing.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;
	
	String productName;
	String brandName;
	
	@ManyToOne
	Category category;
	
	String additionalInfo;
	
	Integer quantity;
	Integer thresholdQuantity;
	Double mrp;
	Double sgst;
	Double cgst;
	Double discount;
	
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
