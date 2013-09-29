package com.htsoft.est.action.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.util.BeanUtil;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.jbpm.jpdl.Node;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.flow.FieldRights;
import com.htsoft.est.model.flow.FormDef;
import com.htsoft.est.model.flow.FormDefMapping;
import com.htsoft.est.model.flow.FormField;
import com.htsoft.est.model.flow.FormTable;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.model.flow.ProUserAssign;
import com.htsoft.est.service.flow.FieldRightsService;
import com.htsoft.est.service.flow.FormDefMappingService;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.flow.ProDefinitionService;


/**
 * 
 * @author 
 *
 */
public class FieldRightsAction extends BaseAction{
	@Resource
	private FieldRightsService fieldRightsService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private FormDefMappingService formDefMappingService;
	@Resource
	private JbpmService jbpmService;
	private FieldRights fieldRights;
	
	private Long rightId;

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public FieldRights getFieldRights() {
		return fieldRights;
	}

	public void setFieldRights(FieldRights fieldRights) {
		this.fieldRights = fieldRights;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<FieldRights> list= fieldRightsService.getAll(filter);
		
		Type type=new TypeToken<List<FieldRights>>(){}.getType();
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
				fieldRightsService.remove(new Long(id));
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
		FieldRights fieldRights=fieldRightsService.get(rightId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(fieldRights));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(fieldRights.getRightId()==null){
			fieldRightsService.save(fieldRights);
		}else{
			FieldRights orgFieldRights=fieldRightsService.get(fieldRights.getRightId());
			try{
				BeanUtil.copyNotNullProperties(orgFieldRights, fieldRights);
				fieldRightsService.save(orgFieldRights);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 取流程节点
	 */
	public String nodes(){
	   String defId=getRequest().getParameter("defId");
	   ProDefinition proDefinition=proDefinitionService.get(new Long(defId));
	   FormDefMapping fdm=formDefMappingService.getByDeployId(proDefinition.getDeployId());
	   StringBuffer buff = new StringBuffer();
	   if(fdm!=null){
		   List<Node> nodes=jbpmService.getFormNodesByDeployId(new Long(proDefinition.getDeployId()));
		   FormDef fd=fdm.getFormDef();
		   List<FormField> fields=new ArrayList<FormField>();
		   if(fd==null){
			   setJsonString("{success:false}");
			   return SUCCESS;
		   }
		   Set<FormTable> tables=fd.getFormTables();
		   Iterator<FormTable> it=tables.iterator();
		   while(it.hasNext()){
			   Set<FormField> fs=it.next().getFormFields();
			   Iterator<FormField> it2=fs.iterator();
			   while(it2.hasNext()){
				   FormField ff=it2.next();
				   if(FormField.IS_SHOW.compareTo(ff.getIsDesignShow())==0){
				      fields.add(ff); 	
				   }
			   }
		   }
		   buff.append("{success:true,result:[");
		   Gson gson=new Gson();
			for (int i = 0; i < nodes.size(); i++) {
				String nodeName=nodes.get(i).getName();
					for(int k=0;k<fields.size();k++){
						FormField field=fields.get(k);
						buff.append("{taskName:'").append(nodeName).append("',mappingId:'" + fdm.getMappingId()).append("'");
						List<FieldRights> list=fieldRightsService.getByMappingFieldTaskName(fdm.getMappingId(), field.getFieldId(), nodeName);
						FieldRights right=new FieldRights();
						if(list.size()>0){
							right=list.get(0);
						}
						buff.append(",rightId:'").append(right.getRightId()==null?"":right.getRightId()).append("',readWrite:'").append(right.getRightId()==null?2:right.getReadWrite()).append("'");
						buff.append(",refieldId:'").append(field.getFieldId())
						.append("',fieldName:'").append(gson.toJson(field.getFieldName()).replace("\"", ""))
						.append("',fieldLabel:'").append(gson.toJson(field.getFieldLabel()).replace("\"", ""))
						.append("'");
					    buff.append("},");
				}
			}
	
			if (!nodes.isEmpty()) {
				buff.deleteCharAt(buff.length() - 1);
			}
	
			buff.append("]}");
	   }else{
		   buff.append("{success:false}");
	   }
		jsonString=buff.toString();
	   return SUCCESS;	
	}
	/**
	 * 批量保存
	 */
	public String multSave(){
		String data=getRequest().getParameter("data");
		Gson gson=new Gson();
		FieldRights[] fieldRights=gson.fromJson(data,FieldRights[].class);
		for(FieldRights right:fieldRights){
			if(right.getRightId()==-1){
               right.setRightId(null);				
			}
			right.setFieldId(right.getRefieldId());
			fieldRightsService.save(right);
		}
		jsonString="{success:true}";
        return SUCCESS;		
	}
	
	public String check(){
		String defId=getRequest().getParameter("defId");
	    ProDefinition proDefinition=proDefinitionService.get(new Long(defId));
	    FormDefMapping fdm=formDefMappingService.getByDeployId(proDefinition.getDeployId());
		if(fdm!=null){
			jsonString="{success:true}";
		}else{
			jsonString="{success:false,msg:'未绑定表单，请先绑定表单！'}";
		}
	    return SUCCESS;
	}
	
}
