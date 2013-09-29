package com.htsoft.est.service.flow;


import com.htsoft.core.model.DynaModel;
import com.htsoft.est.action.flow.FlowRunInfo;
/**
 * 流程表单与实体表操作类
 * <B><P>EST-BPM -- http://www.jee-soft.cn</P></B>
 * <B><P>Copyright (C) 2008-2010 GuangZhou HongTian Software Company (广州宏天软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P></P>
 * <P>product:est-bpm</P>
 * <P></P> 
 * @see com.htsoft.est.service.flow.FlowFormService
 * <P></P>
 * @author 
 * @version V1
 * @create: 2010-12-23下午05:43:16
 */
public interface FlowFormService {
	/**
	 * 保存业务数据至实体表
	 * @param flowRunInfo
	 */
	public DynaModel doSaveData(FlowRunInfo flowRunInfo);
	public boolean deleteItems(String strIds,Long tableId);
}
