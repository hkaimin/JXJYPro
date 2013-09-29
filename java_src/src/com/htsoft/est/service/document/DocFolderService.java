package com.htsoft.est.service.document;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.document.DocFolder;

public interface DocFolderService extends BaseService<DocFolder>{
	/**
	 * 按父Id取得某目录下的所有子文件夹
	 * @param userId
	 * @param parentId
	 * @return
	 */
	public List<DocFolder> getUserFolderByParentId(Long userId,Long parentId);
	
	/**
	 * 取得某path下的所有Folder
	 * @param path
	 * @return
	 */
	public List<DocFolder> getFolderLikePath(String path);
	/**
	 * 获取公共文件夹
	 * @param userId
	 * @param parentId
	 * @return
	 */
	public List<DocFolder> getPublicFolderByParentId(Long parentId);
	/**
	 * 查找目录下的子目录 
	 */
	public List<DocFolder> findByParentId(Long parentId);
	/**
	 * 根据用户和名字查找
	 */
	public List<DocFolder> findByUserAndName(Long userId,String foleName);
	/**
	 * 根据父节点查找在线文件夹
	 */
	public List<DocFolder> getOnlineFolderByParentId(Long parentId);
}


