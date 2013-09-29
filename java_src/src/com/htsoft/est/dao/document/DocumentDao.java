package com.htsoft.est.dao.document;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.document.Document;
import com.htsoft.est.model.system.AppUser;

/**
 * 
 * @author 
 *
 */
public interface DocumentDao extends BaseDao<Document>{
	
	/**
	 * 查找共享的文档
	 * @param document
	 * @param from
	 * @param to
	 * @param userId
	 * @param roleIds
	 * @param depId
	 * @param pb
	 * @return
	 */
	public List<Document> findByIsShared(Document document,Date from,Date to,Long userId,ArrayList<Long> roleIds,Long depId,PagingBean pb);
	/**
	 * 查找公共文档
	 * @param folderId
	 * @param document
	 * @param from
	 * @param to
	 * @param appUser
	 * @param pb
	 * @return
	 */
	public List<Document> findByPublic(String path,Document document,Date from,Date to,AppUser appUser,PagingBean pb);
	/**
	 * 根据文件夹个人查找个人文档
	 */
	public List<Document> findByPersonal(Long userId,Document document,Date from,Date to,String path,PagingBean pb);
	/**
	 * 根据文件夹查找文档下面所有层的文档
	 */
	public List<Document> findByFolder(String path);
	/**
	 * 根据当前用户查找他可以看的文档，包括个人文档，共享文档，公共文档。
	 */
	public List<Document> searchDocument(AppUser appUser,String content,boolean isHaveData,PagingBean pb);
	
	/**
	 * 查找某个文件夹的文档
	 */
	public List<Document> findByFolder(Long folderId);
	/**
	 * 根据文件夹个人查找个人文档
	 */
	public List<Document> findByPersonal(Long userId,Document document,Date from,Date to,String path);
	/**
	 * 查找在线文档
	 * @param doc
	 * @param from
	 * @param to
	 * @return
	 */
	public List<Document> findByOnline(Document doc,Date from,Date to,AppUser user);
}