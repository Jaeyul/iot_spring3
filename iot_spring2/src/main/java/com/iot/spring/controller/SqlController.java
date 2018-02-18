package com.iot.spring.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iot.spring.dao.NaverTransDAO;
import com.iot.spring.service.SqlService;
import com.iot.spring.vo.NaverMsg;



@Controller
@RequestMapping("/sql")
public class SqlController {
	private static final Logger log = LoggerFactory.getLogger(SqlController.class);
	
	@Autowired
	SqlService ss;
	
	@Autowired
	private ObjectMapper om;
	
	@Autowired
	private NaverTransDAO ntDAO;
	
	@RequestMapping(value="/query/{sql}", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getSelectData(
			@PathVariable("sql")String sql, 			
			HttpSession hs,
			Map<String,Object> map) {
		
		log.info("sql=>{}", sql);			
		List<Map<String,Object>> List = ss.executeQuery(sql);
		String colStr = "";
		String typeStr = "";
		int idx = 0;
		for(int i=0; i<List.size(); i++) {
			if(List.get(i).get("colStr")!=null && List.get(i).get("typeStr")!=null) {
				colStr = (String) List.get(i).get("colStr");
				typeStr = (String) List.get(i).get("typeStr");						
				idx = i;
			}			
		}
		List.remove(idx);
					
		map.put("list", List);		
		map.put("colStr", colStr);
		map.put("typeStr", typeStr);
		return map;
	}
	
	@RequestMapping(value="/trans/{translate}", method=RequestMethod.GET)
	public @ResponseBody Map<String,String> getTranslateStr(
			@PathVariable("translate")String translate, 			
			HttpSession hs,
			Map<String,String> map) {
		
		log.info("sql=>{}", translate);	
		try {
			String msg = ntDAO.getText(translate);
			NaverMsg nm = om.readValue(msg, NaverMsg.class);
			String transStr = nm.getMessage().getResult().getTranslatedText();
			map.put("str", transStr);
			return map;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}


	
	
}
