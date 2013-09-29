package com.htsoft.est.util;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.htsoft.core.model.DynaModel;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.XmlUtil;
import com.htsoft.est.FlowConstants;
import com.htsoft.est.model.flow.FormTable;
import com.htsoft.est.service.flow.FormTableService;
public class FlowUtil {
	
	/**
	 * 存放流程动态表单动态实体的结构映射<实体名，实体结构>，在应用程序启动时初始化，修改流程表单实体时需要更新
	 */
	public static Map<String,DynaModel> DynaModelMap=new HashMap<String, DynaModel>();
	
	
	public static String  getPrimaryKeyByEntity(String EntityName){
		String rootPath = AppUtil.getAppAbsolutePath();
		String hbmDirPath = rootPath + "WEB-INF/classes/com/htsoft/est/model/process";
		String hbmFile=hbmDirPath + "/" + EntityName + ".hbm.xml";
		org.dom4j.Document doc=XmlUtil.load(hbmFile);
		org.dom4j.Element root=doc.getRootElement();
		org.dom4j.Element idNode=(Element) root.selectSingleNode("class/id");
		String primaryKey=idNode.attributeValue("name");
		return primaryKey;
	}
	/**
	 * 启动时初始化流程表单定义
	 */
	public static void initDynModel(){
		
		FormTableService formTableService=(FormTableService) AppUtil.getBean("formTableService");
		
		List<FormTable> formTables=formTableService.getAllAndFields();
		
		for(FormTable formTable:formTables){
			DynaModel dynaModel=new DynaModel(formTable);
			DynaModelMap.put(formTable.getEntityName(), dynaModel);
		}

	}
	
	public static DynaModel getInitDynModel(java.io.File entityHbmFile){
		
		//取得该实体对应的所的字段及映射
		String entityName = entityHbmFile.getName();
		String entitySplit[]=entityName.split("\\.");
		entityName=entitySplit[0];
		DynaModel model=new DynaModel(entityName);
		Document doc=XmlUtil.load(entityHbmFile);
		if(doc!=null){
			Element classRoot=doc.getRootElement().element("class");
			Iterator<Element> it=classRoot.elementIterator();
			
			while(it.hasNext()){
				Element fieldEl=it.next();
				String name=fieldEl.attributeValue("name");
				if("id".equals(fieldEl.getName())){//主键
					String type=fieldEl.attributeValue("type");
					model.setType(name, FlowConstants.FIELD_TYPE_MAP.get(type));
					model.setPrimaryFieldName(name);
				}else if("property".equals(fieldEl.getName())){
					String type=fieldEl.attributeValue("type");
					model.setType(name, FlowConstants.FIELD_TYPE_MAP.get(type));
				}else if("bag".equals(fieldEl.getName())){//明细
					model.setType(name, Collection.class);
				}
			}
		}
		
		return model;
	}
	
	public static DynaModel getInitDynModel(String entityName){
	
		//取得该实体对应的所的字段及映射
		DynaModel model=new DynaModel(entityName);
		//提高性能时可以考虑放至缓存中TODO
		String entityHbmFile=AppUtil.getAppAbsolutePath()+"/WEB-INF/classes/com/htsoft/est/model/process/"+entityName+".hbm.xml";
		Document doc=XmlUtil.load(entityHbmFile);

		if(doc!=null){
			Element classRoot=doc.getRootElement().element("class");
			Iterator<Element> it=classRoot.elementIterator();
			
			while(it.hasNext()){
				Element fieldEl=it.next();
				String name=fieldEl.attributeValue("name");
				if("id".equals(fieldEl.getName())){
					String type=fieldEl.attributeValue("type");
					model.setType(name, FlowConstants.FIELD_TYPE_MAP.get(type));
					model.setPrimaryFieldName(name);
				}else if("property".equals(fieldEl.getName())){
					String type=fieldEl.attributeValue("type");
					model.setType(name, FlowConstants.FIELD_TYPE_MAP.get(type));
				}else if("bag".equals(fieldEl.getName())){//明细
//					Element oneToManyNode=(Element)fieldEl.selectSingleNode("./one-to-many");
//					String subEntity=oneToManyNode.attributeValue("entity-name");
					model.setType(name, Collection.class);
				}
			}
		}

		return model;
	}
	
//	public static void main(String[] args){
//		FlowUtil.initDynModel();
//	}
}
