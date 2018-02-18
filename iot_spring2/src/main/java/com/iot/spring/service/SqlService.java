package com.iot.spring.service;

import java.util.List;
import java.util.Map;

public interface SqlService {
	
	public List<Map<String,Object>> executeQuery(String sql);
	public int executeUpdate(String sql);

}
