package com.htsoft.est.action.system;
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
import com.htsoft.est.model.system.Demension;
import com.htsoft.est.service.system.DemensionService;


/**
 * 
 * @author 
 *
 */
public class DemensionAction extends BaseAction{
	@Resource
	private DemensionService demensionService;
	private Demension demension;
	
	private Long demId;

	public Long getDemId() {
		return demId;
	}

	public void setDemId(Long demId) {
		this.demId = demId;
	}

	public Demension getDemension() {
		return demension;
	}

	public void setDemension(Demension demension) {
		this.demension = demension;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<Demension> list= demensionService.getAll(filter);
		
		Type type=new TypeToken<List<Demension>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
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
				demensionService.remove(new Long(id));
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
		Demension demension=demensionService.get(demId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(demension));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(demension.getDemId()==null){
			demensionService.save(demension);
		}else{
			Demension orgDemension=demensionService.get(demension.getDemId());
			try{
				BeanUtil.copyNotNullProperties(orgDemension, demension);
				demensionService.save(orgDemension);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 下拉维度列表
	 * @return
	 */
	public String combo(){
		List<Demension> list=demensionService.getAll();
		StringBuffer sb=new StringBuffer("[['0','全部..']");
		for(Demension dem:list){
			sb.append(",['").append(dem.getDemId()).append("','").append(dem.getDemName()).append("']");
		}
		sb.append("]");
		jsonString=sb.toString();
		return SUCCESS;
	}
}
