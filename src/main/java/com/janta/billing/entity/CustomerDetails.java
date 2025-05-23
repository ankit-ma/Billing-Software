package com.janta.billing.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class CustomerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	String customerName;
	String phoneNumber;
	String address;
	String email;
	@Column(name = "logged_by",nullable = false,updatable = false)
	Long loggedBy;
	@Column(name = "logged_date",nullable = false,updatable = false)
	LocalDateTime loggedDate;
	@Column(name = "last_updated_on",nullable = false)
	LocalDateTime lastUpdatedOn;
	@Column(name = "last_updated_by",nullable = false)
	Long lastUpdatedBy;
	Integer rowstate;
	
	@OneToMany(mappedBy ="customerDetails",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference 
	List<BillRecord> billRecords;
	
	@OneToOne(mappedBy = "customer")
	@JsonManagedReference 
	DueRecord dueRecord;
	
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
