package com.iot.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iot.spring.service.SqlService;

@Controller
@RequestMapping("/sql")
public class SqlController {
	private static final Logger log = LoggerFactory.getLogger(SqlController.class);
	
	@Autowired
	private SqlService sqls;
	
	@RequestMapping(value="/query/{sql}", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> sqlQuery(
			@PathVariable("sql")String sql,
			HttpSession hs){
		
		log.info("sql=>{}",sql);				
		List<Map<String,Object>> dataList = sqls.getQueryData(sql, hs);
		
		Map<String, Object> hm = new HashMap<String,Object>();
		hm.put("list", dataList);
		
		return hm;
	}
	
	@RequestMapping(value="/update/{sql:.+}", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> sqlUpdate(
			@PathVariable("sql")String sql,
			HttpSession hs){
		
		log.info("sql=>{}",sql);		
		int result = sqls.getUpdateResult(sql, hs);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", result);		
		return map;
	}

}
