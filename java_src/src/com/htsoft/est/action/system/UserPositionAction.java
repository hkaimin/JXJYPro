package com.htsoft.est.action.system;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.util.BeanUtil;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.system.UserPosition;
import com.htsoft.est.service.system.UserPositionService;


/**
 * 
 * @author 
 *
 */
public class UserPositionAction extends BaseAction{
	@Resource
	private UserPositionService userPositionService;
	private UserPosition userPosition;
	
	private Long userPosId;

	public Long getUserPosId() {
		return userPosId;
	}

	public void setUserPosId(Long userPosId) {
		this.userPosId = userPosId;
	}

	public UserPosition getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(UserPosition userPosition) {
		this.userPosition = userPosition;
	}
	
	/**
	 * 查找某一用户的职位
	 * @return
	 */
	public String find(){
		String userId=getRequest().getParameter("userId");
		if(StringUtils.isNotEmpty(userId)){
			StringBuffer sb=new StringBuffer("{success:true,result:[");
			List<UserPosition> list=userPositionService.getByUserPos(new Long(userId));
			for(UserPosition up:list){
				sb.append("{userPosId:'").append(up.getUserPosId()).append("',posId:'").append(up.getPosition().getPosId()).append("',posName:'").append(up.getPosition().getPosName()).append("',isPrimary:'").append(up.getIsPrimary()).append("'},");
			}
			if(list.size()>0){
				sb.deleteCharAt(sb.length()-1);
			}
			sb.append("]}");
			jsonString=sb.toString();
		}else{
			jsonString="{success:true,result[]}";
		}
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<UserPosition> list= userPositionService.getAll(filter);
		
		Type type=new TypeToken<List<UserPosition>>(){}.getType();
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
				userPositionService.remove(new Long(id));
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
		UserPosition userPosition=userPositionService.get(userPosId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(userPosition));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(userPosition.getUserPosId()==null){
			userPositionService.save(userPosition);
		}else{
			UserPosition orgUserPosition=userPositionService.get(userPosition.getUserPosId());
			try{
				BeanUtil.copyNotNullProperties(orgUserPosition, userPosition);
				userPositionService.save(orgUserPosition);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 删除用户部门
	 * @return
	 */
	public String del(){
		if(userPosId!=null){
			userPositionService.remove(userPosId);
		}
		return SUCCESS;
	}
}
