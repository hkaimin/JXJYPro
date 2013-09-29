package com.htsoft.est.action.mobile;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.htsoft.core.util.StringUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.system.AppUserService;

public class SignInAction extends BaseAction{
	@Resource
	private AppUserService userService;
	
	@Resource(name="authenticationManager")
	private AuthenticationManager authenticationManager=null; 
	
	private String username;
	private String password;

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

	@Override
	public String execute() throws Exception {
		if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)){
			AppUser user=userService.findByUserName(username);
			if(user!=null){
				String enPassword=StringUtil.encryptSha256(password);
				if(enPassword.equals(user.getPassword())){
					
					UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
					SecurityContextHolder.setContext(securityContext);
					
					return SUCCESS;
				}
			}
		}
		
		return INPUT;
		
	}
}
