package com.janta.billing.service;

import java.util.List;

public interface ActivityService {

	public List<String> fetchActiviList();
	public void addActivity(String activity);
	public void updateActivity(String activity);
	public void deleteActivity(String activity);
}
