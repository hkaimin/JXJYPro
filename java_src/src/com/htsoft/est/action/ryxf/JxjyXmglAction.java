package com.htsoft.est.action.ryxf;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.ContextUtil;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;

import com.htsoft.est.model.jxjy.JxjyKtgl;
import com.htsoft.est.model.jxjy.JxjyRyxfgl;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.project.CourseService;
import com.htsoft.est.service.ryxf.JxjyRyxfglService;
import com.htsoft.est.service.ryxf.JxjyXmglService;
import com.htsoft.est.service.ryxf.MyJdbcService;
/**
 * 
 * @author 
 *
 */
public class JxjyXmglAction extends BaseAction{
	@Resource
	private JxjyXmglService jxjyXmglService;
	@Resource
	private MyJdbcService myJdbcService; 
	@Resource
	private JxjyRyxfglService jxjyRyxfglService;
	@Resource
	private CourseService courseService;
	private JxjyXmgl jxjyXmgl;
	
	private Long xmId;
	private Long ktidVo;
	private Long xmid;

	public Long getXmId() {
		return xmId;
	}

	public void setXmId(Long xmId) {
		this.xmId = xmId;
	}

	public Long getKtidVo() {
		return ktidVo;
	}

	public void setKtidVo(Long ktidVo) {
		this.ktidVo = ktidVo;
	}

	public JxjyXmgl getJxjyXmgl() {
		return jxjyXmgl;
	}

	public void setJxjyXmgl(JxjyXmgl jxjyXmgl) {
		this.jxjyXmgl = jxjyXmgl;
	}

	public Long getXmid() {
		return xmid;
	}

	public void setXmid(Long xmid) {
		this.xmid = xmid;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
//		QueryFilter filter=new QueryFilter(getRequest());
//		List<JxjyXmgl> list= jxjyXmglService.getAll(filter);
//		
//		Type type=new TypeToken<List<JxjyXmgl>>(){}.getType();
//		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
//		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		
		List<JxjyXmgl> list= myJdbcService.getXmgl();
		Type type=new TypeToken<List<JxjyXmgl>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
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
				jxjyXmglService.remove(new Long(id));
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
		JxjyXmgl jxjyXmgl=jxjyXmglService.get(xmId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(jxjyXmgl));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(jxjyXmgl.getXmId()==null){
			jxjyXmglService.save(jxjyXmgl);
		}else{
			JxjyXmgl orgJxjyXmgl=jxjyXmglService.get(jxjyXmgl.getXmId());
			try{
				BeanUtil.copyNotNullProperties(orgJxjyXmgl, jxjyXmgl);
				jxjyXmglService.save(orgJxjyXmgl);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String caurseRes(){
		AppUser user = ContextUtil.getCurrentUser();
		JxjyXmgl xmgl = jxjyXmglService.get(xmid);
		JxjyKtgl ktgl = courseService.get(ktidVo);
		
		JxjyRyxfgl ryxf = new JxjyRyxfgl();
		Date date = new Date();
		SimpleDateFormat sip = new SimpleDateFormat("yyyy-MM-dd");
		String str = sip.format(date);
		ryxf.setRq(str);
		ryxf.setKt(ktgl.getKtmc());
		ryxf.setRybh(user.getUserId());
		ryxf.setXm(user.getFullname());
		ryxf.setKtId(ktgl.getKtId());
		ryxf.setXflx("项目学分");
		ryxf.setXflb(xmgl.getXflb());
		ryxf.setHdxs(xmgl.getHdfs());
		ryxf.setXf(ktgl.getXf());
		ryxf.setXs(ktgl.getXs());
		ryxf.setSfdw(xmgl.getZbdw());
		ryxf.setShzt("2");
		ryxf.setRsjsh("2");
		ryxf.setIs_commit(0L);
		jxjyRyxfglService.save(ryxf);
		return SUCCESS;
		
	}
	
	public String caurseDel(){
		AppUser user = ContextUtil.getCurrentUser();
		List<Map<String,Object>> list = myJdbcService.getRyxfByKtid(user.getUserId(), ktidVo);
		String ryxfId="0";
		if(list.size()>0){
			ryxfId = (BigDecimal)list.get(0).get("ID")+"";
		}
		jxjyRyxfglService.remove(new Long(ryxfId));
		return SUCCESS;
	}
	
}
