package com.janta.billing.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janta.billing.repository.RoleActivityMappingRepository;
import com.janta.billing.repository.RoleActivityRepository;
import com.janta.billing.repository.RoleMasterRepository;
import com.janta.billing.service.ActivityMappingService;
import com.janta.billing.entity.RoleActivityMapping;
import com.janta.billing.entity.RoleMaster;

@Service
public class ActivityMappingServiceImpl implements ActivityMappingService {

	@Autowired
	private RoleActivityMappingRepository roleActivityMappingRepository;
	@Autowired
	private RoleActivityRepository roleActivityRepository;
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Override
	public List<String> getActivityMappingListWithRole(String role) {
		RoleMaster roleMaster= roleMasterRepository.findByRoleName(role);
		
		
		List<String> roleActivityMapping = roleActivityMappingRepository.findActivityNamesByRoleName(roleMaster.getRoleName());
		return roleActivityMapping;
	}

	@Override
	public void addActivityRoleMapping(Long activityId, Long roleId) {
		// TODO Auto-generated method stub
		
	}

}
