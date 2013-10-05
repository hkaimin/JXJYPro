package com.htsoft.est.service.project.impl;

import java.util.List;

import javax.annotation.Resource;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.project.CreditDao;
import com.htsoft.est.model.jxjy.JxjyXflb;
import com.htsoft.est.service.project.CreditService;
import com.htsoft.est.util.JxjyConstant;

import flex.messaging.io.ArrayList;

public class CreditServiceImpl extends BaseServiceImpl<JxjyXflb> implements CreditService{

	@Resource
	private CreditDao dao;
	
	public CreditServiceImpl(CreditDao dao) {
		super(dao);
		this.dao = dao;
		// TODO Auto-generated constructor stub
	}

	@Override
	public JxjyXflb saveLb(JxjyXflb lb) {
		// TODO Auto-generated method stub
		//新建
		if(lb.getXflbid() == null) {
			JxjyXflb temp = this.dao.save(lb);
			return temp;
		} else { //修改
			JxjyXflb temp = this.dao.get(lb.getXflbid());
			try {
				BeanUtil.copyNotNullProperties(temp, lb);
				return dao.save(temp);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				return null;
			}
		}
	}

	@Override
	public List<JxjyXflb> loadAll() {
		// TODO Auto-generated method stub
		List<JxjyXflb> allList = new ArrayList();
		JxjyXflb oneLb = new JxjyXflb();
		oneLb.setMc("I类学分");
		allList.add(oneLb);
		
		List<JxjyXflb> list = this.dao.getLb(JxjyConstant.XUE_FEN_YI_LEI);
		
		allList.addAll(list);
		
		JxjyXflb twoLb = new JxjyXflb();
		twoLb.setMc("II类学分");
		allList.add(twoLb);
		
		List<JxjyXflb> list2 = this.dao.getLb(JxjyConstant.XUE_FEN_ER_LEI);
		
		allList.addAll(list2);
		
		JxjyXflb otherLb = new JxjyXflb();
		otherLb.setMc("其他学分");
		allList.add(otherLb);
		
		List<JxjyXflb> list3 = this.dao.getLb(JxjyConstant.XUE_FEN_QI_TA);
		
		allList.addAll(list3);
		
		return allList;
	}

	@Override
	public void batchModified(String[] ids, String flage) {
		// TODO Auto-generated method stub
		for(String id : ids) {
			JxjyXflb lb = this.dao.get(new Long(id));
			lb.setSfsh(flage);
			this.dao.save(lb);
		}
	}

	@Override
	public void multiDel(String[] ids) {
		// TODO Auto-generated method stub
		for(String id : ids){
			this.dao.remove(new Long(id));
		}
	}

}
