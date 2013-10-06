package com.htsoft.est.action.project;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.dao.system.AppUserDao;
import com.htsoft.est.model.jxjy.JxjyDbbz;
import com.htsoft.est.model.jxjy.JxjyKtgl;
import com.htsoft.est.model.jxjy.JxjyXflb;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.model.jxjy.JxjyZc;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.model.system.UserOrg;
import com.htsoft.est.model.vo.JxjyYyType;
import com.htsoft.est.service.project.CreditService;
import com.htsoft.est.service.project.DbbzService;
import com.htsoft.est.service.project.ProjectService;
import com.htsoft.est.service.project.ZcService;
import com.htsoft.est.service.system.OrganizationService;
import com.htsoft.est.service.system.UserOrgService;
import com.htsoft.est.util.JxjyConstant;

import flex.messaging.io.ArrayList;

/**
 * 
 * @author guojy
 *
 */
public class CreditAction extends BaseAction {
	
	@Resource
	private CreditService creditService;
	@Resource
	private ZcService zcService;
	@Resource
	private DbbzService dbbzService;
	@Resource
	private AppUserDao appUserDao;
	
	private JxjyXflb credit;
	private JxjyDbbz biaozhun;
	
	public String getUserByNbbh() {
		String userNo = this.getRequest().getParameter("userNo");
		AppUser user = this.appUserDao.getByFx(userNo);
		StringBuffer sb = new StringBuffer();
		if(user != null) {
			sb.append("{success:true,userName:'" + user.getFullname() + "',userId:'" + user.getUserId() + "'");
			sb.append("}");
		} else {
			sb.append("{success:false");
			sb.append("}");
		}
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		this.creditService.multiDel(ids);
		return SUCCESS;
	}
	
	public String get(){
		
		JxjyXflb cre = this.creditService.get(new Long(this.getRequest().getParameter("xflbid")));
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(cre));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
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
	
	/**
	 * 显示列表
	 * @throws  
	 */
	public String selectList() {
		String tjlb = (getRequest().getParameter("tjlb") == null) ? ""
				: getRequest().getParameter("tjlb");
		
		if (tjlb.equals("1")) { 	// 条件查询
			
			List<JxjyXflb> list = new ArrayList();
			
			JxjyXflb oneLb = new JxjyXflb();
			oneLb.setXflbid(0l);
			oneLb.setMc("I类学分");
			list.add(oneLb);
			
			JxjyXflb twoLb = new JxjyXflb();
			twoLb.setXflbid(1l);
			twoLb.setMc("II类学分");
			list.add(twoLb);
			
			
			JxjyXflb otherLb = new JxjyXflb();
			otherLb.setXflbid(2l);
			otherLb.setMc("其他学分");
			list.add(otherLb);
			
			
			Type type = new TypeToken<List<JxjyXflb>>() {
			}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(list.size()).append(
			",result:");
			
			Gson gson = new Gson();
			buff.append(gson.toJson(list, type));
			buff.append("}");
			
			jsonString = buff.toString();
			
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
	
	
	
	/**
	 * 显示列表
	 * @throws  
	 */
	public String listZc() {
			
		List<JxjyZc> list = this.zcService.getZcByParentId(0l);
		
		StringBuffer buff = new StringBuffer("[");
		for(JxjyZc zc : list) {
			buff.append("['");
			buff.append(zc.getZcId());
			buff.append("','");
			buff.append(zc.getZcm());
			buff.append("'],");
		}
		String str = buff.toString();
		str = str.substring(0, str.length()-1);
		str += "]";
		jsonString = str;
		
		return SUCCESS;
	}
	
	/**
	 * 显示列表
	 * @throws  
	 */
	public String listYy() {
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
			
			List<JxjyYyType> list = new ArrayList();
			
			JxjyYyType yy = new JxjyYyType();
			yy.setTypeId(1l);
			yy.setMc("一级及以下医院");
			list.add(yy);
			
			JxjyYyType yy2 = new JxjyYyType();
			yy2.setTypeId(2l);
			yy2.setMc("二级医院");
			list.add(yy2);
			
			JxjyYyType yy3 = new JxjyYyType();
			yy3.setTypeId(3l);
			yy3.setMc("三级医院");
			list.add(yy3);
			
			
			Type type = new TypeToken<List<JxjyYyType>>() {
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

	public JxjyDbbz getBiaozhun() {
		return biaozhun;
	}

	public void setBiaozhun(JxjyDbbz biaozhun) {
		this.biaozhun = biaozhun;
	}
	
}
