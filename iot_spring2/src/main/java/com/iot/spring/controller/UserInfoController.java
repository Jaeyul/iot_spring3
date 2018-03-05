package com.iot.spring.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iot.spring.service.UserInfoService;
import com.iot.spring.vo.UserInfoVO;

@Controller
@RequestMapping("/user")
public class UserInfoController {
	@Autowired
	private UserInfoService uis;
	
	private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> login( UserInfoVO ui, HttpSession hs){
		Map<String, Object> map = new HashMap<String, Object>();
		if(uis.login(map, ui, hs)) {
			log.info("map=>{}",map);
		}
		return map;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession hs){

		hs.removeAttribute("user");	
		hs.removeAttribute("isLogin");	
		return "index";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> join(@RequestBody UserInfoVO ui){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "회원가입에 실패하셨습니다.");
		map.put("biz", false);
		log.info("UserInfoVO=>{}",ui);
		int result = uis.join(ui);
		if(result==1) {
			map.put("msg", "회원가입에 성공하셨습니다.");
			map.put("biz", true);
		}else if(result==2) {
			map.put("msg", "중복된 아이디입니다.");
		}
		return map;
	}

	@RequestMapping(value="/check/{uiID}", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> checkID(@PathVariable("uiID")String uiID){
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("uiID=>{}",uiID);
		map.put("msg", "아이디 중복 입니다.");
		map.put("biz", false);
		if(uis.checkUserId(uiID)==0) {
			map.put("msg", "사용가능한 아이디 입니다.");
			map.put("biz", true);
		}
		return map;
	}
}
