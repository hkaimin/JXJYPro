package com.htsoft.est.action.document;
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
import com.htsoft.core.util.JsonUtil;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.document.PaintTemplate;
import com.htsoft.est.service.document.PaintTemplateService;



import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PaintTemplateAction extends BaseAction{
	@Resource
	private PaintTemplateService paintTemplateService;
	private PaintTemplate paintTemplate;
	
	private Long ptemplateId;

	public Long getPtemplateId() {
		return ptemplateId;
	}

	public void setPtemplateId(Long ptemplateId) {
		this.ptemplateId = ptemplateId;
	}

	public PaintTemplate getPaintTemplate() {
		return paintTemplate;
	}

	public void setPaintTemplate(PaintTemplate paintTemplate) {
		this.paintTemplate = paintTemplate;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PaintTemplate> list= paintTemplateService.getAll(filter);
		
//		Type type=new TypeToken<List<PaintTemplate>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		JSONSerializer json=JsonUtil.getJSONSerializer();
		json.exclude("class");
		buff.append(json.serialize(list));
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
				paintTemplateService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	

	public String getByKey(){
	    
	    	String templateKey=getRequest().getParameter("templateKey");
		
		List<PaintTemplate> paintTemplates=paintTemplateService.getByKey(templateKey);
		
		if(paintTemplates.size()>0){
		    	StringBuffer sb = new StringBuffer("{success:true,data:");
			JSONSerializer json=JsonUtil.getJSONSerializer();
			json.exclude("class");
			sb.append(json.serialize(paintTemplates.get(0)));
			sb.append("}");
			setJsonString(sb.toString());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		PaintTemplate paintTemplate=paintTemplateService.get(ptemplateId);
		
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer json=JsonUtil.getJSONSerializer();
		json.exclude("class");
		sb.append(json.serialize(paintTemplate));

		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(paintTemplate.getPtemplateId()==null){
		    boolean keyExist=paintTemplateService.isKeyExist(paintTemplate.getTemplateKey());
		    if(keyExist){
			setJsonString("{success:false,msg:'该Key已经存在！'}");
			return SUCCESS;
		    }
			paintTemplateService.save(paintTemplate);
		}else{
		    	List<PaintTemplate> paintTemplates=paintTemplateService.getByKeyExceptId(paintTemplate.getTemplateKey(),paintTemplate.getPtemplateId());
		    	if(paintTemplates.size()>0){
        		    	setJsonString("{success:false,msg:'该Key已经存在！'}");
        		    	return SUCCESS;
		    	}
			PaintTemplate orgPaintTemplate=paintTemplateService.get(paintTemplate.getPtemplateId());
			try{
				BeanUtil.copyNotNullProperties(orgPaintTemplate, paintTemplate);
				paintTemplateService.save(orgPaintTemplate);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
