package com.iot.spring.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.spring.dao.SqlDAO;
import com.iot.spring.service.SqlService;

@Service
public class SqlServiceImpl implements SqlService {
	@Autowired
	SqlDAO sdao;
	

	@Override
	public List<Map<String, Object>> executeQuery(String sql) {
		
		return sdao.select(sql);
	}

	@Override
	public int executeUpdate(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

}
