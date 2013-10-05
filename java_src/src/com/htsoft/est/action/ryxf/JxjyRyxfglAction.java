package com.htsoft.est.action.ryxf;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.ContextUtil;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;

import com.htsoft.est.model.jxjy.JxjyRyxfgl;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.ryxf.JxjyRyxfglService;
import com.htsoft.est.service.ryxf.MyJdbcService;
/**
 * 
 * @author 
 *
 */
public class JxjyRyxfglAction extends BaseAction{
	@Resource
	private JxjyRyxfglService jxjyRyxfglService;
	@Resource
	private MyJdbcService myJdbcService;
	private JxjyRyxfgl jxjyRyxfgl; 
	
	private Long id;
	private String ids;
	private String shzt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public JxjyRyxfgl getJxjyRyxfgl() {
		return jxjyRyxfgl;
	}

	public void setJxjyRyxfgl(JxjyRyxfgl jxjyRyxfgl) {
		this.jxjyRyxfgl = jxjyRyxfgl;
	}

	public String getShzt() {
		return shzt;
	}

	public void setShzt(String shzt) {
		this.shzt = shzt;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<JxjyRyxfgl> list= jxjyRyxfglService.getAll(filter);
		
		Type type=new TypeToken<List<JxjyRyxfgl>>(){}.getType();
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
				jxjyRyxfglService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	public String commitXF(){
		
		JxjyRyxfgl jxjyRyxfgl=jxjyRyxfglService.get(id);
		jxjyRyxfgl.setIs_commit(1L);
		jxjyRyxfglService.save(jxjyRyxfgl);
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	public String wstSHXF(){
		
		String[] ryxfIdvo = ids.split(",");
		
		for(String s:ryxfIdvo){
			JxjyRyxfgl jxjyRyxfgl=jxjyRyxfglService.get(new Long(s));
			jxjyRyxfgl.setShzt(shzt);
			jxjyRyxfglService.save(jxjyRyxfgl);
		}
		
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	public String rsjSHXF(){
		
		String[] ryxfIdvo = ids.split(",");
		
		for(String s:ryxfIdvo){
			JxjyRyxfgl jxjyRyxfgl=jxjyRyxfglService.get(new Long(s));
			jxjyRyxfgl.setRsjsh(shzt);
			jxjyRyxfglService.save(jxjyRyxfgl);
		}
		
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		JxjyRyxfgl jxjyRyxfgl=jxjyRyxfglService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(jxjyRyxfgl));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(jxjyRyxfgl.getId()==null){
			AppUser currentUser = ContextUtil.getCurrentUser();
			jxjyRyxfgl.setRybh(currentUser.getUserId());
			jxjyRyxfgl.setXm(currentUser.getFullname());
			jxjyRyxfglService.save(jxjyRyxfgl);
		}else{
			JxjyRyxfgl orgJxjyRyxfgl=jxjyRyxfglService.get(jxjyRyxfgl.getId());
			try{
				BeanUtil.copyNotNullProperties(orgJxjyRyxfgl, jxjyRyxfgl);
				jxjyRyxfglService.save(orgJxjyRyxfgl);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String getXflb(){
		
		List<Map<String,Object>> list=myJdbcService.getXflb();
		StringBuffer sb = new StringBuffer("[");
		for (Map map : list) {
			
				sb.append("['" + map.get("XFLBID") + "','" + map.get("MC")
						+ "'],");
			
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
}
