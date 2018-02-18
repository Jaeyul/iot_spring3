package com.iot.spring.vo;

public class UserInfoVO {
	private String uiName;
	private String uiID;
	private String uiPwd;	
	private String uiEmail;
	private String admin;
	public String getUiName() {
		return uiName;
	}
	public void setUiName(String uiName) {
		this.uiName = uiName;
	}
	public String getUiID() {
		return uiID;
	}
	public void setUiID(String uiID) {
		this.uiID = uiID;
	}
	public String getUiPwd() {
		return uiPwd;
	}
	public void setUiPwd(String uiPwd) {
		this.uiPwd = uiPwd;
	}
	public String getUiEmail() {
		return uiEmail;
	}
	public void setUiEmail(String uiEmail) {
		this.uiEmail = uiEmail;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	@Override
	public String toString() {
		return "UserInfoVO [uiName=" + uiName + ", uiID=" + uiID + ", uiPwd=" + uiPwd + ", uiEmail=" + uiEmail
				+ ", admin=" + admin + "]";
	}
	
//	public int getUiNo() {
//		return uiNo;
//	}
//	public void setUiNo(int uiNo) {
//		this.uiNo = uiNo;
//	}
	
	
}
