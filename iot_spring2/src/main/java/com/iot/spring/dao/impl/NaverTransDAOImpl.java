package com.iot.spring.dao.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.iot.spring.dao.NaverTransDAO;


public class NaverTransDAOImpl implements NaverTransDAO{
	
	
	private String url;
	private String contentType;
	private String clientId;
	private String clientSecret;
	private String source;
	private String target;

	public void setUrl(String url) {
		this.url = url;
	
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Override
	public String getText(String text) throws IOException  {
		HttpURLConnection con = null;
		BufferedReader br = null;
		
		try {
			text = URLEncoder.encode(text, "UTF-8");			
			URL url = new URL(this.url);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", this.clientId);
			con.setRequestProperty("X-Naver-Client-Secret", this.clientSecret);
			con.setRequestProperty("Content-Type", this.contentType); 
			// post request
			String postParams = "source=" + this.source + "&target=" + this.target + "&text=" + text.substring(50,250);
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();			
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			return response.toString();

		} catch (Exception e) {
			System.out.println(e);
		} finally {			
			if(con!=null) {
				con.disconnect();				
			}
			if(br!=null) {
				br.close();
			}			
		}
		return "";
	}
	
	
	
}
