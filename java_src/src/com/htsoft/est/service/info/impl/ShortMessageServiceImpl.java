package com.htsoft.est.service.info.impl;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.Constants;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.info.InMessageDao;
import com.htsoft.est.dao.info.ShortMessageDao;
import com.htsoft.est.dao.system.AppUserDao;
import com.htsoft.est.model.info.InMessage;
import com.htsoft.est.model.info.ShortMessage;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.info.InMessageService;
import com.htsoft.est.service.info.ShortMessageService;

public class ShortMessageServiceImpl extends BaseServiceImpl<ShortMessage>
		implements ShortMessageService {
	private ShortMessageDao messageDao;
	@Resource
	private InMessageDao inMessageDao;
	@Resource
	private AppUserDao appUserDao;
	@Resource
	private InMessageService inMessageService;

	public ShortMessageServiceImpl(ShortMessageDao dao) {
		super(dao);
		this.messageDao = dao;
	}

	@Override
	public List<ShortMessage> findAll(Long userId, PagingBean pb) {
		return messageDao.findAll(userId, pb);
	}

	@Override
	public List<ShortMessage> findByUser(Long userId) {
		return messageDao.findByUser(userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShortMessage> searchShortMessage(Long userId, ShortMessage shortMessage,
			Date from, Date to, PagingBean pb, Short readFlag) {
		return messageDao.searchShortMessage(userId, shortMessage, from, to,
				pb, readFlag);
	}

	public ShortMessage save(Long senderId, String receiveIds, String content,Short msgType) {

		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setContent(content);
		shortMessage.setMsgType(msgType);
		AppUser curUser = appUserDao.get(senderId);
		shortMessage.setSender(curUser.getFullname());
		shortMessage.setSenderId(curUser.getUserId());
		shortMessage.setSendTime(new Date());

		messageDao.save(shortMessage);

		String[] reIds = receiveIds.split("[,]");
		if (reIds != null) {

			for (String userId : reIds) {
				InMessage inMsg = new InMessage();
				inMsg.setDelFlag(Constants.FLAG_UNDELETED);
				inMsg.setReadFlag(InMessage.FLAG_UNREAD);
				inMsg.setShortMessage(shortMessage);
				AppUser receiveUser = appUserDao.get(new Long(userId));

				inMsg.setUserId(receiveUser.getUserId());
				inMsg.setUserFullname(receiveUser.getFullname());
				inMessageDao.save(inMsg);
			}
		}

		return shortMessage;
	}

	@Override
	public void sendMesToUser(List<AppUser> users, String content) {
		// TODO Auto-generated method stub
		AppUser appUser = ContextUtil.getCurrentUser();
		for(AppUser user : users) {
			
			ShortMessage message=new ShortMessage();
			message.setContent(content);
			message.setMsgType((short)1);
			message.setSenderId(appUser.getUserId());
			message.setSender(appUser.getFullname());
			message.setSendTime(new Date());
			this.save(message);
			
			InMessage in=new InMessage();	
			in.setUserId(user.getUserId());
			in.setUserFullname(user.getFullname());
			in.setDelFlag(InMessage.FLAG_UNREAD);
			in.setReadFlag((short)0);
			in.setShortMessage(message);
			inMessageService.save(in);
		}
		
	}

}
