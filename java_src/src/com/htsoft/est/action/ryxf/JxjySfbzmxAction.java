package com.htsoft.est.action.ryxf;
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


import com.htsoft.est.model.jxjy.JxjySfbzmx;
import com.htsoft.est.service.ryxf.JxjySfbzmxService;
import com.htsoft.est.service.ryxf.MyJdbcService;
/**
 * 
 * @author 
 *
 */
public class JxjySfbzmxAction extends BaseAction{
	@Resource
	private JxjySfbzmxService jxjySfbzmxService;
	@Resource
	private MyJdbcService myJdbcService;
	private JxjySfbzmx jxjySfbzmx;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JxjySfbzmx getJxjySfbzmx() {
		return jxjySfbzmx;
	}

	public void setJxjySfbzmx(JxjySfbzmx jxjySfbzmx) {
		this.jxjySfbzmx = jxjySfbzmx;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<JxjySfbzmx> list= jxjySfbzmxService.getAll(filter);
		
		Type type=new TypeToken<List<JxjySfbzmx>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	public String listSFMX(){
		
		List<JxjySfbzmx> list= myJdbcService.getSfbzmx(id);
		
		Type type=new TypeToken<List<JxjySfbzmx>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		
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
				jxjySfbzmxService.remove(new Long(id));
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
		JxjySfbzmx jxjySfbzmx=jxjySfbzmxService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(jxjySfbzmx));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(jxjySfbzmx.getId()==null){
			jxjySfbzmxService.save(jxjySfbzmx);
		}else{
			JxjySfbzmx orgJxjySfbzmx=jxjySfbzmxService.get(jxjySfbzmx.getId());
			try{
				BeanUtil.copyNotNullProperties(orgJxjySfbzmx, jxjySfbzmx);
				jxjySfbzmxService.save(orgJxjySfbzmx);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
