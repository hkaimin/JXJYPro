package com.htsoft.est.action.flow;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.jbpm.jpdl.Node;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.util.JsonUtil;
import com.htsoft.core.util.XmlUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.flow.FormDefMapping;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.model.flow.ProcessRun;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.GlobalType;
import com.htsoft.est.service.bpm.ILog.factory.BpmFactory;
import com.htsoft.est.service.flow.FormDefMappingService;
import com.htsoft.est.service.flow.FormTemplateService;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.flow.ProDefinitionService;
import com.htsoft.est.service.flow.ProcessRunService;
import com.htsoft.est.service.system.GlobalTypeService;

import flexjson.JSONSerializer;

/**
 * @description 流程定义
 * @class ProDefinitionAction
 * @author csx
 * @updater YHZ
 * @company www.jee-soft.cn
 * @data 2010-12-28PM
 */
public class ProDefinitionAction extends BaseAction {
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private FormDefMappingService formDefMappingService;
	@Resource
	private FormTemplateService formTemplateService;
	
	@Resource
	private ProcessRunService processRunService; //运行中的流程管理

	private ProDefinition proDefinition;

	private Long defId;

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public ProDefinition getProDefinition() {
		return proDefinition;
	}

	public void setProDefinition(ProDefinition proDefinition) {
		this.proDefinition = proDefinition;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());

		String typeId = getRequest().getParameter("typeId");

		List<ProDefinition> list = null;
		if (StringUtils.isNotEmpty(typeId) && !"0".equals(typeId)) {
			// 有类型ID,说明该用户有该类型的权限,查出该类型下的所有流程定义
			GlobalType proType = globalTypeService.get(new Long(typeId));
			filter.addFilter("Q_proType.path_S_LK", proType.getPath());
			list = proDefinitionService.getAll(filter);
		} else {
			AppUser curUser = ContextUtil.getCurrentUser();
			if (curUser.isSupperManage()) {
				// 假如该用户为超级管理员角色,则该用户可看到所有流程定义
				list = proDefinitionService.getAll(filter);
			} else {
				// 非管理员角色的用户需要通过权限过虑流程
				list = proDefinitionService.getByRights(curUser, proDefinition,
						filter);
			}
		}

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		JSONSerializer serializer = JsonUtil.getJSONSerializer("createtime")
				.exclude("defXml");

