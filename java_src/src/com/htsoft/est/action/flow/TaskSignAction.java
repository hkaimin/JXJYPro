package com.htsoft.est.action.flow;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.util.BeanUtil;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.flow.ProUserAssign;
import com.htsoft.est.model.flow.TaskSign;
import com.htsoft.est.service.flow.ProUserAssignService;
import com.htsoft.est.service.flow.TaskSignService;


/**
 * @description 任务会签
 * @class TaskSignAction
 * @author YHZ
 * @company www.jee-soft.cn
 * @data 2011-1-5PM
 * 
 */
public class TaskSignAction extends BaseAction {
	@Resource
	private TaskSignService taskSignService;
	@Resource
	private ProUserAssignService proUserAssignService;

	private TaskSign taskSign;

	private Long signId;
	private Long assignId; // 流程人员设置Id

	public Long getSignId() {
		return signId;
	}

	public void setSignId(Long signId) {
		this.signId = signId;
	}

	public TaskSign getTaskSign() {
		return taskSign;
	}

	public void setTaskSign(TaskSign taskSign) {
		this.taskSign = taskSign;
	}

	public Long getAssignId() {
		return assignId;
	}

	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<TaskSign> list = taskSignService.getAll(filter);

		Type type = new TypeToken<List<TaskSign>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				taskSignService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		TaskSign taskSign = taskSignService.get(signId);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(taskSign));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/*
	 * 根据assignId查询TaskSign对象
	 */
	public String find() {
		TaskSign ts = taskSignService.findByAssignId(assignId);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		if(ts != null)
			sb.append(gson.toJson(ts));
		else
			sb.append("[]");
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (taskSign.getSignId() == null) {
			ProUserAssign pua = proUserAssignService.get(assignId);
			taskSign.setProUserAssign(pua);
			taskSignService.save(taskSign);
		} else {
			TaskSign orgTaskSign = taskSignService.get(taskSign.getSignId());
			try {
				BeanUtil.copyNotNullProperties(orgTaskSign, taskSign);
				taskSignService.save(orgTaskSign);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}
}
