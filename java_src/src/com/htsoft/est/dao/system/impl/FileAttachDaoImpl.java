package com.htsoft.est.dao.system.impl;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.system.FileAttachDao;
import com.htsoft.est.model.system.FileAttach;

@SuppressWarnings("unchecked")
public class FileAttachDaoImpl extends BaseDaoImpl<FileAttach> implements
		FileAttachDao {

	public FileAttachDaoImpl() {
		super(FileAttach.class);
	}

	@Override
	public void removeByPath(final String filePath) {
		final String hql = "delete from FileAttach fa where fa.filePath = ?";
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString(0, filePath);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 按文件路径取得路径
	 * 
	 * @param filePath
	 */
	public FileAttach getByPath(String filePath) {
		String hql = "from FileAttach fa where fa.filePath = ?";
		return (FileAttach) findUnique(hql, new Object[] { filePath });
	}

	/**
	 * @description 分页查询附件信息,备注：图片格式[bmp,png,jpg,gif,tiff]
	 * @param pb
	 *            PagingBean
	 * @param bo
	 *            boolean,true:file文件,false:image图片文件
	 * @return List <FileAttach>
	 */
	@Override
	public List<FileAttach> fileList(PagingBean pb, String fileType, boolean bo) {
		Long userId = ContextUtil.getCurrentUser().getUserId();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(userId);
		String hql = "select f from FileAttach f where (f.delFlag = 0 or f.delFlag is null) and f.creatorId = ? ";
		if (fileType != null && !fileType.equals("")) {
			hql += " and f.fileType like ? ";
			paramList.add(fileType + "%");
		}
		if (!bo) // 未image图片文件
			hql += "and f.ext in('jpg','gif','jpeg','png','bmp','JPG','GIF','JPEG','PNG','BPM') ";
		hql += "order by f.createtime DESC ";
		logger.debug("FileAttach：" + hql);
		return findByHql(hql, paramList.toArray(), pb);
	}

	/**
	 * 查询所有满足fileType条件的数据
	 */
	@Override
	public List<FileAttach> fileList(String fileType) {
		Long userId = ContextUtil.getCurrentUser().getUserId();
		ArrayList<Object> paramList = new ArrayList<Object>();
		String hql = "select f from FileAttach f where (f.delFlag =0 or f.delFlag is null) and f.creatorId = ? and ";
		paramList.add(userId);
		if (!fileType.isEmpty()) {
			hql += "f.fileType like ? ";
			paramList.add(fileType);
		}
		hql += "order by f.createtime DESC ";
		logger.debug(hql);
		return findByHql(hql, paramList.toArray());
	}

	/**
	 * 附件删除操作，不删除file_attach表中的数据，只修改delFlage为1,然后删除对应的物理文件
	 */
	@Override
	public void remove(Long arg0) {
		FileAttach fileAttach = get(arg0);
		fileAttach.setDelFlag(1);
		save(fileAttach);
		// 刪除物理文件
		File file = new File(fileAttach.getFilePath());
		if (file.exists()) {
			file.delete();
		}
	}
}