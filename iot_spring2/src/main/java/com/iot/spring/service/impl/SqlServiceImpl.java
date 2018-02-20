package com.iot.spring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.spring.dao.SqlDAO;
import com.iot.spring.service.SqlService;

@Service
public class SqlServiceImpl implements SqlService {
	
	@Autowired
	private SqlDAO sdao;

	@Override
	public List<Map<String, Object>> getQueryData(String sql, HttpSession hs) {
		SqlSession ss =  (SqlSession) hs.getAttribute("sqlSession");
		
	
		return sdao.selectQueryData(sql, ss);
	}

	@Override
	public int getUpdateResult(String sql, HttpSession hs) {
		SqlSession ss =  (SqlSession) hs.getAttribute("sqlSession");
				
		return sdao.getUpdateResult(sql, ss);
	}

	@Override
	public Map<String, Object> getMultiResult(String sql, HttpSession hs) {
		Map<String, Object> map = new HashMap<String,Object>();
		SqlSession ss =  (SqlSession) hs.getAttribute("sqlSession");
		String[] sqlArr = sql.split(";");
		int qIndex = 0;
		int uIndex = 0;		
		
		for(int i=0; i<sqlArr.length; i++) {
			if(sqlArr[i].trim().indexOf("select") == 0) {
				List<Map<String,Object>> list =  sdao.selectQueryData(sqlArr[i].trim(), ss);
				qIndex++;				
				map.put("list"+qIndex, list);
				
			}else {
				int result = sdao.getUpdateResult(sqlArr[i].trim(), ss);
				uIndex++;
				map.put("result"+uIndex, result);
			}			
		}	
		return map;
	}

}
