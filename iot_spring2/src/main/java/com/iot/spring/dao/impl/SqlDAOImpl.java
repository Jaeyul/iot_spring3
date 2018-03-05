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
		
		if(sql.indexOf("where ")!= -1) {
			int indexWhere = sql.indexOf("where");	
			sql = sql.substring(0, indexWhere).trim();
		}				
		if(sql.lastIndexOf("as ") != -1) {
			int indexAsStr = sql.lastIndexOf("as ");
			sql = sql.substring(indexAsStr+2).trim();
		}else {
			int indexStr = sql.lastIndexOf(" ");	
			sql = sql.substring(indexStr).trim();
		}
		String tbName = sql;
		
		if(dataList!=null) {
			dataList.get(0).put("tName", tbName);
		}
		
		
		return dataList;
	}

	@Override
	public int getUpdateResult(String sql, SqlSession ss) {
		int result = ss.update("sql.executeUpdate", sql);
		return result;
	}

}
