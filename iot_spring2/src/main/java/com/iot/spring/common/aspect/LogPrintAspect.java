package com.iot.spring.common.aspect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.spring.dao.NaverTransDAO;
import com.iot.spring.vo.NaverMsg;


@Service
@Aspect
public class LogPrintAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogPrintAspect.class);
	
	@Autowired
	private ObjectMapper om;	
	@Autowired
	private NaverTransDAO ntDAO;
	
	
	@Before("execution(* com.iot.spring.controller.*Controller.*(..))")
	public void beforeLog(JoinPoint jp) {
		logger.info("@Before =>{}", jp);		
	}
	
	@Around("execution(* com.iot.spring.controller.*Controller.*(..))")
	public Object aroundLog(ProceedingJoinPoint pjp) throws JsonParseException, JsonMappingException, IOException {
		logger.info("@Around begin");
		Object obj = null;
		long startTime = System.currentTimeMillis();
		try {			
			
			obj = pjp.proceed();				
			
		} 
//		catch(Exception e){
//			logger.error("error=>{}", e);	
//			Map<String,Object> map = new HashMap<String,Object>();
//			String msg = e.getMessage();
//			msg = ntDAO.getText(msg);	
//			logger.error("error=>{}", msg);	
//			//NaverMsg nm = om.readValue(msg, NaverMsg.class);
//			map.put("errorMsg", msg);
//			
//			logger.error("errorasdasdasdasd =>{}",map);
//			return map;
//		} 
		catch (Throwable e) {
			logger.error("error=>{}", e);	
			Map<String,Object> map = new HashMap<String,Object>();
			String msg = ntDAO.getText(e.getMessage());				
			NaverMsg nm = om.readValue(msg, NaverMsg.class);
			map.put("errorMsg", nm.getMessage().getResult().getTranslatedText());			
			return map;
		}
		long time = System.currentTimeMillis()-startTime;
		logger.info("@Around end, RunTime : {} ms", time);		
		return obj;		
	}
	
	
	@After("execution(* com.iot.spring.controller.*Controller.*(..))")
	public void afterLog(JoinPoint jp) {
		
		logger.info("@After =>{}", jp);
	}
	
}
