package com.htsoft.est.service.communicate.impl;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
 */
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.smslib.OutboundMessage;
import org.smslib.Message.MessageEncodings;

import com.htsoft.core.jms.MobileMessageProducer;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.AppUtil;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.core.gms.GmsUtil;
import com.htsoft.est.dao.communicate.SmsMobileDao;
import com.htsoft.est.model.communicate.SmsHistory;
import com.htsoft.est.model.communicate.SmsMobile;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.SysConfig;
import com.htsoft.est.service.communicate.SmsHistoryService;
import com.htsoft.est.service.communicate.SmsMobileService;
import com.htsoft.est.service.system.AppUserService;
import com.htsoft.est.service.system.SysConfigService;
import com.sms.soap.SendStatus;
import com.sms.soap.ServicesSoap;

public class SmsMobileServiceImpl extends BaseServiceImpl<SmsMobile> implements
		SmsMobileService {
	@Resource
	private MobileMessageProducer mobileMessageProducer;
	
	@Resource
	private AppUserService appUserService;

	@Resource
	private SmsMobileService smsMobileService;

	@Resource
	private SmsHistoryService smsHistoryService;
	
	@Resource
	private SysConfigService sysConfigService;
	private SmsMobileDao dao;

	public SmsMobileServiceImpl(SmsMobileDao dao) {
		super(dao);
		this.dao = dao;
	}
	
	@Override
	public SmsMobile save(SmsMobile entity) {
		dao.save(entity);
		mobileMessageProducer.send(entity);
		return entity;
	}

	@Override
	public void saveSms(String userIds, String content) {
		// 号码序列,用","分隔

		if (StringUtils.isNotEmpty(userIds)) {
			String[] ids = userIds.split(",");
			for (String id : ids) {
				AppUser recipients = appUserService.get(new Long(id));

				SmsMobile smsMobile = new SmsMobile();
				smsMobile.setPhoneNumber(recipients.getMobile());
				smsMobile.setSendTime(new Date());
				smsMobile.setRecipients(recipients.getFullname());
				smsMobile.setSmsContent(content);
				smsMobile.setUserId(AppUser.SYSTEM_USER);
				smsMobile.setUserName("系统");
				smsMobile.setStatus(SmsMobile.STATUS_NOT_SENDED);

				smsMobileService.save(smsMobile);
			}
		}
	}

	@Override
	public void sendSms() {
		// TODO Auto-generated method stub
		List<SmsMobile> list = smsMobileService.getNeedToSend();
		send(list);
	}

	public void send(List<SmsMobile> list){
		//是否用短信端口
		boolean smsPort = AppUtil.getSmsPort();
		
		if(smsPort){
			SysConfig uri = sysConfigService.findByKey("smsPortUri");
			SysConfig userID = sysConfigService.findByKey("smsPortUserID");
			SysConfig account = sysConfigService.findByKey("smsPortAccount");
			SysConfig pwd = sysConfigService.findByKey("smsPortPwd");
			SendStatus status =null;
			for(SmsMobile smsMobile : list){
				status = DirectSend(uri.getDataValue(),
						userID.getDataValue(), account.getDataValue(), pwd.getDataValue()
						,smsMobile.getPhoneNumber(),smsMobile.getSmsContent(), "", 1, "",1);
				if(status.getRetCode().equals("Sucess")){
					smsMobile.setStatus(SmsMobile.STATUS_SENDED);
					
					SmsHistory smsHistory = new SmsHistory();
					try {
						BeanUtil.copyNotNullProperties(smsHistory, smsMobile);
						smsHistory.setSmsId(null);
						smsHistoryService.merge(smsHistory);
						
						smsMobileService.remove(smsMobile.getSmsId());
					} catch (Exception e) {
						logger.error(e);
						//e.printStackTrace();
					}
				}else{
					logger.debug("======Send sms failure!please check the setting of the sms port .===");
				}
			}
		}else{
			OutboundMessage msg;
			org.smslib.Service gmsService = GmsUtil.getGmsService();
			//System.out.println("初始化成功，准备开启服务");
			if(list.size()>0){
				try {
					
					gmsService.startService();
					//System.out.println("服务启动成功");
						for(SmsMobile smsMobile : list){
							msg = new OutboundMessage(smsMobile.getPhoneNumber(), smsMobile.getSmsContent());
							msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
							gmsService.sendMessage(msg);
							smsMobile.setStatus(SmsMobile.STATUS_SENDED);
							
							SmsHistory smsHistory = new SmsHistory();
							BeanUtil.copyNotNullProperties(smsHistory, smsMobile);
							smsHistory.setSmsId(null);
							smsHistoryService.merge(smsHistory);
							
							smsMobileService.remove(smsMobile.getSmsId());
							
							
						}
					gmsService.stopService();
				} catch (Exception e) {
					logger.error(e);
					//e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public List<SmsMobile> getNeedToSend() {
		return dao.getNeedToSend();
	}

	@Override
	public void sendOneSms(SmsMobile smsMobile) {
		//是否用短信端口
		boolean smsPort = AppUtil.getSmsPort();
		
		if(smsPort){
			SysConfig uri = sysConfigService.findByKey("smsPortUri");
			SysConfig userID = sysConfigService.findByKey("smsPortUserID");
			SysConfig account = sysConfigService.findByKey("smsPortAccount");
			SysConfig pwd = sysConfigService.findByKey("smsPortPwd");
			SendStatus status =null;
				status = DirectSend(uri.getDataValue(),
						userID.getDataValue(), account.getDataValue(), pwd.getDataValue()
						,smsMobile.getPhoneNumber(),smsMobile.getSmsContent(), "", 1, "",1);
				if(status.getRetCode().equals("success")){
					smsMobile.setStatus(SmsMobile.STATUS_SENDED);
					
					SmsHistory smsHistory = new SmsHistory();
					try {
						BeanUtil.copyNotNullProperties(smsHistory, smsMobile);
						smsHistory.setSmsId(null);
						smsHistoryService.merge(smsHistory);
						
						smsMobileService.remove(smsMobile.getSmsId());
					} catch (Exception e) {
						logger.error(e);
						//e.printStackTrace();
					}
				}else{
					logger.debug("======Send sms failure!please check the setting of the sms port .===");
				}
		}else{
			try {
				OutboundMessage msg;
				org.smslib.Service gmsService = GmsUtil.getGmsService();
				gmsService.startService();
				//System.out.println("服务启动成功");
				msg = new OutboundMessage(smsMobile.getPhoneNumber(), smsMobile.getSmsContent());
				msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
				gmsService.sendMessage(msg);
				smsMobile.setStatus(SmsMobile.STATUS_SENDED);
				
				SmsHistory smsHistory = new SmsHistory();
				BeanUtil.copyNotNullProperties(smsHistory, smsMobile);
				smsHistory.setSmsId(null);
				smsHistoryService.merge(smsHistory);
				
				smsMobileService.remove(smsMobile.getSmsId());
			
				gmsService.stopService();
			} catch (Exception e) {
				logger.error(e);
				//e.printStackTrace();
			}
		}
		
	}
	
	public SendStatus DirectSend(String url,String userID,String account,String password,
			String phones,String content,String sendTime,int sendType,String postFixNumber,int sign)
	{
		SendStatus status = null;
		org.codehaus.xfire.service.Service serviceModel = new ObjectServiceFactory().create(ServicesSoap.class);
		ServicesSoap service = null;

		try{
			service = (ServicesSoap)new XFireProxyFactory().create(serviceModel, url);
			status = service.directSend(userID, account, password, phones, content, sendTime, sendType, postFixNumber,sign);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
		return status;
	}
}