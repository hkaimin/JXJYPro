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
import com.htsoft.est.model.system.PositionSub;
import com.htsoft.est.service.system.PositionSubService;


/**
 * 
 * @author 
 *
 */
public class PositionSubAction extends BaseAction{
	@Resource
	private PositionSubService positionSubService;
	private PositionSub positionSub;
	
	private Long mainpositionid;

	public Long getMainpositionid() {
		return mainpositionid;
	}

	public void setMainpositionid(Long mainpositionid) {
		this.mainpositionid = mainpositionid;
	}

	public PositionSub getPositionSub() {
		return positionSub;
	}

	public void setPositionSub(PositionSub positionSub) {
		this.positionSub = positionSub;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PositionSub> list= positionSubService.getAll(filter);
		
		Type type=new TypeToken<List<PositionSub>>(){}.getType();
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
				positionSubService.remove(new Long(id));
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
		PositionSub positionSub=positionSubService.get(mainpositionid);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(positionSub));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(positionSub.getMainPositionId()==null){
			positionSubService.save(positionSub);
		}else{
			PositionSub orgPositionSub=positionSubService.get(positionSub.getMainPositionId());
			try{
				BeanUtil.copyNotNullProperties(orgPositionSub, positionSub);
				positionSubService.save(orgPositionSub);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
