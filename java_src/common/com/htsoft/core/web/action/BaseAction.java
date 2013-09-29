package com.htsoft.core.web.action;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import org.springframework.mail.MailSender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.htsoft.core.Constants;
import com.htsoft.core.engine.MailEngine;
import com.htsoft.core.web.paging.PagingBean;



/**
 * Ext Base Action for all the request.
 * 
 * @author csx
 * 
 */
public class BaseAction{
	public static final String SUCCESS="success";
	public static final String INPUT="input";
	
	/**
	 * 成功跳转的页面(jsp)
	 */
	private String successResultValue="/jsonString.jsp";

	public String getSuccessResultValue() {
		return successResultValue;
	}

	public void setSuccessResultValue(String successResultValue) {
		this.successResultValue = successResultValue;
	}

	public static final String JSON_SUCCESS="{success:true}";
	
	/**
	 * 结合Ext的分页功能： dir DESC limit 25 sort id start 50
	 */
	/**
	 * 当前是升序还是降序排数据
	 */
	protected String dir;
	/**
	 * 排序的字段
	 */
	protected String sort;
	/**
	 * 每页的大小
	 */
	protected Integer limit=25;
	/**
	 * 开始取数据的索引号
	 */
	protected Integer start=0;

	protected String jsonString=JSON_SUCCESS;

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonString() {
		return jsonString;
	}

	public BaseAction() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected transient final Log logger = LogFactory.getLog(getClass());

	protected MailEngine mailEngine;
	
	protected MailSender mailSender;

	
	public final String CANCEL = "cancel";

	public final String VIEW = "view";

	/**
	 * Convenience method to get the request
	 * 
	 * @return current request
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Convenience method to get the response
	 * 
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * Convenience method to get the session. This will create a session if one
	 * doesn't exist.
	 * 
	 * @return the session from the request (request.getSession()).
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	// ---------------------------Methods------------------------------

	protected PagingBean getInitPagingBean() {
		PagingBean pb = new PagingBean(start,limit);
		return pb;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}
	
	public MailEngine getMailEngine(){
		return mailEngine;
	}

	public String list() {
		return SUCCESS;
	}

	public String edit() {
		return INPUT;
	}

	public String save() {
		return INPUT;
	}

	public String delete() {
		return SUCCESS;
	}

	public String multiDelete() {
		return SUCCESS;
	}

	public String multiSave() {
		return SUCCESS;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	/**
	 * 按url返回其默认对应的jsp
	 * @return
	 * @throws Exception
	 */
	public String execute() throws Exception {
		HttpServletRequest request=getRequest();   
        String uri=request.getRequestURI();   
        String url=uri.substring(request.getContextPath().length());   
        url=url.replace(".do", ".jsp");   
        url="/pages"+url;
        
        if(logger.isInfoEnabled()){
        	logger.info("forward url:" + url);
        }
        setSuccessResultValue(url);   
        return SUCCESS;   

	}
	
	/**
	 * gson list的列表
	 * @param listData
	 * @param totalItems 
	 * @param onlyIncludeExpose 仅是格式化包括@Expose标签的字段
	 * @return
	 */
	public String gsonFormat(List listData,int totalItems,boolean onlyIncludeExpose){
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(totalItems).append(",result:");
		
		Gson gson=null;
		if(onlyIncludeExpose){
			gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat(Constants.DATE_FORMAT_FULL).create();
		}else{
			gson=new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_FULL).create();
		}
		buff.append(gson.toJson(listData));
		
		buff.append("}");
		
		return buff.toString();
	}
	
	public String gsonFormat(List listData,int totalItems){
		return gsonFormat(listData,totalItems,false);
	}

}
