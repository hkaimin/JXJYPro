package com.htsoft.est.action.project;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.dao.project.DbryDao;
import com.htsoft.est.model.jxjy.JxjyDbbz;
import com.htsoft.est.model.jxjy.JxjyDbry;
import com.htsoft.est.model.jxjy.JxjyKtgl;
import com.htsoft.est.service.project.DbbzService;

public class DbbzAction extends BaseAction{

	@Resource
	private DbbzService dbbzService;
	@Resource
	private DbryDao dbryDao;
	
	private JxjyDbbz biaozhun;
	
	private JxjyDbry dbry;
	
	public String listCheck() {
		
		QueryFilter filter = new QueryFilter(getRequest());
		
		if(this.dbry == null) {
			this.dbry = new JxjyDbry();
		}
		
		List<JxjyDbry> list = this.dbryDao.getAllList(filter, this.dbry);
		Type type = new TypeToken<List<JxjyDbry>>() {
		}.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':").append(
				filter.getPagingBean().getTotalItems()).append(",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();
		
		return SUCCESS;
	}
	
	public String checkOrg() {
		String orgId = this.getRequest().getParameter("orgId");
		String yearNo = this.getRequest().getParameter("yearNo");
		String zc = this.getRequest().getParameter("zc");
		this.dbbzService.checkOrg(orgId, zc, yearNo);
		return SUCCESS;
	}
	
	public String checkPerson() {
		String userNo = this.getRequest().getParameter("userNo");
		String yearNo = this.getRequest().getParameter("yearNo");
		this.dbbzService.checkPerson(userNo, yearNo);
		return SUCCESS;
	}
	
	public String get(){
		
		JxjyDbbz bz = this.dbbzService.get(new Long(this.getRequest().getParameter("id")));
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bz));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		this.dbbzService.multiDel(ids);
		return SUCCESS;
	}
	
	public String saveBiaozhun() {
		JxjyDbbz temp = this.dbbzService.saveBiaozhun(this.biaozhun);
		if(temp != null) {
			jsonString = "{success:true}";
		} else {
			jsonString = "{success:false,message:'保存信息失败！'}";
		}
		return SUCCESS;
	}
	
	public String list() {
		
		QueryFilter filter = new QueryFilter(getRequest());
		
		List<JxjyDbbz> list = this.dbbzService.list(filter);
		Type type = new TypeToken<List<JxjyDbbz>>() {
		}.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':").append(
				filter.getPagingBean().getTotalItems()).append(",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	public JxjyDbbz getBiaozhun() {
		return biaozhun;
	}

	public void setBiaozhun(JxjyDbbz biaozhun) {
		this.biaozhun = biaozhun;
	}

	public JxjyDbry getDbry() {
		return dbry;
	}

	public void setDbry(JxjyDbry dbry) {
		this.dbry = dbry;
	}
	
}
