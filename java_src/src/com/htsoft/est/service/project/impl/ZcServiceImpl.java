package com.htsoft.est.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.project.ZcDao;
import com.htsoft.est.model.jxjy.JxjyZc;
import com.htsoft.est.service.project.ZcService;

import flex.messaging.io.ArrayList;

public class ZcServiceImpl extends BaseServiceImpl<JxjyZc> implements ZcService{

	@Resource
	private ZcDao dao;
	
	public ZcServiceImpl(ZcDao dao) {
		super(dao);
		this.dao = dao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JxjyZc> getZcByParentId(Long pId) {
		// TODO Auto-generated method stub
		List<JxjyZc> allList = new ArrayList();
		List<JxjyZc> pList = this.dao.getZcByParentId(pId);
		for(JxjyZc zc : pList) {
			allList.add(zc);
			List<JxjyZc> cList = this.dao.getZcByParentId(zc.getZcId());
			allList.addAll(cList);
		}
		return allList;
	}

}
