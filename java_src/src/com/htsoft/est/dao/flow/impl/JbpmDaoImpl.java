package com.htsoft.est.dao.flow.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.FileCopyUtils;

import com.htsoft.est.dao.flow.JbpmDao;
/**
 * 
 * <B><P>EST-BPM -- http://www.jee-soft.cn</P></B>
 * <B><P>Copyright (C) 2008-2010 GuangZhou HongTian Software Company (广州宏天软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P>Jbpm相关表的底层读写操作类</P>
 * <P>product:est-bpm</P>
 * <P></P> 
 * @see com.htsoft.est.dao.flow.impl.JbpmDaoImpl
 * <P></P>
 * @author 
 * @version V1
 * @create: 2010-11-27下午04:51:37
 */
public class JbpmDaoImpl implements JbpmDao{
	
	private Log logger=LogFactory.getLog(JbpmDaoImpl.class);
	
	@Resource
	private JdbcTemplate jdbcTemplate;
//	@Resource
//	private HibernateTemplate hibernateTemplate;
	
	public JbpmDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 取得流程定义的XML
	 * @param deployId
	 * @return
	 */
	public String getDefXmlByDeployId(String deployId){
		String sql="select * from JBPM4_LOB where NAME_= ? and DEPLOYMENT_= ? ";
		
		final LobHandler lobHandler = new DefaultLobHandler();  // reusable object new OracleLobHandler(); //
		final ByteArrayOutputStream contentOs=new ByteArrayOutputStream();
		String defXml=null;
			try{ 
				jdbcTemplate.query(
					 sql, new Object[] {"process.jpdl.xml",deployId},
					 new AbstractLobStreamingResultSetExtractor() {
						 public void streamData(ResultSet rs) throws SQLException, IOException {
							 FileCopyUtils.copy(lobHandler.getBlobAsBinaryStream(rs, "BLOB_VALUE_"),contentOs);
						 }
					 }
			);
			defXml= new String(contentOs.toByteArray(),"UTF-8");
		}catch(Exception ex){
			logger.debug(ex.getMessage());
		}
		return defXml;
	}
	/**
	 * 把修改过的xml更新至回流程定义中
	 * @param deployId
	 * @param defXml
	 */
	public void wirteDefXml(final String deployId,String defXml){
		try{
			LobHandler lobHandler=new DefaultLobHandler();
			final byte[] btyesXml=defXml.getBytes("UTF-8");
			String sql="update JBPM4_LOB set BLOB_VALUE_=? where NAME_= ? and DEPLOYMENT_= ? ";
			jdbcTemplate.execute(sql,new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				@Override
				protected void setValues(PreparedStatement ps, LobCreator lobCreator)
						throws SQLException, DataAccessException {
					lobCreator.setBlobAsBytes(ps, 1, btyesXml);
					ps.setString(2, "process.jpdl.xml");
					ps.setString(3, deployId);
				}
			});
		}catch(Exception ex){
			
		}
	}
//	/**
//	 * 更新 Task DueDate
//	 * @param taskId
//	 * @param dueDate
//	 */
//	public void updateDueDate(Long taskId,Date dueDate){
//		String sql="update JBPM4_TASK set DUEDATE_=? where DBID_=?";
//		jdbcTemplate.update(sql, new Object[]{dueDate,taskId});
//	}
	
}
