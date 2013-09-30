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
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.model.system.UserOrg;
import com.htsoft.est.service.system.OrganizationService;
import com.htsoft.est.service.system.UserOrgService;

/**
 * 
 * @author guojy
 *
 */
public class ProjectAction extends BaseAction {
	
	@Resource
	private OrganizationService organizationService;
	@Resource
	private UserOrgService userOrgService;

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

			/*List<DpBdz> bdzList = dpBdzService.findBySsdwId(filter);
			Type type = new TypeToken<List<DpBdz>>() {
			}.getType();
			StringBuffer buff = new StringBuffer(
					"{success:true,'totalCounts':").append(
					filter.getPagingBean().getTotalItems()).append(",result:");

			Gson gson = new Gson();
			buff.append(gson.toJson(bdzList, type));
			buff.append("}");

			jsonString = buff.toString();

		} else if (method.equals("search")) { 	// 条件查询
			
			List<DpBdz> list = dpBdzService.search(dpBdz, filter);

			Type type = new TypeToken<List<DpBdz>>() {
			}.getType();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(filter.getPagingBean().getTotalItems()).append(
							",result:");

			Gson gson = new Gson();
			buff.append(gson.toJson(list, type));
			buff.append("}");
*/
//			jsonString = buff.toString();
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

		Long demId = new Long(this.getRequest().getParameter("demensionId"));
		Long orgId = new Long(this.getRequest().getParameter("treeNodeId"));

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
				strbuff.append("treeNodeType:'org'");
				List<Organization> list = organizationService
						.getCompanyByParent(orgX.getOrgId(), null);
				if (list == null || list.size() <= 0 || orgX.getDepth()==2) { // 子节点没有公司，设为叶子
					strbuff.append(",");
					strbuff.append("leaf:true");
				}
				strbuff.append("}");
			}
			strbuff.append("]");
		}
		setJsonString(strbuff.toString());
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
					if (org.getOrgType().equals(Organization.ORG_TYPE_COMPANY)) {
						orgX = org;
					}
					StringBuffer strbuff = new StringBuffer();
					strbuff.append("{");
					strbuff.append("resId:'").append(orgX.getOrgId()).append(
							"',");
					strbuff.append("text:'").append(orgX.getOrgName()).append(
							"',");
					strbuff.append("treeNodeType:'org'");
					if (orgX.getDepth() == 2l) {
						strbuff.append(",leaf:true");
					} else {
						List<Organization> list = organizationService
								.getCompanyByParent(orgX.getOrgId(),
										null);
						if (list == null || list.size() <= 0) { // 子节点没有公司，设为叶子
							strbuff.append(",");
							strbuff.append("leaf:true");
						}
					}
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
			// strbuff.append("expanded:true,");
			strbuff.append("treeNodeType:'org'");
			if(orgParent.getDepth() == 2l) {
				strbuff.append(",leaf:true");
			} else {
				if (strChild != null && !strChild.equals("")) {
					strbuff.append(",");
					strbuff.append("children:[").append(strChild).append("]");
				}
			}
			strbuff.append("}");

			return recursiveOrganizationTree_parent(orgParent, strbuff
					.toString());
		}
	}

}
