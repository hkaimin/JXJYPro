package com.htsoft.est.dao.project.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.BasicDao;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.DbbzDao;
import com.htsoft.est.model.jxjy.JxjyDbbz;
import com.htsoft.est.util.StringUtil;

import flex.messaging.io.ArrayList;

public class DbbzDaoImpl extends BaseDaoImpl<JxjyDbbz> implements DbbzDao{

	@Resource
	private BasicDao basicDao;
	
	public DbbzDaoImpl() {
		super(JxjyDbbz.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<JxjyDbbz> listBz(QueryFilter filter) {
		// TODO Auto-generated method stub
		String nf = filter.getRequest().getParameter("nf");
		if(nf == null ) {
			nf = "2013";
		}
		StringBuffer sql = new StringBuffer("select bz.*, zc.zcm from jxjy_dbbz bz, jxjy_zc zc where bz.zc=zc.zc_id and bz.nf = '" + nf + "'");
		StringBuffer countSql = new StringBuffer("select count(bz.id) from jxjy_dbbz bz, jxjy_zc zc where bz.zc=zc.zc_id and bz.nf = '" + nf + "'");
		
		
		int total = ((BigDecimal)this.basicDao.queryTotalCntBySql(countSql.toString())).intValue();
		filter.getPagingBean().setTotalItems(total);
		
		logger.debug("sql:" + sql);
		List<Map<String, Object>> list = this.basicDao.loadByForTransfReturnListMap(sql.toString(), filter.getPagingBean().getFirstResult(), filter.getPagingBean().getPageSize());
		
		List<JxjyDbbz> bzList = new ArrayList();
		
		for(Map<String, Object> map : list) {
			
			JxjyDbbz bz = new JxjyDbbz();
			
			bz.setId(StringUtil.o2l(map.get("ID")));
			bz.setYylx(StringUtil.o2s(map.get("YYLX")));
			
			
			bz.setZc(StringUtil.o2s(map.get("ZCM")));
			
			
			bz.setKhnr(StringUtil.o2s(map.get("KHNR")));
			bz.setXfz(StringUtil.o2l(map.get("XFZ")));
			bz.setZgf(StringUtil.o2l(map.get("ZGF")));
			bz.setTjms(StringUtil.o2s(map.get("TJMS")));
			bz.setKhnrId(StringUtil.o2l(map.get("KHNR_ID")));
			bz.setTjlx(StringUtil.o2s(map.get("TJLX")));
			bz.setNf(StringUtil.o2s(map.get("NF")));
			
			bzList.add(bz);
		}
		
		return bzList;
	}

}
