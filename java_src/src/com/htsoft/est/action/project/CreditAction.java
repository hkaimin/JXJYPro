package com.htsoft.est.action.project;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.jxjy.JxjyXflb;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.model.system.UserOrg;
import com.htsoft.est.service.project.CreditService;
import com.htsoft.est.service.project.ProjectService;
import com.htsoft.est.service.system.OrganizationService;
import com.htsoft.est.service.system.UserOrgService;

/**
 * 
 * @author guojy
 *
 */
public class CreditAction extends BaseAction {
	
	@Resource
	private CreditService creditService;
	
	private JxjyXflb credit;
	
	public String save() {
		JxjyXflb temp = this.creditService.saveLb(this.credit);
		if(temp != null) {
			jsonString = "{success:true}";
		} else {
			jsonString = "{success:false,message:'保存信息失败！'}";
		}
		return SUCCESS;
	}
	
	public String modified() {
		String[] ids = this.getRequest().getParameterValues("ids");
		String flage = this.getRequest().getParameter("flage");
		this.creditService.batchModified(ids, flage);
		return SUCCESS;
	}
	
	/**
	 * 显示列表
	 * @throws  
	 */
	@Override
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		String method = (getRequest().getParameter("method") == null) ? ""
				: getRequest().getParameter("method");

		if (method.equals("search")) { 	// 条件查询

//			List<JxjyXmgl> list = this.projectService.listProject(filter);
//			Type type = new TypeToken<List<JxjyXmgl>>() {
//			}.getType();
//			StringBuffer buff = new StringBuffer(
//					"{success:true,'totalCounts':").append(
//					filter.getPagingBean().getTotalItems()).append(",result:");
//
//			Gson gson = new Gson();
//			buff.append(gson.toJson(list, type));
//			buff.append("}");
//
//			jsonString = buff.toString();

		} else { 	
			
			List<JxjyXflb> list = this.creditService.loadAll();

			Type type = new TypeToken<List<JxjyXflb>>() {
			}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(list.size()).append(
							",result:");

			Gson gson = new Gson();
			buff.append(gson.toJson(list, type));
			buff.append("}");

			jsonString = buff.toString();
		} 

		return SUCCESS;
	}


	public JxjyXflb getCredit() {
		return credit;
	}


	public void setCredit(JxjyXflb credit) {
		this.credit = credit;
	}
	
}
