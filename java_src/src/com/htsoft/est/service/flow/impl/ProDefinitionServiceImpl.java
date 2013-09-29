package com.htsoft.est.service.flow.impl;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import org.dom4j.Element;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.XmlUtil;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.flow.ProDefinitionDao;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.GlobalType;
import com.htsoft.est.service.bpm.ILog.factory.BpmFactory;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.flow.ProDefinitionService;
import com.htsoft.est.service.system.GlobalTypeService;

public class ProDefinitionServiceImpl extends BaseServiceImpl<ProDefinition>
		implements ProDefinitionService {

	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private JbpmService jbpmService;

	private ProDefinitionDao dao;

	public ProDefinitionServiceImpl(ProDefinitionDao dao) {
		super(dao);
		this.dao = dao;
	}

	public ProDefinition getByDeployId(String deployId) {
		return dao.getByDeployId(deployId);
	}

	public ProDefinition getByName(String name) {
		return dao.getByName(name);
	}

	@Override
	public List<ProDefinition> getByRights(AppUser curUser,
			ProDefinition proDefinition, QueryFilter filter) {
		return dao.getByRights(curUser, proDefinition, filter);
	}

	@Override
	public boolean checkNameByVo(ProDefinition proDefinition) {
		return dao.checkNameByVo(proDefinition);
	}

	@Override
	public boolean checkProcessNameByVo(ProDefinition proDefinition) {
		return dao.checkProcessNameByVo(proDefinition);
	}

	@Override
	public List<ProDefinition> findRunningPro(ProDefinition proDefinition,
			Short runstate, PagingBean pb) {
		return dao.findRunningPro(proDefinition, runstate, pb);
	}

	/**
	 * 保存流程信息
	 */
	@Override
	public String defSave(ProDefinition proDefinition, Boolean deploy) {
		logger.info("...eneter defSave......");
		String msg = "";
		// 转化xml文件格式
		if (proDefinition.getDrawDefXml() != null && !proDefinition.getDrawDefXml().equals("")) {
			String text = change(proDefinition.getDrawDefXml(), proDefinition.getProcessName());
			proDefinition.setDefXml(text);
			logger.debug("解析的JBPM对应的xml文件：\n" + text);
		}
		//流程类型
		Long proTypeId = proDefinition.getProTypeId();
		if (proTypeId != null) {
			GlobalType proType = globalTypeService.get(proTypeId);
			proDefinition.setProType(proType);
		}

		// 检查流程名称的唯一性
		if (!checkNameByVo(proDefinition)) {
			// 假如不唯一
			msg = "流程名称(系统中使用)已存在,请重新填写.";
		}
		if (!checkProcessNameByVo(proDefinition)) {
			// 假如不唯一
			msg = "流程名称(定义中使用)已存在,请重新填写.";
		}
		if(deploy){
			save(proDefinition,deploy.toString());
			msg = "true";
			logger.debug("flex流程设计【保存并发布】操作返回结果：" + msg);
		} else {			
			proDefinition.setCreatetime(new Date());
			save(proDefinition);
			msg = "true";
			logger.debug("flex流程设计【保存】操作返回结果：" + msg);
		}
		return msg;
	}

	/**
	 * @description 转变xml文件的格式
	 * @param str
	 * @param processName
	 *            JBPM流程Key
	 * @return
	 */
	private String change(String xml, String processName) {
		String text = "";
		if (xml != null && !xml.equals("")) {
			org.dom4j.Document document = XmlUtil.stringToDocument(xml);
			Element element = document.getRootElement();
			BpmFactory factory = new BpmFactory(document);
			@SuppressWarnings("unchecked")
			Iterator<Element> it = element.elements().iterator();
			text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n <process name=\""
					+ processName + "\" xmlns=\"http://jbpm.org/4.4/jpdl\">";
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
	private void save(ProDefinition proDefinition, String deploy) {
		if (logger.isDebugEnabled()) {
			logger.debug("---deploy---" + deploy);
		}
		if (proDefinition.getDefId() != null) {// 更新操作
			ProDefinition proDef = get(proDefinition.getDefId());
			try {
				BeanUtil.copyNotNullProperties(proDef, proDefinition);
				if ("true".equals(deploy))
					jbpmService.saveOrUpdateDeploy(proDef);
				else 
					save(proDef);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		} else {// 添加流程
			proDefinition.setCreatetime(new Date());
			
			if (logger.isDebugEnabled()) {
				logger.debug("---start deploy---");
			}
			
			if ("true".equals(deploy))
				jbpmService.saveOrUpdateDeploy(proDefinition);
			else 
				save(proDefinition);
		}
	}
}