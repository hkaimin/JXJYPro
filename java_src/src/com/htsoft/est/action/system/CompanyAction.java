package com.htsoft.est.action.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.htsoft.core.Constants;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.system.Company;
import com.htsoft.est.service.system.CompanyService;


public class CompanyAction extends BaseAction{

	private Company company;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Resource
	private CompanyService companyService;
	

	public String check(){
		List<Company> list = companyService.findCompany();
		if(list.size()>0){
			setJsonString("{success:true}");
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	
	public String list(){
		List<Company> list = companyService.findCompany();
		if(list.size()>0){
			company = list.get(0);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			StringBuffer cf = new StringBuffer("{success:true,result:[");
			cf.append(gson.toJson(company));
			cf.append("]}");
			setJsonString(cf.toString());
		}else{
			setJsonString("{success:false,message:'还没有填写公司信息'}");
			return SUCCESS;			
		}		
		return SUCCESS;

	}

	public String add(){
		companyService.save(company);
		Map map=AppUtil.getSysConfig();
		map.remove(Constants.COMPANY_LOGO);
		map.remove(Constants.COMPANY_NAME);
		map.put(Constants.COMPANY_LOGO,company.getLogo());
		map.put(Constants.COMPANY_NAME,company.getCompanyName());
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String delphoto(){
		List<Company> list = companyService.findCompany();
		if(list.size()>0){
			company = list.get(0);
			company.setLogo("");
			companyService.save(company);
		}
		return SUCCESS;
	}

}
