package com.janta.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.RoleActivity;

@Repository
public interface RoleActivityRepository extends JpaRepository<RoleActivity, Long> {

}
