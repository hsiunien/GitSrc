package com.softa.fscl.models;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用户实体类
 * 
 * @author hsiunien
 * 
 */

public class User {
	private int userId;
	private String userName;
	private String password;
	private String email;
	private String phone;
	private int credits;
	private int loginState;

	public int getUserId() {
		return userId;
	}

	public void copy(User user) {
		Method[] methods = user.getClass().getMethods();
		try {
			for (int i = 0; i < methods.length; i++) {
				String mName = methods[i].getName();
				if (mName.startsWith("set")) {
			 
					String nName =null;
					//若为boolean的属性则没有get方法 只有 isXXX方法
					 if(boolean.class.equals(methods[i].getParameterTypes()[0]) ){
						  nName=mName.replace("set", "is");
					  }	else{
						  nName=mName.replace("set", "get");
					  }
					
					Method method = user.getClass().getDeclaredMethod(nName,
							null);
					methods[i].invoke(this, method.invoke(user, null));

				}
				 
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getLoginState() {
		return loginState;
	}

	public void setLoginState(int loginState) {
		this.loginState = loginState;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

}
