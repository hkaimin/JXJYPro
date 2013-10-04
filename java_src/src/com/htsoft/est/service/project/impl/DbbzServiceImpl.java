package com.htsoft.est.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.project.DbbzDao;
import com.htsoft.est.model.jxjy.JxjyDbbz;
import com.htsoft.est.service.project.DbbzService;

public class DbbzServiceImpl extends BaseServiceImpl<JxjyDbbz> implements DbbzService{

	@Resource
	private DbbzDao dao;
	
	public DbbzServiceImpl(DbbzDao dao) {
		super(dao);
		this.dao = dao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public JxjyDbbz saveBiaozhun(JxjyDbbz bz) {
		// TODO Auto-generated method stub
		//新建
		if(bz.getId() == null) {
			if(bz.getTjlx().equals("1")) {
				String str = bz.getKhnr() + "达到" + bz.getZgf() + "分";
				bz.setTjms(str);
			} else if(bz.getTjlx().equals("2")) {
				String str = bz.getKhnr() + "最高" + bz.getZgf() + "分";
				bz.setTjms(str);
			}
			JxjyDbbz temp = this.dao.save(bz);
			return temp;
		} else { //修改
			JxjyDbbz temp = this.dao.get(bz.getId());
			try {
				BeanUtil.copyNotNullProperties(temp, bz);
				if(bz.getTjlx().equals("1")) {
					String str = bz.getKhnr() + "达到" + bz.getZgf() + "分";
					bz.setTjms(str);
				} else if(bz.getTjlx().equals("2")) {
					String str = bz.getKhnr() + "最高" + bz.getZgf() + "分";
					bz.setTjms(str);
				}
				return dao.save(temp);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				return null;
			}
		}
	}

	@Override
	public List<JxjyDbbz> list(QueryFilter filter) {
		// TODO Auto-generated method stub
		return this.dao.listBz(filter);
	}

}
