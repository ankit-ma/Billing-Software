package com.janta.billing.util;

import java.util.Map;

public class MapUtility {

	public void mergeMaps(Map<String,Object> map1,Map<String,Object> map2)
	{
		for(Map.Entry<String, Object> entry: map2.entrySet()) {
			map1.merge(entry.getKey(), entry.getValue(), (v1,v2)->{
				if(v1 instanceof Map && v2 instanceof Map) {
					mergeMaps((Map<String,Object>) v1, (Map<String,Object>) v2);
					return v1;
				}
				return v2;
			});
		}
	}
}
