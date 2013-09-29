package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.flow.FormDefDao;
import com.htsoft.est.model.flow.FieldRights;
import com.htsoft.est.model.flow.FormDef;
import com.htsoft.est.model.flow.FormField;
import com.htsoft.est.model.flow.FormTable;
import com.htsoft.est.service.flow.FormDefService;
import com.htsoft.est.service.flow.FormFieldService;
import com.htsoft.est.service.flow.FormTableService;

public class FormDefServiceImpl extends BaseServiceImpl<FormDef> implements FormDefService{
	private FormDefDao dao;
	@Resource
	private FormTableService formTableService;
	@Resource
	private FormFieldService formFieldService;
	public FormDefServiceImpl(FormDefDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.flow.FormDefService#getByDeployId(java.lang.String)
	 */
	public List<FormDef> getByDeployId(String deployId){
		return dao.getByDeployId(deployId);
	}
	
	/**
	 * 按流程定义ID及任务名称查找对应的表单定义
	 * @param deployId
	 * @param activityName
	 * @return
	 */
	public FormDef getByDeployIdActivityName(String deployId,String activityName){
		return dao.getByDeployIdActivityName(deployId, activityName);
	}

	@Override
	public FormDef saveFormDef(FormDef formDef, Map<FormTable, String> datas) {
		if(formDef.getFormDefId()==null){
			dao.save(formDef);
		}else{
			FormDef orgFormDef=dao.get(formDef.getFormDefId());
			Set<FormTable> tables=orgFormDef.getFormTables();
			Set<FormTable> keys=datas.keySet();
			tables.retainAll(keys);
			try{
				BeanUtil.copyNotNullProperties(orgFormDef, formDef);
				orgFormDef.setFormTables(tables);
				formDef=dao.save(orgFormDef);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		
		Iterator<Map.Entry<FormTable, String>> it=datas.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<FormTable, String> obj=(Map.Entry<FormTable, String>)it.next();
			FormTable formTable=obj.getKey();
			String data=obj.getValue();
			if(StringUtils.isNotEmpty(data)){
				Gson gson=new Gson();
				FormField[] formFields=gson.fromJson(data, FormField[].class);
				Set<FormField> fieldss=new HashSet<FormField>();
				for(FormField field:formFields){
					    if(field.getFieldId()==null){
					    	fieldss.add(field);
					    }else{
					    	FormField orgForm=formFieldService.get(field.getFieldId());
					    	Set<FieldRights> rights=orgForm.getFieldRightss();
					    	try {
								BeanUtil.copyNotNullProperties(orgForm, field);
								orgForm.setFieldRightss(rights);
								fieldss.add(orgForm);
//								field=formFieldService.save(orgForm);
					    	} catch (Exception e) {
								e.printStackTrace();
							}
					    }
					    
				}
				
				if(formTable.getTableId()==null){
					formTable.setFormDef(formDef);
					formTable.setFormFields(fieldss);
					formTable=formTableService.save(formTable);
				}else{
					FormTable orgFormTable=formTableService.get(formTable.getTableId());
					Set<FormField> fields=orgFormTable.getFormFields();
					try{
						BeanUtil.copyNotNullProperties(orgFormTable, formTable);
						//保留fields里有fieldss相同的字段
						fields.retainAll(fieldss);
						fieldss.removeAll(fields);
						fields.addAll(fieldss);
						orgFormTable.setFormFields(fields);
						formTable=formTableService.save(orgFormTable);
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
				}

			}
		}
		return formDef;
	}

}