		buff.append(serializer.serialize(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				// 删除流程的定义，就会删除流程的实例，表单的数据及Jbpm的流程相关的内容
				// proDefinitionService.remove(new Long(id));
				jbpmService.doUnDeployProDefinition(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {

		if (defId != null) {
			proDefinition = proDefinitionService.get(defId);
		} else {
			proDefinition = new ProDefinition();
			String proTypeId = getRequest().getParameter("proTypeId");
			if (StringUtils.isNotEmpty(proTypeId)) {
				GlobalType proType = globalTypeService.get(new Long(proTypeId));
				proDefinition.setProType(proType);
			}
		}

		// 用JSONSerializer解决延迟加载的问题
		JSONSerializer serializer = JsonUtil.getJSONSerializer("createtime");
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(serializer.serialize(proDefinition));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}
	
	public String flexGet(){
		if (defId != null) {
			proDefinition = proDefinitionService.get(defId);
		} else {
			proDefinition = new ProDefinition();
			String proTypeId = getRequest().getParameter("proTypeId");
			if (StringUtils.isNotEmpty(proTypeId)) {
				GlobalType proType = globalTypeService.get(new Long(proTypeId));
				proDefinition.setProType(proType);
			}
		}
		StringBuffer msg = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Result>");
		// defId
		msg.append("<defId>" + proDefinition.getDefId() + "</defId>"); 
		// drawDefXml
		msg.append("<drawDefXml>" + proDefinition.getDrawDefXml() + "</drawDefXml>");
	
		if(proDefinition.getProType() != null){
			// proTypeName 
			msg.append("<proTypeName>" + proDefinition.getProType().getTypeName() + "</proTypeName>");
			// proTypeId
			msg.append("<proTypeId>" + proDefinition.getProType().getProTypeId() + "</proTypeId>"); 
		}
		
		// name
		msg.append("<name>" + proDefinition.getName() + "</name>"); 
		
		// processName
		msg.append("<processName>" + proDefinition.getProcessName() + "</processName>");
		
		// status
		msg.append("<status>" + proDefinition.getStatus() + "</status>"); 
		
		// description
		msg.append("<description>" + proDefinition.getDescription() + "</description>");
		
		// newVersion
		msg.append("<newVersion>" + proDefinition.getNewVersion() + "</newVersion>");
		msg.append("</Result>");
		setJsonString(msg.toString());
		return SUCCESS;
	}
	
	/**
	 * flex提交
	 */
	public String flexDefSave(){
//		if(proDefinition.getDefId() != null && !proDefinition.getDefId().equals("")){
//			
//			save();
//		} else {
			logger.info("...eneter defSave......");
			boolean deploy = Boolean.parseBoolean(getRequest().getParameter("deploy"));
			//转化xml文件格式
			if(proDefinition.getDrawDefXml()!= null && !proDefinition.getDrawDefXml().equals("")){
				String text = convertFlexXmlToJbpmXml(proDefinition.getDrawDefXml(), proDefinition.getProcessName());
				proDefinition.setDefXml(text); 
				logger.debug("解析的JBPM对应的xml文件：\n" + text);
			}
	
			// 检查流程名称的唯一性
			if (!proDefinitionService.checkNameByVo(proDefinition)) {   
				// 假如不唯一
				setJsonString("流程名称(系统中使用)已存在,请重新填写.");
				return SUCCESS;
			}
			if (!proDefinitionService.checkProcessNameByVo(proDefinition)) {
				// 假如不唯一
				setJsonString("流程名称(定义中使用)已存在,请重新填写.");
				return SUCCESS;
			}
			if (deploy) {
				save();
			} else {
				Long proTypeId = proDefinition.getProTypeId();
				if (proTypeId != null) {
					GlobalType proType = globalTypeService.get(proTypeId);
					proDefinition.setProType(proType);
				}
				proDefinition.setCreatetime(new Date());
				proDefinitionService.save(proDefinition);
				setJsonString("true");
			}
		//}
		return SUCCESS;
	}

	public String defSave() {
		return custSave();
	}
	
	private String custSave(){
		logger.info("...eneter defSave......");
		boolean deploy = Boolean.parseBoolean(getRequest().getParameter("deploy"));
		//转化xml文件格式
		if(proDefinition.getDrawDefXml()!= null && !proDefinition.getDrawDefXml().equals("")){
			String text = convertFlexXmlToJbpmXml(proDefinition.getDrawDefXml(), proDefinition.getProcessName());
			proDefinition.setDefXml(text); 
			logger.debug("解析的JBPM对应的xml文件：\n" + text);
		}

		// 检查流程名称的唯一性
		if (!proDefinitionService.checkNameByVo(proDefinition)) {
			// 假如不唯一
			setJsonString("{success:false,msg:'流程名称(系统中使用)已存在,请重新填写.'}");
			return SUCCESS;
		}
		if (!proDefinitionService.checkProcessNameByVo(proDefinition)) {
			// 假如不唯一
			setJsonString("{success:false,msg:'流程名称(定义中使用)已存在,请重新填写.'}");
			return SUCCESS;
		}
		if (deploy) {
			save();
		} else {
			Long proTypeId = proDefinition.getProTypeId();
			if (proTypeId != null) {
				GlobalType proType = globalTypeService.get(proTypeId);
				proDefinition.setProType(proType);
			}
			proDefinition.setCreatetime(new Date());
			proDefinitionService.save(proDefinition);
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}

	/**
	 * @description 转变xml文件的格式
	 * @param str
	 * @param processName JBPM流程Key
	 * @return
	 */
	private String convertFlexXmlToJbpmXml(String xml,String processName) {
		String text = "";
		if (xml != null && !xml.equals("")) {
			org.dom4j.Document document = XmlUtil.stringToDocument(xml);
			Element element = document.getRootElement();
			BpmFactory factory = new BpmFactory(document);
			@SuppressWarnings("unchecked")
			Iterator<Element> it = element.elements().iterator();
			text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <process name=\"" + processName + "\" xmlns=\"http://jbpm.org/4.4/jpdl\">";
			while (it.hasNext()) {
				Element el = it.next();
				String str = factory.getInfo(el, el.getName());
				text += str;
			}
			text += "</process>";
		}
		return text;
	}
	/**
	 * 添加及保存操作
	 */
	public String save() {

		Long proTypeId = proDefinition.getProTypeId();
		if (proTypeId != null) {
			GlobalType proType = globalTypeService.get(proTypeId);
			proDefinition.setProType(proType);
		}

		String deploy = getRequest().getParameter("deploy");
		if (logger.isDebugEnabled()) {
			logger.debug("---deploy---" + deploy);
		}

		if (proDefinition.getDefId() != null) {// 更新操作
			ProDefinition proDef = proDefinitionService.get(proDefinition
					.getDefId());
			try {
				BeanUtil.copyNotNullProperties(proDef, proDefinition);
				if ("true".equals(deploy)) {
					jbpmService.saveOrUpdateDeploy(proDef);
				} else {
					proDefinitionService.save(proDef);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else {// 添加流程
			proDefinition.setCreatetime(new Date());

			if (logger.isDebugEnabled()) {
				logger.debug("---start deploy---");
			}

			if ("true".equals(deploy)) {
				jbpmService.saveOrUpdateDeploy(proDefinition);
			} else {
				proDefinitionService.save(proDefinition);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 通过defId取得某个流程是使用表单模板还是在线的定制模板
	 * @return
	 */
	public String formTemp(){
		StringBuffer sb=new StringBuffer("{success:true");
		Short isUseTemplate=FormDefMapping.NOT_USE_TEMPLATE;
		
		ProDefinition proDefinition=proDefinitionService.get(defId);
		
		if(proDefinition!=null && proDefinition.getDeployId()!=null){
			FormDefMapping fdm=formDefMappingService.getByDeployId(proDefinition.getDeployId());
			if(fdm!=null){
				isUseTemplate=fdm.getUseTemplate();
				sb.append(",mappingId:").append(fdm.getMappingId());
			}
		}
		sb.append(",isUseTemplate:"+isUseTemplate+"}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 保存formMapping
	 * @return
	 */
	public String saveFm(){
		
		ProDefinition pd=proDefinitionService.get(defId);
		String useTemplate=getRequest().getParameter("useTemplate");
		
		if(pd!=null && pd.getDeployId()!=null){
			FormDefMapping mapping=formDefMappingService.getByDeployId(pd.getDeployId());
			Short isUseTemplate=FormDefMapping.NOT_USE_TEMPLATE;
			if("true".equals(useTemplate)){
				isUseTemplate=FormDefMapping.USE_TEMPLATE;
			}
			if(mapping!=null){
				mapping.setUseTemplate(isUseTemplate);
			}else{
				mapping=new FormDefMapping();
				mapping.setUseTemplate(isUseTemplate);
				mapping.setDefId(pd.getDefId());
				mapping.setDeployId(pd.getDeployId());
				mapping.setVersionNo(pd.getNewVersion());
			}
			formDefMappingService.save(mapping);

			if("true".equals(useTemplate)){
				List formTemplateList=formTemplateService.getByMappingId(mapping.getMappingId());
				if(formTemplateList.size()==0){
					//为该流程映射添加所有表单定义
					List<Node> nodes = jbpmService.getFormNodesByDeployId(new Long(pd.getDeployId()));
					List<String>nodeNames=new ArrayList<String>();
					for(Node node:nodes){
						nodeNames.add(node.getName());
					}
					formTemplateService.batchAddDefault(nodeNames, mapping);
				}
			}
			setJsonString("{success:true,mappingId:"+mapping.getMappingId()+"}");
		}

		return SUCCESS;
	}

	
	/**
	 * 取得表单映射
	 * TODO
	 * @return
	 */
	public String getFormMapping(){
		ProDefinition pd=proDefinitionService.get(defId);
		StringBuffer sb=new StringBuffer();
		Short isUseTemplate=FormDefMapping.NOT_USE_TEMPLATE;
		if(pd!=null && pd.getDeployId()!=null){
			FormDefMapping mapping=formDefMappingService.getByDeployId(pd.getDeployId());
			if(mapping!=null){
				
			}
		}
		
		return SUCCESS;
	}

	/**
	 * 修改流程状态
	 */
	public String update() {
		ProDefinition orgProDefinition = proDefinitionService.get(proDefinition
				.getDefId());
		orgProDefinition.setStatus(proDefinition.getStatus());
		proDefinitionService.save(orgProDefinition);

		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 判断该流程是否可以重新设置表单
	 */
	public String checkRun(){
		//1.判断流程是否存为运行中的流程[process_run]
		boolean isRun = processRunService.checkRun(defId); //存在
		String msg ="{success:true}";
		if(isRun)//存在
			msg = "{failure:true,msg:'对不起，该流程正在运行不能设置，请谅解！'}";
		setJsonString(msg);
		return SUCCESS;
	}
	
	
	public String inList(){
//		QueryFilter filter = new QueryFilter(getRequest());
		PagingBean pb =getInitPagingBean();
//		filter.setFilterName("InstanceProcess");
//		filter.addParamValue(ProcessRun.RUN_STATUS_RUNNING);
		
		List<ProDefinition> list=proDefinitionService.findRunningPro(proDefinition, ProcessRun.RUN_STATUS_RUNNING, pb);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(
				",result:[");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for(ProDefinition pd:list){
        	 buff.append("{defId:'").append(pd.getDefId())
        	 .append("',subTotal:").append(processRunService.countRunningProcess(pd.getDefId()))
        	 .append(",typeName:'").append(pd.getProType()==null?"":pd.getProType().getTypeName())
        	 .append("',name:'").append(pd.getName())
        	 .append("',createtime:'").append(sdf.format(pd.getCreatetime()))
        	 .append("',deployId:'").append(pd.getDeployId())
        	 .append("',status:").append(pd.getStatus()).append("},");
        }
        if(list.size()>0){
        	buff.deleteCharAt(buff.length()-1);
        }
		buff.append("]}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
}
