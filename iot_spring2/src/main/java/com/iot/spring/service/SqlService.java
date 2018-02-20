package com.iot.spring.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public interface SqlService {
	
	public List<Map<String,Object>> getQueryData(String sql, HttpSession hs);
	public int getUpdateResult(String sql, HttpSession hs);
	public Map<String,Object> getMultiResult(String sql, HttpSession hs);
	
}
