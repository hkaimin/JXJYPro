package com.htsoft.est.dao.document.impl;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.document.PaintTemplateDao;
import com.htsoft.est.model.document.PaintTemplate;

@SuppressWarnings("unchecked")
public class PaintTemplateDaoImpl extends BaseDaoImpl<PaintTemplate> implements
	PaintTemplateDao {

    public PaintTemplateDaoImpl() {
	super(PaintTemplate.class);
    }

    /**
     * 按Key查找模板
     * 
     * @param templateKey
     * @return
     */
    public List<PaintTemplate> getByKey(String templateKey) {
	String hql = "from PaintTemplate pt where pt.templateKey=?";
	return findByHql(hql, new Object[] { templateKey });
    }

    /**
     * 查找某个模板，除去id为eptTemplateId对应的记录
     * 
     * @param templateKey
     * @param eptTemplateId
     * @return
     */
    public List<PaintTemplate> getByKeyExceptId(String templateKey,Long eptTemplateId) {
	String hql = "from PaintTemplate pt where pt.templateKey=? and pt.ptemplateId!=?";
	return findByHql(hql, new Object[] { templateKey, eptTemplateId });
    }

}