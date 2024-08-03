package com.janta.billing.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String roleName;
	LocalDateTime loggedDate;
	LocalDateTime lastUpdatedOn;
	
	@OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
	List<EmployeeDetails> employees;
	
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
