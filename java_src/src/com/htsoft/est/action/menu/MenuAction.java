package com.htsoft.est.action.menu;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.google.gson.Gson;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.util.XmlUtil;

import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.system.AppRole;

import org.dom4j.io.OutputFormat;

/**
 * @description 显示系统左边的功能菜单
 * @class MenuAction
 * @author csx,YHZ
 * @company www.jee-soft.cn
 * @createtime 2011-1-27AM
 * 
 */
public class MenuAction extends BaseAction {

	/**
	 * 根据权限获取当前用户的菜单
	 * @return
	 */
	private Document getCurDocument(){
		Document doc=getModuleDocument();
		Set<String> rights=ContextUtil.getCurrentUser().getRights();
		
		if(rights.contains(AppRole.SUPER_RIGHTS)){//具有超级管理权限
			return doc;
		}

		Document newDoc=DocumentHelper.createDocument();
		Element root = newDoc.addElement( "Menus" );
		
		createSubMenus(rights,doc.getRootElement(),root);
		
		if(logger.isDebugEnabled()){
			logger.debug("XML:" + newDoc.asXML());
		}
		
		return newDoc;
		
	}
	private Document getModuleDocument(){
		String topMenuId=getRequest().getParameter("topMenuId");
		if(StringUtils.isEmpty(topMenuId)){
			topMenuId="oa";
		}

		Document doc=AppUtil.getItemsMenus().get(topMenuId.toLowerCase());
		
		return doc;
		
	}
	
	private void createSubMenus(Set<String>rights,Element curNodes,Element newCurNodes){
		List els = curNodes.elements();
		if(els.size()==0) return ;
		
		for (int i = 0; i < els.size(); i++) {
			Element el = (Element) els.get(i);
			String id = el.attributeValue("id");
			if(id!=null){
				if(rights.contains(id)){
					Element newNodes=newCurNodes.addElement(el.getName());
					Iterator<Attribute> it=el.attributeIterator();
					
					while(it.hasNext()){
						Attribute at=it.next();
						newNodes.addAttribute(at.getName(),at.getValue());
					}
					createSubMenus(rights,el,newNodes);
				}
			}			
		}
	}
	

	/**
	 * 返回当前用户左树的菜单
	 * @return
	 */
	public String panelTree(){
		
		String isReload=getRequest().getParameter("isReload");
		//reload the menu
		if("true".equals(isReload)){
			
			AppUtil.reloadMenu();
		}
		
		Gson gson=new Gson();
		Document doc=getCurDocument();
		
		//Document doc=getModuleDocument();
		
		StringBuffer sb = new StringBuffer("[");
		
		if (doc != null) {
			Element root = doc.getRootElement();
			List els = root.elements();

			for (int i = 0; i < els.size(); i++) {
				Element el = (Element) els.get(i);
				
				Attribute id = el.attribute("id");
				Attribute text = el.attribute("text");
				Attribute iconCls = el.attribute("iconCls");
				
				sb.append("{id:'").append(id == null ? "" : id.getValue()).append("',");
				sb.append("text:'").append(text == null ? "" : text.getValue()).append("',");
				sb.append("iconCls:'").append(iconCls == null ? "" : iconCls.getValue()).append("',");
				sb.append("subXml:").append(gson.toJson(getModelXml(doc,id.getValue())))
				.append("},");

			}
			if(els.size()>0){
				sb.deleteCharAt(sb.length()-1);
			}
		}
		
		sb.append("]");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 返回某个模板的XML菜单
	 * @param doc
	 * @param modelId
	 * @return
	 */
	protected String getModelXml(Document doc,String modelId){
		StringBuffer sb=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r");
		
		Element el=doc.getRootElement();
		
		List nodes=el.selectNodes("/Menus/Items[@id='"+modelId+"']/*");
		
		sb.append("<Menus>\r");
		for(int i=0;i<nodes.size();i++){
			Node node=(Node)nodes.get(i);
			sb.append(node.asXML());
		}
		sb.append("\r</Menus>\r");
		
		return sb.toString();
	}

}
