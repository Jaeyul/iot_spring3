package com.iot.spring.service.impl;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.spring.dao.UserInfoDAO;
import com.iot.spring.service.UserInfoService;
import com.iot.spring.vo.UserInfoVO;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDAO uidao;
	
	@Override
	public boolean login(Map<String, Object> rMap, UserInfoVO ui, HttpSession hs) {
		ui = uidao.selectUserInfo(ui);
		rMap.put("msg", "아이디 비밀번호를 확인해주세요.");
		rMap.put("biz", false);
		hs.setAttribute("isLogin", false);
		if(ui!=null) {
			rMap.put("msg", ui.getUiName() + "님 로그인에 성공하셨습니다.");
			rMap.put("biz", true);
			
			hs.setAttribute("user", ui);
			hs.setAttribute("isLogin", true);
			return true;
		}
		return false;		
	}
	

	public int checkUserId(String uiId) {
		UserInfoVO ui = new UserInfoVO();
		ui.setUiID(uiId);
		if(uidao.selectUserInfo(ui)!=null){
			return 1;
		}
		return 0;
	}
	
	@Override
	public int join(UserInfoVO ui) {
		if(checkUserId(ui.getUiID())==1) {
			return 2;
		}
		return uidao.insertUserInfo(ui);
	}

	

}
