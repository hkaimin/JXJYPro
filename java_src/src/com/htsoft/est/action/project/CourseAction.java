package com.htsoft.est.action.project;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.jxjy.JxjyKtgl;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.model.system.UserOrg;
import com.htsoft.est.service.project.CourseService;
import com.htsoft.est.service.project.ProjectService;
import com.htsoft.est.service.system.OrganizationService;
import com.htsoft.est.service.system.UserOrgService;

/**
 * 
 * @author guojy
 *
 */
public class CourseAction extends BaseAction {
	
	@Resource
	private OrganizationService organizationService;
	@Resource
	private UserOrgService userOrgService;
	@Resource
	private ProjectService projectService;
	@Resource
	private CourseService courseService;
	
	private JxjyKtgl course;

	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		this.courseService.mutilDel(ids);
		return SUCCESS;
	}
	
	public String get(){
		
		JxjyKtgl kt = this.courseService.get(new Long(this.getRequest().getParameter("ktId")));
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(kt));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	public String save() {
		JxjyKtgl temp = this.courseService.saveCourse(this.course);
		if(temp != null) {
			jsonString = "{success:true}";
		} else {
			jsonString = "{success:false,message:'保存信息失败！'}";
		}
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

		if (method.equals("treeClick")) { 	// 树点击，根据区局ID加载

			List<JxjyKtgl> list = this.courseService.listCourse(filter);
			Type type = new TypeToken<List<JxjyKtgl>>() {
			}.getType();
			StringBuffer buff = new StringBuffer(
					"{success:true,'totalCounts':").append(
					filter.getPagingBean().getTotalItems()).append(",result:");

			Gson gson = new Gson();
			buff.append(gson.toJson(list, type));
			buff.append("}");

			jsonString = buff.toString();

		} else if (method.equals("search")) { 	// 条件查询
			
			List<JxjyKtgl> list = this.courseService.search(filter, this.course);

			Type type = new TypeToken<List<JxjyKtgl>>() {
			}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(filter.getPagingBean().getTotalItems()).append(
							",result:");

			Gson gson = new Gson();
			buff.append(gson.toJson(list, type));
			buff.append("}");

			jsonString = buff.toString();
		} else {
			jsonString = "{success:true,'totalCounts':0,result:[]}";
		}

		return SUCCESS;
	}
	
	/**
	 * 取得树
	 * 
	 * @return
	 */
	public String tree() {

		StringBuffer strbuff = new StringBuffer();

		String orgType = this.getRequest().getParameter("orgType");
		
		Long demId = new Long(this.getRequest().getParameter("demensionId"));
		Long orgId = new Long(this.getRequest().getParameter("treeNodeId"));

		if(orgType.equals(Organization.ORG_TYPE_DEPARTMENT.toString())) { //科室加载项目
			
			
			List<JxjyXmgl> xmList = this.projectService.getProjectByOrg(orgId);
			
			if(xmList.size() > 0) {
				strbuff.append("[");
				boolean bFirst = true;
				for (JxjyXmgl p : xmList) {
					if (!bFirst) {
						strbuff.append(",");
					} else {
						bFirst = false;
					}
					strbuff.append("{");
					strbuff.append("resId:'").append(p.getXmId()).append("',");
					strbuff.append("text:'").append(p.getMc()).append("',");
					strbuff.append("orgType:'").append("project").append("',");
					
					strbuff.append("treeNodeType:'org'");
					
					strbuff.append(",");
					strbuff.append("leaf:true");
					strbuff.append("}");
				}
				strbuff.append("]");
			}
			setJsonString(strbuff.toString());
			
		} else {
			List<Organization> orgList = organizationService.getCompanyByParent(
					orgId, demId);

			if (orgList != null && orgList.size() > 0) {
				strbuff.append("[");
				boolean bFirst = true;
				for (Organization orgX : orgList) {
					if (!bFirst) {
						strbuff.append(",");
					} else {
						bFirst = false;
					}
					strbuff.append("{");
					strbuff.append("resId:'").append(orgX.getOrgId()).append("',");
					strbuff.append("text:'").append(orgX.getOrgName()).append("',");
					strbuff.append("orgType:'").append(orgX.getOrgType()).append("',");
					strbuff.append("treeNodeType:'org'");
//					List<Organization> list = organizationService
//							.getCompanyByParent(orgX.getOrgId(), null);
//					if (list == null || list.size() <= 0) { // 子节点没有公司，设为叶子
//						strbuff.append(",");
//						strbuff.append("leaf:true");
//					}
					strbuff.append("}");
				}
				strbuff.append("]");
			}
			setJsonString(strbuff.toString());
		}
		
		return SUCCESS;
	}

	/**
	 * 取得树根
	 * 
	 * @return
	 */
	public String treeRoot() {
		String strReturn = "";
		AppUser user = ContextUtil.getCurrentUser();
		if (user != null) {
			List<UserOrg> userOrgList = userOrgService.getDepOrgsByUserId(user
					.getUserId());
			for (UserOrg userOrg : userOrgList) {
				if (userOrg.getIsPrimary().equals(UserOrg.PRIMARY)) { // 获得当前用户的主部门
					Organization org = userOrg.getOrganization();
					Organization orgParent = organizationService.get(org
							.getOrgSupId());
					Organization orgX = orgParent;
//					if (org.getOrgType().equals(Organization.ORG_TYPE_COMPANY)) {
						orgX = org;
//					}
					StringBuffer strbuff = new StringBuffer();
					strbuff.append("{");
					strbuff.append("resId:'").append(orgX.getOrgId()).append(
							"',");
					strbuff.append("text:'").append(orgX.getOrgName()).append(
							"',");
					
					//增加orgType
					strbuff.append("orgType:'").append(orgX.getOrgType()).append("',");
					
					strbuff.append("treeNodeType:'org'");
//					if (orgX.getDepth() == 2l) {
//						strbuff.append(",leaf:true");
//					} else {
						List<Organization> list = organizationService
								.getCompanyByParent(orgX.getOrgId(),
										null);
						if (list == null || list.size() <= 0) { // 子节点没有公司，设为叶子
							strbuff.append(",");
							strbuff.append("leaf:true");
						}
//					}
					strbuff.append("}");

					strReturn = recursiveOrganizationTree_parent(orgX, strbuff
							.toString());
					break;
				}
			}
		}
		if (strReturn == null) {
			strReturn = "{success:true,data:[{resId:'0',text:'获取资源出错！',leaf:true}]}";

		} else if (strReturn.equals("")) {
			strReturn = "{success:true,data:[{resId:'0',text:'请设置您的主部门！',leaf:true}]}";
		} else {
			strReturn = "{success:true,data:" + strReturn + "}";
		}
		setJsonString(strReturn);
		return SUCCESS;
	}

	// 递归组织父节点
	private String recursiveOrganizationTree_parent(Organization org,
			String strChild) {

		if (org == null) {
			return null;
		}

		String strs[] = org.getPath().split("\\.");

		if (strs.length < 2) { // 出错
			return null;

		} else if (strs.length == 2) { // 根节点
			String str = "[" + strChild + "]";
			return str;

		} else { // 非根节点

			Organization orgParent = organizationService.get(org.getOrgSupId());

			StringBuffer strbuff = new StringBuffer();
			strbuff.append("{");
			strbuff.append("resId:'").append(orgParent.getOrgId()).append("',");
			strbuff.append("text:'").append(orgParent.getOrgName())
					.append("',");
			
			//增加orgType
			strbuff.append("orgType:'").append(orgParent.getOrgType()).append("',");
			
			// strbuff.append("expanded:true,");
			strbuff.append("treeNodeType:'org'");
//			if(orgParent.getDepth() == 2l) {
//				strbuff.append(",leaf:true");
//			} else {
				if (strChild != null && !strChild.equals("")) {
					strbuff.append(",");
					strbuff.append("children:[").append(strChild).append("]");
				}
//			}
			strbuff.append("}");

			return recursiveOrganizationTree_parent(orgParent, strbuff
					.toString());
		}
	}

	public JxjyKtgl getCourse() {
		return course;
	}

	public void setCourse(JxjyKtgl course) {
		this.course = course;
	}
	
}
