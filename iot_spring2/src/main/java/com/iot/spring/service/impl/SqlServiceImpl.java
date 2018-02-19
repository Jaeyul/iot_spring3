package com.iot.spring.service.impl;

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

}
