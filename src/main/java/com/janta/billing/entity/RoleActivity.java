package com.janta.billing.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleActivity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String activityName;
	@OneToMany(mappedBy = "activity",fetch = FetchType.LAZY)
	List<RoleActivityMapping> activityList;
}
