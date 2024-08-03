package com.janta.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.RoleMaster;

@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long> {

	RoleMaster findByRoleName(String string);

}
