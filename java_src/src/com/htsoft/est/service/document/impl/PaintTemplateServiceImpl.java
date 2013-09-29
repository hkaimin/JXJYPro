package com.htsoft.est.service.document.impl;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.document.PaintTemplateDao;
import com.htsoft.est.model.document.PaintTemplate;
import com.htsoft.est.service.document.PaintTemplateService;

public class PaintTemplateServiceImpl extends BaseServiceImpl<PaintTemplate>
	implements PaintTemplateService {
    @SuppressWarnings("unused")
    private PaintTemplateDao dao;

    public PaintTemplateServiceImpl(PaintTemplateDao dao) {
	super(dao);
	this.dao = dao;
    }

    /**
     * 查找某个模板，除去id为eptTemplateId对应的记录
     * 
     * @param templateKey
     * @param eptTemplateId
     * @return
     */
    public List<PaintTemplate> getByKeyExceptId(String templateKey,
	    Long eptTemplateId) {
	return dao.getByKeyExceptId(templateKey, eptTemplateId);
    }

    /**
     * 按Key查找模板
     * 
     * @param templateKey
     * @return
     */
    public List<PaintTemplate> getByKey(String templateKey) {
	return dao.getByKey(templateKey);
    }

    /**
     * 该Key是否在存
     * 
     * @param templateKey
     * @return
     */
    public boolean isKeyExist(String templateKey) {
	if (getByKey(templateKey).size() > 0) {
	    return true;
	}
	return false;
    }

}