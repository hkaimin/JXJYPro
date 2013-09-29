package com.htsoft.est.service.flow.impl;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskImpl;


import com.htsoft.core.jms.MailMessageProducer;
import com.htsoft.core.jms.MobileMessageProducer;
import com.htsoft.core.model.DynaModel;
import com.htsoft.core.model.MailModel;
import com.htsoft.core.service.DynamicService;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.core.util.StringUtil;
import com.htsoft.est.action.flow.FlowRunInfo;
import com.htsoft.est.model.communicate.SmsMobile;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.model.flow.ProcessRun;
import com.htsoft.est.model.info.ShortMessage;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.communicate.SmsMobileService;
import com.htsoft.est.service.flow.FlowFormService;
import com.htsoft.est.service.flow.JbpmService;
import com.htsoft.est.service.flow.ProDefinitionService;
import com.htsoft.est.service.flow.ProcessRunService;
import com.htsoft.est.service.flow.ProcessService;
import com.htsoft.est.service.flow.RunDataService;
import com.htsoft.est.service.info.ShortMessageService;
import com.htsoft.est.service.system.AppUserService;


public class ProcessServiceImpl implements ProcessService{
	
	private final Log logger=LogFactory.getLog(ProcessServiceImpl.class);
	
	public ProcessServiceImpl() {}
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	ProDefinitionService proDefinitionService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private FlowFormService flowFormService;
	
	@Resource
	private AppUserService appUserService;
	@Resource
	private MailMessageProducer mailMessageProducer;
	
	@Resource
	private MobileMessageProducer mobileMessageProducer;
	
	@Resource
	private SmsMobileService smsMobileService;
	
	@Resource
	private RunDataService runDataService;
	
