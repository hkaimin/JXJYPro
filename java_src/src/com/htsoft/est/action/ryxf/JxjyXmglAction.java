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

import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.service.ryxf.JxjyXmglService;
/**
 * 
 * @author 
 *
 */
public class JxjyXmglAction extends BaseAction{
	@Resource
	private JxjyXmglService jxjyXmglService;
	private JxjyXmgl jxjyXmgl;
	
	private Long xmId;

	public Long getXmId() {
		return xmId;
	}

	public void setXmId(Long xmId) {
		this.xmId = xmId;
	}

	public JxjyXmgl getJxjyXmgl() {
		return jxjyXmgl;
	}

	public void setJxjyXmgl(JxjyXmgl jxjyXmgl) {
		this.jxjyXmgl = jxjyXmgl;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<JxjyXmgl> list= jxjyXmglService.getAll(filter);
		
		Type type=new TypeToken<List<JxjyXmgl>>(){}.getType();
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
				jxjyXmglService.remove(new Long(id));
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
		JxjyXmgl jxjyXmgl=jxjyXmglService.get(xmId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(jxjyXmgl));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(jxjyXmgl.getXmId()==null){
			jxjyXmglService.save(jxjyXmgl);
		}else{
			JxjyXmgl orgJxjyXmgl=jxjyXmglService.get(jxjyXmgl.getXmId());
			try{
				BeanUtil.copyNotNullProperties(orgJxjyXmgl, jxjyXmgl);
				jxjyXmglService.save(orgJxjyXmgl);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
