package com.iot.spring.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.iot.spring.vo.UserInfoVO;

public interface UserInfoService {
	public boolean login(Map<String,Object> rMap, UserInfoVO ui, HttpSession hs);
	public int join(UserInfoVO ui);
	public int checkUserId(String uiId);
}
