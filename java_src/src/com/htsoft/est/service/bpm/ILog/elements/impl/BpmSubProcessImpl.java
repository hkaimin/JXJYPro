package com.htsoft.est.service.bpm.ILog.elements.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.Element;

import com.htsoft.est.FlowConstants;
import com.htsoft.est.service.bpm.ILog.elements.BpmElementsManager;
import com.htsoft.est.service.bpm.ILog.helper.BpmHelper;

/**
 * @description 解析子流程
 * @class BpmSubProcessImpl
 * @extends BpmHelper
 * @implement BpmElementsManager
 * @author YHZ
 * @company www.jee-soft.cn
 * @createtime 2011-5-11PM
 * 
 */
public class BpmSubProcessImpl extends BpmHelper implements BpmElementsManager {

	public BpmSubProcessImpl(Document document) {
		super(document);
	}

	/**
	 * @descrption 子流程，包含属性：sub-process-key,outcome
	 * @param element
	 *            Element对象
	 * @return String
	 */
	@Override
	public String getInfo(Element element) {
		StringBuffer sbf = new StringBuffer("<sub-process ");
		sbf.append(super.getAttributes(element));
		Map<String, String> map = super.getNodeLabels(element, new String[] {
				"sub_process_key" });
		
		sbf.append(" sub-process-key=\"" + map.get("sub_process_key") + "\" ");
		sbf.append(" outcome=\"#{" + FlowConstants.TO_PARENT_PATH +"}\"");
		sbf.append(">\n");
		super.addTransition(element, sbf);
		sbf.append("</sub-process>\n");
		return sbf.toString();
	}

}
