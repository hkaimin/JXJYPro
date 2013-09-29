package com.htsoft.est.action.system;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.htsoft.core.util.BeanUtil;

import com.htsoft.core.web.action.BaseAction;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.RelativeJob;
import com.htsoft.est.model.system.RelativeUser;
import com.htsoft.est.service.system.AppUserService;
import com.htsoft.est.service.system.RelativeJobService;
import com.htsoft.est.service.system.RelativeUserService;


import flexjson.JSONSerializer;

/**
 * @description 相对岗位人员管理
 * @author YHZ
 * @company www.jee-soft.cn
 * @data 2010-12-13AM
 * 
 */
public class RelativeUserAction extends BaseAction {
	@Resource
	private RelativeUserService relativeUserService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private RelativeJobService relativeJobService;
	
	private RelativeUser relativeUser;

	private Long relativeUserId;

	private Long userId;
	private Long reJobId;

	public Long getRelativeUserId() {
		return relativeUserId;
	}

	public void setRelativeUserId(Long relativeUserId) {
		this.relativeUserId = relativeUserId;
	}

	public RelativeUser getRelativeUser() {
		return relativeUser;
	}

	public void setRelativeUser(RelativeUser relativeUser) {
		this.relativeUser = relativeUser;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getReJobId() {
		return reJobId;
	}

	public void setReJobId(Long reJobId) {
		this.reJobId = reJobId;
	}

	/**
	 * 显示列表
	 */
	public String list() {
		PagingBean pb = getInitPagingBean();
		List<RelativeUser> list = relativeUserService.list(userId, reJobId, pb);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pb.getTotalItems()).append(",result:");
		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 批量删除
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				relativeUserService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 */
	public String get() {
		RelativeUser relativeUser = relativeUserService.get(relativeUserId);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(relativeUser));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (relativeUser.getRelativeUserId() == null) {
			relativeUserService.save(relativeUser);
		} else {
			RelativeUser orgRelativeUser = relativeUserService.get(relativeUser
					.getRelativeUserId());
			try {
				BeanUtil.copyNotNullProperties(orgRelativeUser, relativeUser);
				relativeUserService.save(orgRelativeUser);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * 添加多条记录
	 */
	public String mutliAdd() {
		boolean yesOrNoCurrentUser = false;
		ArrayList<Long> ex = new ArrayList<Long>(); // 存在用户,不需要添加
		String noEx = ""; // 不存在的用户
		String jobUserIds = getRequest().getParameter("jobUserIds");// 岗位员工列表
		String userId = getRequest().getParameter("userId"); // 所属员工
		AppUser appUser = appUserService.get(new Long(userId)); // 所属员工的详细信息
		// 筛选可以添加的用户信息
		for (String uid : jobUserIds.split(",")) {
			if (uid.equals(userId)) // 判断是否为自己
				yesOrNoCurrentUser = true;
			else {
				AppUser apu = relativeUserService.judge(new Long(userId),
						new Long(uid));
				if (apu == null)// 该用户不存在,需要添加的用户
					ex.add(new Long(uid));
				else
					noEx += apu.getFullname() + ","; // 不需要添加的,保存fullname
			}
		}
		if (!noEx.equals(""))
			noEx = noEx.substring(0, noEx.length() - 1);
		// 添加操作
		String msg = "";
		if (ex != null && ex.size() > 0) {
			String exMsg = "";
			for (Long uid : ex) {
				AppUser jobUser = appUserService.get(new Long(uid));
				// 设置RelativeUser对象属性
				RelativeUser ruSave = new RelativeUser();
				RelativeJob rJob = relativeJobService.get(reJobId);
				ruSave.setRelativeJob(rJob);
				ruSave.setJobUser(jobUser);
				ruSave.setAppUser(appUser);
				ruSave.setIsSuper(relativeUser.getIsSuper());
				//end 
				RelativeUser ru = relativeUserService.save(ruSave);
				exMsg += ru.getJobUser().getFullname() + ",";
			}
			exMsg = exMsg.substring(0, exMsg.length() - 1);
			msg = "{success:true,msg:'成功添加[" + exMsg + "]用户";
			if (noEx != null && !noEx.equals(""))
				msg += "，其中[" + noEx + "]已经添加";
			if (yesOrNoCurrentUser)
				msg += "，用户本身不能添加";
			msg = msg + "！'}";
		} else
			msg = "{success:true,msg:'对不起，没有适合添加的用户，请重新选择！'}"; // 没有可以适合添加的用户
		setJsonString(msg);
		return SUCCESS;
	}

}
