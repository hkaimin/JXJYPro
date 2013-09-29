package com.htsoft.est.action.system;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.model.system.UserOrg;
import com.htsoft.est.service.system.AppUserService;
import com.htsoft.est.service.system.OrganizationService;
import com.htsoft.est.service.system.UserOrgService;
/**
 * 
 * @author 
 *
 */
public class OrganizationAction extends BaseAction{
	@Resource
	private OrganizationService organizationService;
	@Resource
	private UserOrgService userOrgService;
	@Resource
	private AppUserService appUserService;

	private Organization organization;
	
	private Long orgId;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		
		String orgSupId=getRequest().getParameter("orgSupId");
		if(StringUtils.isNotEmpty(orgSupId)){
			Organization supOrg=organizationService.get(new Long(orgSupId));
			filter.addFilter("Q_path_S_LFK", supOrg==null?"0.":supOrg.getPath());
		}
		filter.addSorted("path", "asc");
		List<Organization> list= organizationService.getAll(filter);
		
		Type type=new TypeToken<List<Organization>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		
//		
//		JSONSerializer json=JsonUtil.getJSONSerializer("createtime","updatetime");
		
		buff.append(gson.toJson(list,type));
		
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				//删除某个组织及其下属组织
				organizationService.delCascade(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		Organization organization=organizationService.get(orgId);
//		Set au = organization.getAppUsers();
//		JSONSerializer json=JsonUtil.getJSONSerializer("createtime","updatetime");
//		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		List<AppUser> list = appUserService.getChargeOrgUsers(organization.getUserOrgs());
		String chargeIds = "";
		String chargeNames = "";
		if(list!=null&&list.size()>0){
			for(int index=0;index<list.size();index++){
				AppUser au = list.get(index);
				if(index==0){
					chargeIds += au.getUserId();
					chargeNames += au.getFullname();
				}else{
					chargeIds += ","+au.getUserId();
					chargeNames += ","+au.getFullname();
				}
			}
		}
		
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:{");
//		sb.append(json.deepSerialize(organization));
//		sb.append(gson.toJson(organization));
		sb.append("\"orgId\":\""+organization.getOrgId()+"\",");
		sb.append("\"demId\":\""+organization.getDemId()+"\",");
		sb.append("\"orgName\":\""+organization.getOrgName()+"\",");
		sb.append("\"orgDesc\":\""+organization.getOrgDesc()+"\",");
		sb.append("\"orgSupId\":\""+organization.getOrgSupId()+"\",");
		sb.append("\"orgType\":\""+organization.getOrgType()+"\",");
		sb.append("\"chargeIds\":\""+chargeIds+"\",");
		sb.append("\"chargeNames\":\""+chargeNames+"\",");
		sb.append("}}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String chargeIds = getRequest().getParameter("chargeIds");
		if(organization.getOrgId()==null){
			organization.setCreatetime(new Date());
			organization.setUpdatetime(new Date());
			organization.setCreatorId(ContextUtil.getCurrentUserId());
			organization.setUpdateId(ContextUtil.getCurrentUserId());
			
			organizationService.save(organization);
			Long parentId=organization.getOrgSupId();
			if(parentId==0){
				organization.setPath("0."+organization.getOrgId()+".");
				organization.setDepth(1l);
			}else{
				Organization supOrg=organizationService.get(parentId);
				organization.setPath(supOrg.getPath()+organization.getOrgId()+".");
				organization.setDepth(supOrg.getDepth()==null?1:supOrg.getDepth()+1);
			}
			organizationService.save(organization);

			if(chargeIds!=null&&chargeIds.length()>0){
				String[] pids = chargeIds.split(",");
				for(int index=0;index<pids.length;index++){
					UserOrg userOrg = new UserOrg();
					userOrg.setUserid(new Long(pids[index]));
					userOrg.setOrgId(organization.getOrgId());
					userOrg.setIsPrimary(new Short("1"));
					userOrg.setIsCharge(new Short("1"));
					userOrgService.save(userOrg);
				}
			}
		}else{
			Organization orgOrganization=organizationService.get(organization.getOrgId());
			try{
				
				Long parentId=orgOrganization.getOrgSupId();
				if(parentId==0){
					orgOrganization.setPath("0."+organization.getOrgId()+".");
					orgOrganization.setDepth(1l);
				}else{
					Organization supOrg=organizationService.get(parentId);
					orgOrganization.setPath(supOrg.getPath()+orgOrganization.getOrgId()+".");
					orgOrganization.setDepth(supOrg.getDepth()+1);
				}
				orgOrganization.setUpdatetime(new Date());
				orgOrganization.setUpdateId(ContextUtil.getCurrentUserId());
				BeanUtil.copyNotNullProperties(orgOrganization, organization);
				organizationService.save(orgOrganization);

				userOrgService.delUserOrg(orgOrganization.getOrgId());
				if(chargeIds!=null&&chargeIds.length()>0){
					String[] pids = chargeIds.split(",");
					for(int index=0;index<pids.length;index++){
						UserOrg userOrg = new UserOrg();
						userOrg.setUserid(new Long(pids[index]));
						userOrg.setOrgId(organization.getOrgId());
						userOrg.setIsPrimary(new Short("1"));
						userOrg.setIsCharge(new Short("1"));
						userOrgService.save(userOrg);
					}
				}
				
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 取得树
	 * @return
	 */
	public String tree(){
		String flag = getRequest().getParameter("flag"); // 判断是orgSetting或userForm
		String pDemId=getRequest().getParameter("demId");
		Long demId=0l;
		if(StringUtils.isNotEmpty(pDemId)){
			demId=new Long(pDemId);
		}
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'全部',expanded:true,children:[");
		List<Organization> orgList=organizationService.getByParent(new Long(0l),demId);
		for(Organization org:orgList){
//			if("userForm".equals(flag)){
//				if(Organization.ORG_TYPE_COMPANY==org.getOrgType()||Organization.ORG_TYPE_DEPARTMENT==org.getOrgType()){
//					buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName()).append("',")
//					.append("orgType:'").append(org.getOrgType()).append("',");
//					 buff.append(getChildList(org.getOrgId(),demId));
//				}
//			}else{
				buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName()).append("',")
				.append("orgType:'").append(org.getOrgType()).append("',");
				 buff.append(getChildList(org.getOrgId(),demId));
//			}
		}
		if (!orgList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		
		return SUCCESS;
	}
	
	public String getChildList(Long parentId,Long demId){
		StringBuffer buff=new StringBuffer();
		List<Organization> orgList=organizationService.getByParent(parentId,demId);
		if(orgList.size()==0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString(); 
		}else {
			buff.append("expanded:true,children:[");
			for(Organization org:orgList){
				buff.append("{id:'"+org.getOrgId()).append("',text:'"+org.getOrgName()).append("',")
				.append("orgType:'").append(org.getOrgType()).append("',");
			    buff.append(getChildList(org.getOrgId(),demId));
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}
	/**
	 * 明细
	 * @return
	 */
	public String detail(){
		String orgId=getRequest().getParameter("orgId");
		if(StringUtils.isNotEmpty(orgId) && !"0".equals(orgId)){
			Organization organization=organizationService.get(new Long(orgId));
			
			if(organization.getOrgSupId()!=null && organization.getOrgSupId()!=-1){
				Organization supOrg=organizationService.get(organization.getOrgSupId());
				getRequest().setAttribute("supOrg",supOrg);
			}
			
			getRequest().setAttribute("org", organization);
		}
		return "detail";
		
	}
	
	
}