	@Resource
	private ShortMessageService shortMessageService;
	
	
	/**
	 * 启动工作流  传入defId
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public ProcessRun doStartFlow(HttpServletRequest request) throws Exception{
		FlowRunInfo startInfo=getFlowRunInfo(request);
		ProcessRun processRun=null;
		
		String useTemplate=request.getParameter("useTemplate");

		//若在提交参数中指定启动工作流前需要预处理
		int result=invokeHandler(startInfo, "PRE");
		
		if(result==-1 || result>=1){//正常
			DynaModel entity=null;
			if(!"true".equals(useTemplate)){
				//保存业务数据
				entity=flowFormService.doSaveData(startInfo);
				if(entity!=null){
					//把业务数据也放至流程中
					startInfo.getVariables().putAll(entity.getDatas());
				}
			}
			//启动流程
			processRun=jbpmService.doStartProcess(startInfo);
			
			if("true".equals(useTemplate)){
			    startInfo.getVariables().putAll(BeanUtil.getMapFromRequest(request));
			}
			//保存后，把流程中相关的变量及数据全部提交至run_data表中，以方便后续的展示
			runDataService.saveFlowVars(processRun.getRunId(), startInfo.getVariables());
			
			//加上，以方便第三方业务读取流程相关的数据
			startInfo.setProcessRun(processRun);
			
			//发送邮件或短信通知相关人员
			notice(processRun,startInfo);
			
			if(entity!=null){
				//更新runId，通过runId，可以取到该审批业务的所有审批信息 
				try{
					entity.set("runId", processRun.getRunId());
					DynamicService service=BeanUtil.getDynamicServiceBean((String)entity.get(FlowRunInfo.ENTITY_NAME));
					service.save(entity.getDatas());
				}catch(Exception ex){
					ex.printStackTrace();
					logger.debug("error:" + ex.getMessage());
				}
			}
		}
		//若在提交参数中指定启动工作流后需要后处理
		invokeHandler(startInfo,"AFT");
		
		return processRun;
	}
	/**
	 * 完成任务，并且进入下一步
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public ProcessRun doNextFlow(HttpServletRequest request) throws Exception{
		
		String useTemplate=request.getParameter("useTemplate");
		
		FlowRunInfo nextInfo=getFlowRunInfo(request);
		
		ProcessRun processRun=null;
		//执行之前的动作
		int result=invokeHandler(nextInfo, "PRE");
		
		if(result==-1 || result>=1){//正常
			if(!"true".equals(useTemplate)){
				//保存业务数据
				DynaModel entity=flowFormService.doSaveData(nextInfo);
				if(entity!=null){
					//把业务数据也放至流程中
					nextInfo.getVariables().putAll(entity.getDatas());
				}
			}
			
			//保存流程数据
			processRun= jbpmService.doNextStep(nextInfo);
			
			if("true".equals(useTemplate)){
			    nextInfo.getVariables().putAll(BeanUtil.getMapFromRequest(request));
			}
    			//保存后，把流程中相关的变量及数据全部提交至run_data表中，以方便后续的展示
    			runDataService.saveFlowVars(processRun.getRunId(), nextInfo.getVariables());
			
			nextInfo.setProcessRun(processRun);
			//发送邮件或短信通知相关人员
			notice(processRun,nextInfo);
		}
		//执行之后的动作
		invokeHandler(nextInfo,"AFT");
		
		return processRun;
	}
	
	/**
	 * 使用邮件或短信通知相关的人员处理
	 * @param piId
	 */
	private void notice(ProcessRun processRun,FlowRunInfo flowInfo ){
		if(processRun.getPiId()==null) return;
		List<Task> taskList=jbpmService.getTasksByPiId(processRun.getPiId());
		
		for(Task task:taskList){
			TaskImpl taskImpl=(TaskImpl)task;
			if(taskImpl.getAssignee()==null){
				Iterator<ParticipationImpl> partIt= taskImpl.getAllParticipants().iterator();
				while(partIt.hasNext()){
					ParticipationImpl part=partIt.next();
					if(part.getGroupId()!=null && StringUtil.isNumeric(part.getGroupId())){
						//发送邮件
						List<AppUser> appUserList=appUserService.findByRoleId(new Long(part.getGroupId()));
						for(AppUser appUser:appUserList){
							sendMailNotice(processRun.getSubject(),taskImpl,appUser,flowInfo);
						}
					}else if(part.getUserId()!=null && StringUtil.isNumeric(part.getUserId())){
						AppUser appUser=appUserService.get(new Long(part.getUserId()));
						sendMailNotice(processRun.getSubject(),taskImpl,appUser,flowInfo);
					}
				}
			}else if(StringUtil.isNumeric(taskImpl.getAssignee())){				
				AppUser appUser=appUserService.get(new Long(taskImpl.getAssignee()));
				sendMailNotice(processRun.getSubject(),taskImpl,appUser,flowInfo);
			}
		}
		
	}

