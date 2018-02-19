package com.iot.spring.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.iot.spring.dao.SqlDAO;

@Repository
public class SqlDAOImpl implements SqlDAO {

	@Override
	public List<Map<String, Object>> selectQueryData(String sql, SqlSession ss) {
		
		List<Map<String,Object>> dataList = ss.selectList("sql.executeQuery", sql); 
		
		return dataList;
	}

	@Override
	public int getUpdateResult(String sql, SqlSession ss) {
		int result = ss.update("sql.executeUpdate", sql);
		return result;
	}

}
