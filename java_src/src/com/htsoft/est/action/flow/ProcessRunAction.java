package com.htsoft.est.action.flow;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.htsoft.core.Constants;
import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.jbpm.pv.TaskInfo;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.util.JsonUtil;
import com.htsoft.core.util.StringUtil;

import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.flow.ProcessRun;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.flow.HistoryTaskService;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.flow.ProcessRunService;
import com.htsoft.est.service.system.AppUserService;


import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class ProcessRunAction extends BaseAction{
	@Resource
	private ProcessRunService processRunService;
	private ProcessRun processRun;
	@Resource
	private JbpmService jbpmService; 
	
	@Resource
	private HistoryTaskService historyTaskService;
	
	@Resource
	private AppUserService appUserService;
	
	private Long runId;

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public ProcessRun getProcessRun() {
		return processRun;
	}

	public void setProcessRun(ProcessRun processRun) {
		this.processRun = processRun;
	}
	
	/**
	 * 显示流程历史
	 * @return
	 */
	public String history(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<ProcessRun> list= processRunService.getAll(filter);
		
		Type type=new TypeToken<List<ProcessRun>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 显示我正在参与的尚未结束流程，以方便对流程进行追回等操作
	 * @return
	 */
	public String myRunning(){
		QueryFilter filter=new QueryFilter(getRequest());
		filter.setFilterName("myRunning");
		filter.addParamValue(ContextUtil.getCurrentUserId());
		filter.addParamValue(ProcessRun.RUN_STATUS_RUNNING);
		
		List<ProcessRun> processRunList=processRunService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:[");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(ProcessRun run:processRunList){
			buff.append("{runId:'").append(run.getRunId()).append("',subject:'")
			.append(run.getSubject()).append("',createtime:'").append(sdf.format(run.getCreatetime()))
			.append("',piId:'").append(run.getPiId()).append("',defId:'").append(run.getProDefinition().getDefId())
			.append("',runStatus:'").append(run.getRunStatus()).append("'");
			//通过runid取得任务
			List<Task> listTask=jbpmService.getTasksByPiId(run.getPiId());
			if(listTask!=null){
				String tasks="";
				String usernames="";
				int i=0;
				for(Task task:listTask){
					if(i++>0){
						tasks+=",";
						usernames+=",";
					}
					tasks+=task.getName();
					if(task.getAssignee()!=null && StringUtil.isNumeric(task.getAssignee())){
						AppUser appUser=appUserService.get(new Long(task.getAssignee()));
						usernames+=appUser.getFullname();
					}else{//按角色取
						TaskImpl taskImpl=(TaskImpl)task;
						Iterator<ParticipationImpl> it = taskImpl.getParticipations().iterator();
						while(it.hasNext()){
							ParticipationImpl part=it.next();
							if(part.getUserId()!=null){
								if(StringUtil.isNumeric(part.getUserId())){
									AppUser appUser=appUserService.get(new Long(part.getUserId()));
									usernames+=appUser.getFullname();
								}
							}else if(part.getGroupId()!=null){
								if(StringUtil.isNumeric(part.getGroupId())){
									List<AppUser> users=appUserService.getUsersByRoleId(new Long(part.getGroupId()));
									for(AppUser user:users){
										usernames+=user.getFullname();
									}
								}
							}
						}
					}
				}
				buff.append(",tasks:'").append(tasks).append("'");
				buff.append(",exeUsers:'").append(usernames).append("'");
			}
			buff.append("},");
		}
		
		if(processRunList.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		buff.append("}");
		
		jsonString=buff.toString();
		
		//setRunResult(processRunList,filter.getPagingBean());
		
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		
		//加上过滤条件，表示只显示当前用户的申请
		filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		
		List<ProcessRun> list= processRunService.getAll(filter);
		
		jsonString=formatRunList(list,filter.getPagingBean().getTotalItems());
		
		return SUCCESS;
	}
	
	
	private String formatRunList(List<ProcessRun> processRunList,Integer totalItems){
		Gson gson=JsonUtil.getGson();
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(totalItems).append(",result:[");
		
		for(ProcessRun run:processRunList){
			buff.append("{runId:").append(gson.toJson(run.getRunId())).append(",subject:")
			.append(gson.toJson(run.getSubject())).append(",createtime:").append(gson.toJson(run.getCreatetime()))
			.append(",piId:").append(gson.toJson(run.getPiId())).append(",defId:").append(gson.toJson(run.getProDefinition().getDefId()))
			.append(",runStatus:").append(gson.toJson(run.getRunStatus())).append("},");
		}
		
		if(processRunList.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		buff.append("}");
		return buff.toString();
	}
	
	
	/**
	 * 浏览我参与的流程
	 * @return
	 */
	public String my(){
		QueryFilter filter=new QueryFilter(getRequest());
		
		//该filterName配置在app-dao.xml中
		filter.setFilterName("MyAttendProcessRun");
		//加上过滤条件，表示只显示当前用户的申请
		filter.addParamValue(ContextUtil.getCurrentUserId());
		
		List<ProcessRun> processRunList=processRunService.getAll(filter);
		
		jsonString=formatRunList(processRunList,filter.getPagingBean().getTotalItems());
		return SUCCESS;
	}
	
	/**
	 * 删除一个尚未启动的流程
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				processRunService.remove(new Long(id));
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
		ProcessRun processRun=processRunService.get(runId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(processRun));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		processRunService.save(processRun);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String instance(){
		QueryFilter filter=new QueryFilter(getRequest());
		
		List<ProcessRun> list=processRunService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		JSONSerializer serializer = JsonUtil.getJSONSerializer("createtime")
				.exclude("appUser","processForms");
		
		buff.append(serializer.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	
	public String tasks(){
		String runId=getRequest().getParameter("runId");
		ProcessRun processRun=processRunService.get(new Long(runId));
		String piId=processRun.getPiId();
		List<Task> tasks=jbpmService.getTasksByPiId(piId);
		List<TaskImpl> list=new ArrayList<TaskImpl>();
		
		for(Task task:tasks){
			list.add((TaskImpl)task);
		}
		List<TaskInfo> infos=constructTaskInfos(list,processRun);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(
				",result:");
		Gson gson=new GsonBuilder().setDateFormat(Constants.DATE_FORMAT_FULL).create();
		buff.append(gson.toJson(infos));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	protected List<TaskInfo> constructTaskInfos(List<TaskImpl> taskImpls,ProcessRun processRun){
		List<TaskInfo> taskInfoList=new ArrayList<TaskInfo>();
		for(TaskImpl taskImpl:taskImpls){
			TaskInfo taskInfo=new TaskInfo(taskImpl);
			if(taskImpl.getAssignee()!=null&&!taskImpl.getAssignee().trim().equalsIgnoreCase("null")){
				try{
					AppUser user=appUserService.get(new Long(taskImpl.getAssignee()));
					taskInfo.setAssignee(user.getFullname());
				}catch(Exception ex){
					logger.error(ex);
				}
			}
			if(taskImpl.getSuperTask()!=null){
				taskImpl=taskImpl.getSuperTask();
			}
			if(processRun!=null){
				taskInfo.setTaskName(processRun.getSubject() + "--" + taskImpl.getActivityName());
				taskInfo.setActivityName(taskImpl.getActivityName());
			}
			//显示任务，需要加上流程的名称
			taskInfoList.add(taskInfo);
		}
		return taskInfoList;
	}
	
	public String end(){
		String runId=getRequest().getParameter("runIds");
		String[] ids=runId.split(",");
		for(String id:ids){
			ProcessRun processRun=processRunService.get(new Long(id));
			if(processRun!=null){
				String piId=processRun.getPiId();
				try{
					ProcessInstance pi= jbpmService.getProcessInstance(piId);
					if(pi!=null){
						jbpmService.endProcessInstance(piId);
					}
					processRun.setRunStatus(ProcessRun.RUN_STATUS_FINISHED);
					processRunService.save(processRun);
				}catch(Exception e){
					e.printStackTrace();
					setJsonString("{success:false}");
					return SUCCESS;
				}
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 任务回退，只允许当前用户执行后提交到下一步，并且下一步的任务尚没有处理
	 * @return
	 */
	public String rollback(){
		//前一任务执节点名
		String preTaskName=null;
		
		ProcessRun processRun=processRunService.get(runId);
		//取得该实例的所有的任务，并且任务的前一节点执行人为当前人，才允许回滚
		List<Task> tasks=jbpmService.getTasksByPiId(processRun.getPiId());
		String assignee=ContextUtil.getCurrentUserId().toString();
		for(Task task:tasks){
			List<Transition> trans=jbpmService.getInTransForTask(task.getId());
			for(Transition tran:trans){
				String preType=tran.getSource().getType();
				logger.info("pre node type:" + preType);
				
				if("decision".equals(preType) || "fork".equals(preType)){//对于前一节点为汇集及分支的情况
					Activity source= tran.getSource();
					List preTrans=source.getIncomingTransitions();
					for(int i=0;i<preTrans.size();i++){
						Transition tr=(Transition)preTrans.get(i);
						String outcome=tr.getName();
						String activityName=tr.getSource().getName();
						List<HistoryTaskInstanceImpl> list=historyTaskService.getByPiIdAssigneeOutcome(processRun.getPiId(), assignee, activityName, outcome);
						if(list.size()>0){
							HistoryTaskInstanceImpl impl=(HistoryTaskInstanceImpl)list.get(0);
							preTaskName=impl.getActivityName();
							logger.info("allow back 2:" + impl.getActivityName());
							break;
						}
					}
					
				}else if("task".equals(preType)){//前一节点为任务节点
					String outcome=tran.getName();
					String activityName=tran.getSource().getName();
					List<HistoryTaskInstanceImpl> list=historyTaskService.getByPiIdAssigneeOutcome(processRun.getPiId(), assignee, activityName, outcome);
					if(list.size()>0){
						HistoryTaskInstanceImpl impl=(HistoryTaskInstanceImpl)list.get(0);
						preTaskName=impl.getActivityName();
						logger.info("allow back :" + impl.getActivityName());
						break;
					}
				}
				
			}
		}
		//假若前一任务为当前执行用户，则允许回退
		if(preTaskName!=null){
			logger.info("prepared to jump previous task node");
			jbpmService.jumpToPreTask(processRun.getPiId(),assignee,preTaskName);
			jsonString="{success:true}";
		}else{
			jsonString="{success:false}";
		}
		
		return SUCCESS;
	}
	
}