	/**
	 * 发送邮件及短信通知
	 * @param task
	 * @param appUser
	 */
	private void sendMailNotice(String piSubject,Task task,AppUser appUser,FlowRunInfo flowRunInfo){
		Date curDate=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String curDateStr=sdf.format(curDate);
		
		String shortContent= curDateStr + "待办事项(" + piSubject + ")提交至审批环节("+ task.getName() + ")，请及时审批。";
		shortMessageService.save(AppUser.SYSTEM_USER, appUser.getUserId().toString(), shortContent, ShortMessage.MSG_TYPE_TASK);
		
		if(flowRunInfo.isSendMail()){
			//发送邮件
			if(appUser.getEmail()!=null){
				if(logger.isDebugEnabled()){
					logger.info("Notice " + appUser.getFullname() + " by mail:" + appUser.getEmail());
				}
				String tempPath="mail/flowMail.vm";
				Map model=new HashMap();
				model.put("curDateStr", curDateStr);
				model.put("appUser", appUser);
				model.put("task", task);
				String subject="来自"+AppUtil.getCompanyName()+"办公系统的待办任务(" + piSubject + "--" + task.getName() + ")提醒";
				
				MailModel mailModel=new MailModel();
				mailModel.setMailTemplate(tempPath);
				mailModel.setTo(appUser.getEmail());
				mailModel.setSubject(subject);
				mailModel.setMailData(model);
				//把邮件加至发送列队中去
				mailMessageProducer.send(mailModel);
			}
		}
		if(flowRunInfo.isSendMsg()){
			//发送手机短信
			if(appUser.getMobile()!=null){
				if(logger.isDebugEnabled()){
					logger.info("Notice " + appUser.getFullname() + " by mobile:" + appUser.getMobile());
				}
				
				if(appUser.getMobile()!=null){
					String content=AppUtil.getCompanyName()+"办公系统于" + curDateStr + "产生了一项待办事项(" + piSubject + "--" + task.getName() + ")，请您在规定时间内完成审批~";
					SmsMobile smsMobile=new SmsMobile();
					smsMobile.setPhoneNumber(appUser.getMobile());
					smsMobile.setSmsContent(content);
					smsMobile.setSendTime(new Date());
					smsMobile.setUserId(-1l);
					smsMobile.setUserName("system user");
					smsMobile.setStatus(SmsMobile.STATUS_NOT_SENDED);
					
					smsMobileService.save(smsMobile);
					//放置发送队列
					mobileMessageProducer.send(smsMobile);
				}
			}
		}
	}
	
	/**
	 * 初始化一个新的流程
	 * @return
	 */
	public ProcessRun getInitNewProcessRun(HttpServletRequest request){
		String defId=request.getParameter("defId");
		ProDefinition proDefinition=proDefinitionService.get(new Long(defId));
		
		return processRunService.getInitNewProcessRun(proDefinition);
	}
	
	/**
	 * 取得流程运行的相关信息
	 */
	protected FlowRunInfo getFlowRunInfo(HttpServletRequest request) {
		FlowRunInfo info=new FlowRunInfo(request);
		//Map<String, ParamField> fieldMap=getConstructFieldMap(request);
		//info.setParamFields(fieldMap);
		return info;
	}

	/**
	 * 取得流程定义
	 * @return
	 */
	protected ProDefinition getProDefinition(HttpServletRequest request){
		ProDefinition proDefinition=null;
		String defId=request.getParameter("defId");
		if(defId!=null){
			 proDefinition=proDefinitionService.get(new Long(defId));
		}else {
			String taskId=request.getParameter("taskId");
			ProcessRun processRun=processRunService.getByTaskId(taskId.toString());
			proDefinition=processRun.getProDefinition();
		}
		return proDefinition;
	}
	
	/**
	 * 流程执行前后触发
	 * @param flowRunInfo
	 * @param preAfterMethodFlag 前后标识，PRE代表前置，AFT代表后置
	 * @return 0 代表失败 1代表成功，-1代表不需要执行
	 */
	public int invokeHandler(FlowRunInfo flowRunInfo,String preAfterMethodFlag) throws Exception{
		String handler=null;
		//前置方法
		if("PRE".equals(preAfterMethodFlag)){
			handler=flowRunInfo.getPreHandler();
		}else{//后置方法
			handler=flowRunInfo.getAfterHandler();
		}
		//没有指定方法
		if(handler==null) return -1;
			
		String [] beanMethods=handler.split("[.]");
		if(beanMethods!=null){
			String beanId=beanMethods[0];
			String method=beanMethods[1];
			//触发该Bean下的业务方法
			Object serviceBean=AppUtil.getBean(beanId);
			if(serviceBean!=null){
				Method invokeMethod=serviceBean.getClass().getDeclaredMethod(method, new Class[]{FlowRunInfo.class});
				return (Integer)invokeMethod.invoke(serviceBean, flowRunInfo);
			}
		}
		logger.error("error invoke handler " + handler);
		return 0;
	}
}
