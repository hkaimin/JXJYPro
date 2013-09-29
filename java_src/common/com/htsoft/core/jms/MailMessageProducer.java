package com.htsoft.core.jms;

import javax.annotation.Resource;
import javax.jms.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

import com.htsoft.core.model.MailModel;
/**
 * 用于发送邮件消息
 * @author csx
 *
 */
public class MailMessageProducer {
	
	private static final Log logger=LogFactory.getLog(MailMessageProducer.class);
	
	@Resource
	private Queue mailQueue;
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	public void send(MailModel mailModel){
		logger.debug("procduce the mail message");
		//产生邮件发送的消息，加到消息队列中去
		jmsTemplate.convertAndSend(mailQueue, mailModel);
	}
	
}
