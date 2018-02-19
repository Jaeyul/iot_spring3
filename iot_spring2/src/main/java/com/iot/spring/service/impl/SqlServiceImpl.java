package com.iot.spring.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.iot.spring.service.SqlService;

@Service
public class SqlServiceImpl implements SqlService {

	@Override
	public List<Map<String, Object>> getQueryData(String sql, HttpSession hs) {
		SqlSession ss =  (SqlSession) hs.getAttribute("sqlSession");
		
		return null;
	}

}
