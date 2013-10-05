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
import com.htsoft.est.model.jxjy.JxjySfbzsz;
import com.htsoft.est.service.ryxf.JxjySfbzmxService;
import com.htsoft.est.service.ryxf.JxjySfbzszService;
import com.htsoft.est.service.ryxf.MyJdbcService;
/**
 * 
 * @author 
 *
 */
public class JxjySfbzszAction extends BaseAction{
	@Resource
	private JxjySfbzszService jxjySfbzszService;
	@Resource
	private MyJdbcService myJdbcService;
	@Resource
	private JxjySfbzmxService jxjySfbzmxService;
	private JxjySfbzsz jxjySfbzsz;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JxjySfbzsz getJxjySfbzsz() {
		return jxjySfbzsz;
	}

	public void setJxjySfbzsz(JxjySfbzsz jxjySfbzsz) {
		this.jxjySfbzsz = jxjySfbzsz;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
//		QueryFilter filter=new QueryFilter(getRequest());
//		List<JxjySfbzsz> list= jxjySfbzszService.getAll(filter);
//		
//		Type type=new TypeToken<List<JxjySfbzsz>>(){}.getType();
//		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
//		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		List<JxjySfbzsz> list= myJdbcService.getSfbz();
		
		Type type=new TypeToken<List<JxjySfbzsz>>(){}.getType();
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
				jxjySfbzszService.remove(new Long(id));
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
		JxjySfbzsz jxjySfbzsz=jxjySfbzszService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(jxjySfbzsz));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(jxjySfbzsz.getId()==null){
			JxjySfbzsz sfbzsz = jxjySfbzszService.save(jxjySfbzsz);
//			JxjySfbzmx sfbzmx = new JxjySfbzmx();
//			sfbzmx.setSfbzszid(sfbzsz.getId());
//			jxjySfbzmxService.save(sfbzmx);
		}else{
			JxjySfbzsz orgJxjySfbzsz=jxjySfbzszService.get(jxjySfbzsz.getId());
			try{
				BeanUtil.copyNotNullProperties(orgJxjySfbzsz, jxjySfbzsz);
				jxjySfbzszService.save(orgJxjySfbzsz);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
