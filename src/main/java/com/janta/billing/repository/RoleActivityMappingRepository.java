package com.janta.billing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janta.billing.entity.RoleActivityMapping;
import com.janta.billing.entity.RoleMaster;

@Repository
public interface RoleActivityMappingRepository extends JpaRepository<RoleActivityMapping, Long> {

	RoleActivityMapping findByRole(RoleMaster roleMaster);

	//List<RoleActivityMapping> findAllByRole(RoleMaster roleMaster);

	@Query("SELECT ram.activity.activityName FROM RoleActivityMapping ram WHERE ram.role.roleName = :roleName")
    List<String> findActivityNamesByRoleName(@Param("roleName") String roleName);

}
