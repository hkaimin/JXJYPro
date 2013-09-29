package com.htsoft.est.action.flow;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.htsoft.core.Constants;
import com.htsoft.core.jbpm.pv.TaskInfo;
import com.htsoft.core.util.ContextUtil;

import com.htsoft.core.web.action.BaseAction;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.info.ShortMessage;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.info.ShortMessageService;

/**
 * 流程中的任务的显示及操作
 * @author csx
 *
 */
public class TaskAction extends BaseAction{
	@Resource(name="flowTaskService")
	private com.htsoft.est.service.flow.TaskService flowTaskService;
	@Resource
	private TaskService taskService;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private JbpmService jbpmService;
	
	/**
	 * 按分页取得目前所有的正在进行的任务
	 * @return
	 */
	public String all(){
		String taskName=getRequest().getParameter("taskName");
		PagingBean pb=new PagingBean(start, limit);
		List<TaskInfo> tasks=flowTaskService.getAllTaskInfos(taskName, pb);
		setJsonString(gsonFormat(tasks, pb.getTotalItems()));
		return SUCCESS;
	}
	
	/**
	 * 取得某个任务的处理用户
	 * @return
	 */
	public String users(){
		String taskId=getRequest().getParameter("taskId");
		String activityName=getRequest().getParameter("activityName");
		Set<AppUser> users=jbpmService.getNodeHandlerUsers(taskId, activityName);
		StringBuffer uIds=new StringBuffer();
		StringBuffer uNames=new StringBuffer();
		Iterator<AppUser>it=users.iterator();
		int i=0;
		while(it.hasNext()){
			AppUser user=it.next();
			if(i>0){
				uIds.append(",");
				uNames.append(",");
			}
			uIds.append(user.getUserId());
			uNames.append(user.getFullname());
			i++;
		}
		
		jsonString="{success:true,userIds:'" + uIds.toString() + "',userNames:'" + uNames.toString()+ "'}";
		
		return SUCCESS;
	}
	
	/**
	 * 设置任务过期时间
	 * @return
	 */
	public String due(){
		String taskIds=getRequest().getParameter("taskIds");
		String dueDateStr=getRequest().getParameter("dueDate");
		if(logger.isDebugEnabled()){
			logger.debug("taskIds:" + taskIds + " dueDate:" + dueDateStr);
		}
		if(StringUtils.isNotEmpty(taskIds)){
			String[]taskIdArr=taskIds.split("[,]");
			try{
				Date dueDate=DateUtils.parseDate(dueDateStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
				for(String taskId:taskIdArr){
					Task task=taskService.getTask(taskId);
					task.setDuedate(dueDate);
					taskService.saveTask(task);
				}
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		
		return SUCCESS;
	}
	//任务指派
	public String handler(){
		String taskIds=getRequest().getParameter("taskIds");
		String userId=getRequest().getParameter("userId");
		
		if(logger.isDebugEnabled()){
			logger.debug("taskIds:" + taskIds + " userId:" + userId);
		}
		if(StringUtils.isNotEmpty(taskIds)){
			String[]taskIdArr=taskIds.split("[,]");
			for(String taskId:taskIdArr){
				taskService.assignTask(taskId, userId);
			}
		}
		
		return SUCCESS;
		
	}
	
	public String list(){
		PagingBean pb=new PagingBean(start, limit);
		List<TaskInfo> tasks=flowTaskService.getTaskInfosByUserId(ContextUtil.getCurrentUserId().toString(),pb);
		setJsonString(gsonFormat(tasks, pb.getTotalItems()));
		return SUCCESS;
	}
	
	public String change(){
		HttpServletRequest request=getRequest();
		String taskId=request.getParameter("taskId");
		String userId=request.getParameter("userId");
		String curUserId=ContextUtil.getCurrentUserId().toString();
		Task task=taskService.getTask(taskId);
		if(task!=null && curUserId.equals(task.getAssignee())){
			taskService.assignTask(taskId, userId);
			String msg=request.getParameter("msg");
			if(StringUtils.isNotEmpty(msg)){
				//添加短信息提示
				shortMessageService.save(AppUser.SYSTEM_USER,userId,msg,ShortMessage.MSG_TYPE_TASK);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 释放任务
	 * @return
	 */
	public String unlock(){
		String taskId=getRequest().getParameter("taskId");
		Task task=taskService.getTask(taskId);
		
		String curUserId=ContextUtil.getCurrentUserId().toString();
		
		if(task!=null && curUserId.equals(task.getAssignee())){//为本人的任务，并且尚未完成才能解锁
			taskService.assignTask(task.getId(), null);
			setJsonString("{success:true,unlocked:true}");
		}else{
			setJsonString("{success:true,unlocked:false}");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 锁定任务
	 * @return
	 */
	public String lock(){
		
		String taskId=getRequest().getParameter("taskId");
		Task task=taskService.getTask(taskId);
		
		if(task!=null && task.getAssignee()==null){//该任务尚未被分配，或该任务已经被处理完毕
			taskService.assignTask(task.getId(), ContextUtil.getCurrentUserId().toString());
			setJsonString("{success:true,hasAssigned:false}");
		}else{
			setJsonString("{success:true,hasAssigned:true}");
		}
		
		return SUCCESS;
	}
	
    public String display(){
		
		PagingBean pb=new PagingBean(0, 8);//获取前八条数据
		List<TaskInfo> tasks=flowTaskService.getTaskInfosByUserId(ContextUtil.getCurrentUserId().toString(),pb);
        getRequest().setAttribute("taskList", tasks);
		return "display";
	}
    /**
     * 检测当前任务是否被锁定，如果是自己的或者未锁定，则返回TRUE,
     * 已经被他人锁定，则返回FALSE
     * @return
     */
    public String check(){
		
		String taskId=getRequest().getParameter("taskId");
		TaskImpl task=(TaskImpl)taskService.getTask(taskId);
		
		StringBuffer sb=new StringBuffer("{success:true,isSubFlow:");
		//为子流程任务
		boolean isSubFlow=false;
		
		if(task!=null &&  task.getExecution()!=null && task.getExecution().getSuperProcessExecution()!=null){
			isSubFlow=true;
		}
		sb.append(isSubFlow);
		String cruUserId=ContextUtil.getCurrentUserId().toString();

		if(task!=null &&task.getAssignee()!=null&&task.getAssignee().equals(cruUserId)){//该任务尚未被分配，或该任务已经被处理完毕
			//Skip here
		}else if(task!=null && task.getAssignee()==null){//任务尚未授予人员
			taskService.assignTask(task.getId(),cruUserId );
			sb.append(",assigned:true");
		}else{
			sb.append(",assigned:false");
		}
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
}
