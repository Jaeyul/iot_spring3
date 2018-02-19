package com.iot.spring.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public interface SqlDAO {
	
	public List<Map<String,Object>> selectQueryData(String sql, SqlSession ss);

}
