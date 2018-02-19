package com.iot.spring.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sql")
public class SqlController {
	private static final Logger log = LoggerFactory.getLogger(SqlController.class);
	
	@RequestMapping(value="/query/{sql}", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> sqlQuert(
			@PathVariable("sql")String sql,
			HttpSession hs){
		
		log.info("sql=>{}",sql);		
		return null;
	}

}
