package com.htsoft.est.action.communicate;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.htsoft.core.Constants;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.communicate.SmsHistory;
import com.htsoft.est.model.communicate.SmsMobile;
import com.htsoft.est.service.communicate.SmsHistoryService;
import com.htsoft.est.service.communicate.SmsMobileService;
/**
 * 
 * @author 
 *
 */
public class SmsHistoryAction extends BaseAction{
	@Resource
	private SmsHistoryService smsHistoryService;
	@Resource
	private SmsMobileService smsMobileService;
	private SmsHistory smsHistory;
	
	private Long smsId;

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public SmsHistory getSmsHistory() {
		return smsHistory;
	}

	public void setSmsHistory(SmsHistory smsHistory) {
		this.smsHistory = smsHistory;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String status = getRequest().getParameter("status");
		List list = null;
		QueryFilter filter=new QueryFilter(getRequest());
		if(StringUtils.isNotEmpty(status) && status.equals(SmsMobile.STATUS_NOT_SENDED.toString())){
			 list = smsMobileService.getAll(filter);
		}else{
			 list= smsHistoryService.getAll(filter);
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_FULL).create();
		buff.append(gson.toJson(list));
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
				smsHistoryService.remove(new Long(id));
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
		SmsHistory smsHistory=smsHistoryService.get(smsId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(smsHistory));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		smsHistoryService.save(smsHistory);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
