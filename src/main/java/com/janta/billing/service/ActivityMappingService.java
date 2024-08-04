package com.janta.billing.service;

import java.util.List;

import com.janta.billing.entity.RoleActivityMapping;

public interface ActivityMappingService {
	public List<String> getActivityMappingListWithRole(String role);
	
	public void addActivityRoleMapping(Long activityId,Long roleId);
}
