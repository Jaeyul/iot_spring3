package com.iot.spring.dao;

import java.util.List;
import java.util.Map;

public interface SqlDAO {
	
	public List<Map<String,Object>> select(String sql);
	public int insert(String sql);
	public int delete(String sql);
	public int update(String sql);

}
