package com.htsoft.est.action.system;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import nl.captcha.Captcha;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.htsoft.core.util.StringUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.SysConfig;
import com.htsoft.est.service.system.AppUserService;
import com.htsoft.est.service.system.SysConfigService;

public class LoginAction extends BaseAction {
	private AppUser user;
	private String username;
	private String password;
	private String checkCode;// 验证码

	// must be same to app-security.xml
	private String key = "RememberAppUser";

	// private String rememberMe;//自动登录
	@Resource
	private AppUserService userService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;

	/**
	 * jsp登录
	 * 
	 * @return
	 */
	public String login() {
		jsonString = "{";
		AppUser user = userService.findByUserName(username);
		// 取得验证码配置
		SysConfig codeConfig = sysConfigService.findByKey("codeConfig");
		if (user != null) {
			// 判断密码及用户名是否一致
			//String newPassword = StringUtil.encryptSha256(password);
			String newPassword = password;
			// 密码是否一致
			if (!user.getPassword().equals(newPassword)) {
				jsonString += "msg:'密码不正确',";
			}
			if(user.getStatus()==0){
				jsonString += "msg:'账户还未通过审核，请耐心等待',";
			}
			
			if(jsonString.length()==1){
				// 检查验证码
				// 取得验证码
				Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
				
				// 判断是否需要验证码验证
				if (codeConfig != null && codeConfig.getDataValue().equals(SysConfig.CODE_OPEN)) {
					// 验证码验证
					if (captcha!=null && !captcha.isCorrect(checkCode)) {
						jsonString += "msg:'验证码不正确',";
					}
				} 
				if(jsonString.length()==1){
					UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
					SecurityContextHolder.setContext(securityContext);
					getSession().setAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY, username);
					String rememberMe = getRequest().getParameter("_spring_security_remember_me");
		
					if (rememberMe != null && rememberMe.equals("on")) {
						// 加入cookie
						long tokenValiditySeconds = 1209600; // 14 days
						long tokenExpiryTime = System.currentTimeMillis() + (tokenValiditySeconds * 1000);
						String signatureValue = DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" + user.getPassword() + ":" + key);
						String tokenValue = username + ":" + tokenExpiryTime + ":" + signatureValue;
						String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue.getBytes()));
						getResponse().addCookie(makeValidCookie(tokenExpiryTime, tokenValueBase64));
					}
				}
			}

		} else {// 用户不存在
			jsonString += "msg:'用户不存在',";
		}
		
		if(jsonString.length()>1){
			jsonString += "success:false}";
		}else{
			jsonString += "success:true}";
		}
		
		setJsonString(jsonString);
		System.out.println(jsonString);
		return SUCCESS;
	}


	
	/**
	 * Ext登录
	 * 
	 * @return
	 */
	public String loginDefault() {
		// 定义验证信息
		StringBuffer msg = new StringBuffer("{msg:'");
		// 取得验证码配置
		SysConfig codeConfig = sysConfigService.findByKey("codeConfig");

		// 取得验证码
		Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
		Boolean login = false;

		String newPassword = null;
		// 用户名不为空
		if (!"".equals(username) && username != null) {
			setUser(userService.findByUserName(username));
			// 验证用户是否存在
			if (user != null) {
				// 密码不为空
				if (StringUtils.isNotEmpty(password)) {
					// 密码加密
					newPassword = StringUtil.encryptSha256(password);
					// 密码验证
					if (user.getPassword().equalsIgnoreCase(newPassword)) {
						// 判断是否需要验证码验证
						if (codeConfig != null && codeConfig.getDataValue().equals(SysConfig.CODE_OPEN)) {
							if (captcha == null) {
								msg.append("请刷新验证码再登录.'");
							} else {
								// 验证码验证
								if (captcha.isCorrect(checkCode)) {
									login = dyPwdCheck(msg, login);
								} else
									msg.append("验证码不正确.'");
							}
						} else {
							// 此处不需要验证码验证
							login = dyPwdCheck(msg, login);
						}
					} else
						msg.append("密码不正确.'");
				} else
					msg.append("密码不能为空.'");
			} else
				msg.append("用户不存在.'");
		}
		if (login) {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
			SecurityContextHolder.setContext(securityContext);
			getSession().setAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY, username);
			String rememberMe = getRequest().getParameter("_spring_security_remember_me");

			if (rememberMe != null && rememberMe.equals("on")) {
				// 加入cookie
				long tokenValiditySeconds = 1209600; // 14 days
				long tokenExpiryTime = System.currentTimeMillis() + (tokenValiditySeconds * 1000);
				// DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" +
				// password + ":" + getKey());
				String signatureValue = DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" + user.getPassword() + ":" + key);
				String tokenValue = username + ":" + tokenExpiryTime + ":" + signatureValue;
				String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue.getBytes()));
				getResponse().addCookie(makeValidCookie(tokenExpiryTime, tokenValueBase64));
			}
			setJsonString("{success:true}");
			/**
			 * jforum论坛整合
			 */
			try {
				username = URLEncoder.encode(username, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Cookie cookie = new Cookie("jforumSSOCookieNameUser", username + "," + user.getPassword() + "," + user.getEmail());
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			getResponse().addCookie(cookie);
		} else {
			msg.append(",failure:true}");
			setJsonString(msg.toString());
		}

		return SUCCESS;
	}

	// add Cookie
	protected Cookie makeValidCookie(long expiryTime, String tokenValueBase64) {
		HttpServletRequest request = getRequest();
		Cookie cookie = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, tokenValueBase64);
		cookie.setMaxAge(60 * 60 * 24 * 365 * 5); // 5 years
		cookie.setPath(org.springframework.util.StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
		return cookie;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	private boolean dyPwdCheck(StringBuffer msg, boolean login) {
		// 取得动态密码配置
		SysConfig dyPwdConfig = sysConfigService.findByKey("dynamicPwd");
		// 判断是否需要动态密码
		if (dyPwdConfig != null && dyPwdConfig.getDataValue().equals(SysConfig.DYPWD_OPEN)) {
			// 动态密码配置为打开状态
			if (user.getUserId().longValue() == AppUser.SUPER_USER.longValue()) {
				// 假如是超级管理员,则不需要动态密码
				login = true;
			} else {
				if (StringUtils.isEmpty(user.getDynamicPwd())) {
					msg.append("此用户未有令牌,请联系管理员.'");
				} else if (user.getDyPwdStatus().shortValue() == AppUser.DYNPWD_STATUS_UNBIND.shortValue()) {
					msg.append("此用户令牌未绑定,请联系管理员.'");
				} else {
					String curDynamicPwd = getRequest().getParameter("curDynamicPwd");
					HashMap<String, String> input = new HashMap<String, String>();
					input.put("app", "demoauthapp");
					input.put("user", user.getDynamicPwd());
					input.put("pw", curDynamicPwd);

					String result = userService.initDynamicPwd(input, "verify");
					if (result.equals("ok")) {
						if (user.getStatus() == 1) {
							login = true;
						} else
							msg.append("此用户已被禁用.'");
					} else {
						msg.append("令牌不正确,请重新输入.'");
					}
				}
			}

		} else {
			// 此处不需要动态密码
			// 判断用户是否被禁用,超级管理员不可被禁用
			if (user.getStatus() == 1 || user.getUserId().longValue() == AppUser.SUPER_USER.longValue()) {
				login = true;
			} else
				msg.append("此用户已被禁用.'");
		}

		return login;
	}

	/**
	 * 验证码 <no use>
	 * 
	 * @return
	 */
	public String validateCaptcha() {
		// 定义验证信息
		StringBuffer msg = new StringBuffer("{msg:'");
		// 取得验证码配置
		SysConfig codeConfig = sysConfigService.findByKey("codeConfig");

		// 取得验证码
		Captcha captcha = (Captcha) getSession().getAttribute(Captcha.NAME);
		Boolean login = false;

		// 判断是否需要验证码验证
		if (codeConfig != null && codeConfig.getDataValue().equals(SysConfig.CODE_OPEN)) {
			if (captcha == null) {
				msg.append("请刷新验证码再登录.'");
			} else {
				// 验证码验证
				if (captcha.isCorrect(checkCode)) {
					// login = dyPwdCheck(msg, login);
					setJsonString("{success:true}");
				} else {
					msg.append("验证码不正确.'");
					msg.append(",failure:true}");
					setJsonString(msg.toString());
				}
			}
		} else {
			// 此处不需要验证码验证
			// login = dyPwdCheck(msg, login);
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}
}
