package com.softa.fscl.net;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.softa.fscl.models.BaseRequestEntity;
import com.softa.fscl.models.User;
import com.softa.fscl.net.WebServiceClient.WebServiceListener;

public class UserServiceClient {
	private static String requestName = "Android_WebService_User.asmx";
	private static String paramName = "stringuser";
	private WebServiceClient client;
	private UserServiceListenr listener;
	private static final int OP_LOGIN = 1, OP_REG = 2;
	 

	public UserServiceClient(UserServiceListenr listener) {
		client = new WebServiceClient(listener);
		this.listener = listener;
		client.setRequestName(requestName);
	}

	public void helloWorld() {
		client.requestService("HelloWorld", paramName, null);
	}

	public void loginByPhone(String phone, String pwd) {
		RequestEntity entity = new RequestEntity();
		entity.setPhone(phone);
		entity.setPwd(pwd);
		listener.op=OP_LOGIN;
		client.requestService("loginByPhone", paramName, entity);
	}

	public void loginByEmail(String email, String pwd) {

		RequestEntity entity = new RequestEntity();
		entity.setEmail(email);
		entity.setPwd(pwd);
		listener.op=OP_LOGIN;
		client.requestService("loginByEmail", paramName, entity);
	}

	public void regist(String email, String phone, String pwd, String name) {
		RequestEntity entity = new RequestEntity();
		entity.setEmail(email);
		entity.setPwd(pwd);
		entity.setPhone(phone);
		entity.setRelname(name);
		listener.op=OP_REG;
		client.requestService("AddUser", paramName, entity);
	}

	private class ResponseEntity extends BaseRequestEntity {
		int UserID;
		int UserLevelID;
		String UserName;
		String UserRealName;
		String UserPwd;
		String E_Mail;
		String Phone;
		int TotalCredits;

		public int getTotalCredits() {
			return TotalCredits;
		}

		public void setTotalCredits(int totalCredits) {
			TotalCredits = totalCredits;
		}

		public String getUserName() {
			return UserName;
		}

		public void setUserName(String userName) {
			UserName = userName;
		}

		public String getUserRealName() {
			return UserRealName;
		}

		public void setUserRealName(String userRealName) {
			UserRealName = userRealName;
		}

		public String getUserPwd() {
			return UserPwd;
		}

		public void setUserPwd(String userPwd) {
			UserPwd = userPwd;
		}

		public String getE_Mail() {
			return E_Mail;
		}

		public void setE_Mail(String e_Mail) {
			E_Mail = e_Mail;
		}

		public String getPhone() {
			return Phone;
		}

		public void setPhone(String phone) {
			Phone = phone;
		}

		public int getUserID() {
			return UserID;
		}

		public void setUserID(int userID) {
			UserID = userID;
		}

		public int getUserLevelID() {
			return UserLevelID;
		}

		public void setUserLevelID(int userLevelID) {
			UserLevelID = userLevelID;
		}
	}

	private class RequestEntity extends BaseRequestEntity {
		private String phone, pwd, email, relname;

		public String getRelname() {
			return relname;
		}

		public void setRelname(String relname) {
			this.relname = relname;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getPwd() {
			return pwd;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

	}

	public static abstract class UserServiceListenr implements
			WebServiceListener {
	
		public int op = 0;
		
		@Override
		public void onRequestStart() {

		}

		@Override
		public void onNetError(String msg) {
			switch (op) {
			case OP_LOGIN:
			onLoginFalse(msg);
			break;
			case OP_REG:
				onRegisted(-1, msg);
				break;
			}
		}

		@Override
		public void onComplete(String resultJson) {
			switch (op) {
			case OP_LOGIN:
				if ("\"False\"".equalsIgnoreCase(resultJson)) {
					onLoginFalse("用户名或密码错误");
				} else {
					Gson gson = new Gson();
					try {

						ResponseEntity e = gson.fromJson(resultJson,
								ResponseEntity.class);
						if (e != null) {
							User user = new User();
							user.setUserId(e.getUserID());
							user.setPassword(e.getUserPwd());
							user.setUserName(e.getUserRealName());
							user.setEmail(e.getE_Mail());
							user.setLoginState(1);
							user.setCredits(e.getTotalCredits());
							onLoginSuccess(user);
						} else {
							onLoginFalse("获取用户信息错误");
						}
					} catch (JsonSyntaxException e) {
						onLoginFalse("非法json格式");
					}
				}
				
				break;
			case OP_REG:
				Gson gson = new Gson();
				 JsonObject json=gson.fromJson(resultJson, JsonObject.class);
				 onRegisted(json.get("Code").getAsInt(),json.get("Msg").getAsString());
			break;
			default:
				break;
			}
			op = 0;	
		}
		public void onRegisted(int code,String msg){
			
		}
		
		public void onLoginFalse(String msg) {
		};
		public void onLoginSuccess(User user) {

		};
	}
}
