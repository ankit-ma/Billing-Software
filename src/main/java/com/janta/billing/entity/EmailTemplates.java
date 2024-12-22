package com.janta.billing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmailTemplates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String templateName;
    private String dynamicKeys;

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
