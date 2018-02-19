package com.iot.spring.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public interface SqlService {
	
	public List<Map<String,Object>> getQueryData(String sql, HttpSession hs);

}
