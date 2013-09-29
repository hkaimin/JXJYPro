package com.htsoft.est.action.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.FileUtil;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.jbpm.jpdl.Node;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.flow.ExtFormItem;
import com.htsoft.est.model.flow.FormDef;
import com.htsoft.est.model.flow.FormDefMapping;
import com.htsoft.est.model.flow.FormTemplate;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.service.flow.FormDefMappingService;
import com.htsoft.est.service.flow.FormTemplateService;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.flow.ProDefinitionService;

/**
 * 
 * @author 
 *
 */
public class FormTemplateAction extends BaseAction{
	@Resource
	private FormTemplateService formTemplateService;
	@Resource
	private FormDefMappingService formDefMappingService;
	@Resource
	private ProDefinitionService proDefinitionService;
	
	@Resource
	private JbpmService jbpmService;
	
	private FormTemplate formTemplate;
	
	private Long templateId;
	
	private Long mappingId;

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}


	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}
	/**
	 * 显示某个流程映射的所有表单
	 * @return
	 */
	public String mappings(){
		
		FormDefMapping formDefMapping=formDefMappingService.get(mappingId);
		
		List<FormTemplate> formTemplateList=formTemplateService.getByMappingId(mappingId);
		
		List<Node> nodes = jbpmService.getFormNodesByDeployId(new Long(formDefMapping.getDeployId()));
		
		StringBuffer buff = new StringBuffer("{success:true,result:[");
		
		for(int i=0;i<nodes.size();i++){
			String nodeName=nodes.get(i).getName();
			buff.append("{nodeName:'").append(nodeName).append("'");
			for(FormTemplate template:formTemplateList){
				if(nodeName.equals(template.getNodeName())){
					buff.append(",mappingId:'").append(template.getMappingId())
					.append("',templateId:'").append(template.getTemplateId()).append("',formUrl:'")
					.append(template.getFormUrl()==null?"":template.getFormUrl()).append("',tempType:'").append(template.getTempType()==null? 1 :template.getTempType()).append("'");
					break;
				}
			}
			buff.append("},");
		}
		
		if(nodes.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		
		buff.append("]}");
		
		
		jsonString=buff.toString();
		
		
		return SUCCESS;
	}
	/**
	 * 保存流程的表单设置
	 * @return
	 */
	public String saveList(){
		String formTemps=getRequest().getParameter("formTemps");
		if(StringUtils.isNotEmpty("formTemps")){
			Gson gson=new Gson();
			FormTemplate[]formTemplates=(FormTemplate[])gson.fromJson(formTemps, FormTemplate[].class);
			if(formTemplates!=null){
				for(FormTemplate ft:formTemplates){
					if(ft.getTemplateId()!=null){
						FormTemplate temp=formTemplateService.get(ft.getTemplateId());
						temp.setTempType(ft.getTempType());
						temp.setFormUrl(ft.getFormUrl());
						formTemplateService.save(temp);
					}
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<FormTemplate> list= formTemplateService.getAll(filter);
		
		Type type=new TypeToken<List<FormTemplate>>(){}.getType();
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
				formTemplateService.remove(new Long(id));
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
		FormTemplate formTemplate=formTemplateService.get(templateId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(formTemplate));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
			String activityName=getRequest().getParameter("activityName");
			String extFormDef=getRequest().getParameter("extFormDef");
			//FormDef formDef=formDefService.get(formDefId);
			
			FormTemplate formTemplate=formTemplateService.get(templateId);
			formTemplate.setTempContent(extFormDef);
			
			//保存该表单的Ext项目配置,为下次加载修改恢复提供数据来源
			String extDef=getRequest().getParameter("extDef");
			formTemplate.setExtDef(extDef);
			
			formTemplateService.save(formTemplate);
			
			//取得流程对应的定义，把该任务的表单写至表单模板的js中，同时生成对应的映射文件.
			String formItemDef=getRequest().getParameter("formItemDef");
			
			logger.info("extFormDef:" + extFormDef);
			logger.info("formItemDef:" + formItemDef);
			
			ProDefinition proDefinition=proDefinitionService.getByDeployId(formTemplate.getFormDefMapping().getDeployId());
			String formPath=AppUtil.getAppAbsolutePath() + "/WEB-INF/FlowForm/" + proDefinition.getName()+"/"+formTemplate.getFormDefMapping().getVersionNo();
			
			File flowDirPath=new File(formPath);
			if(!flowDirPath.exists()){//若不存在目录，则创建
				flowDirPath.mkdirs();
			}
//			Gson gson=new Gson();			
//			ExtFormItem[]formItems=gson.fromJson("["+formItemDef+"]", ExtFormItem[].class);
//			StringBuffer xmlBuf=new StringBuffer(); 			
//			if(formItems!=null){
//				xmlBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//				xmlBuf.append("<fields>\n");
//				for(ExtFormItem item:formItems){
//					xmlBuf.append("\t<field name=\"" + item.getName()+ "\" label=\""+ item.getFieldLabel() 
//							+ "\" type=\"" + item.getType() + "\" length=\"" + item.getMaxLength() +"\" isShowed=\"" + item.getIsShowed() + "\"/>\n");
//				}
//				xmlBuf.append("</fields>\n");
//			}
//			
//			if(xmlBuf.length()>0){
//				String fieldFilePath=formPath + "/" + activityName + "-fields.xml";
//				FileUtil.writeFile(fieldFilePath,xmlBuf.toString());
//			}
			
			if(proDefinition!=null){
				//把表单的定义写至文件
				String extFilePath=formPath + "/" + activityName + ".vm";
				FileUtil.writeFile(extFilePath,extFormDef);
			}
		
		return SUCCESS;
		
	}
}
