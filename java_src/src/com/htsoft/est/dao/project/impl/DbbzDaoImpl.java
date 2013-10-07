package com.htsoft.est.dao.project.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.BasicDao;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.project.DbbzDao;
import com.htsoft.est.model.jxjy.JxjyDbbz;
import com.htsoft.est.model.jxjy.JxjyRyxfgl;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.util.StringUtil;

import flex.messaging.io.ArrayList;

public class DbbzDaoImpl extends BaseDaoImpl<JxjyDbbz> implements DbbzDao{

	@Resource
	private BasicDao basicDao;
	@Resource
	private JdbcTemplate jdbcTemplate;
	
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

	@Override
	public List<JxjyDbbz> getBzByNf(String nf, Long userId) {
		// TODO Auto-generated method stub
		
		StringBuffer sql = new StringBuffer("select bz.* from app_user u, organization o, user_org uo, jxjy_dbbz bz where u.userid=uo.userid and o.org_id=uo.org_id and u.tinfo=bz.zc and o.org_type=bz.yylx and u.userid=" + userId + " and nf='" + nf + "'");
		
		logger.debug("sql:" + sql);
		List<Map<String, Object>> list = this.basicDao.loadByForTransfReturnListMap(sql.toString());
		
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

	@Override
	public Long getXfByLb(Long xflbId, Long userId, String nf) {
		// TODO Auto-generated method stub
		
		String startTime = nf + "-01-01";
		String endTime = nf + "-12-30";
		
		StringBuffer sql = new StringBuffer("select sum(xf.xf) sum from jxjy_xflb lb, jxjy_xmgl xm, jxjy_ktgl kt, jxjy_ryxfgl xf where lb.xflbid=xm.xflbid and xm.xm_id=kt.xm_id and kt.kt_id=xf.kt_id and xm.jbsj >= '" + startTime + "' and xm.jbsj <= '" + endTime + "' and xf.rybh=" + userId + " and xm.xflbid=" + xflbId);
		
		logger.debug("sql:" + sql);
		
		Long total = (Long) this.jdbcTemplate.queryForLong(sql.toString());		
		
		return total;
	}

	@Override
	public List<AppUser> getUserByOrgAndZc(Long orgId, String zc) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from app_user u, organization o, user_org uo where u.userid=uo.userid and uo.org_id=o.org_id and u.tinfo=" + zc + " and o.org_id=" + orgId);
		
		logger.debug("sql:" + sql);
		List<Map<String, Object>> list = this.basicDao.loadByForTransfReturnListMap(sql.toString());
		
		List<AppUser> userList = new ArrayList();
		
		for(Map<String, Object> map : list) {
			
			AppUser user = new AppUser();
			
			//加载人员的人员编号
			user.setUserId(StringUtil.o2l(map.get("USERID")));
			user.setFax(StringUtil.o2s(map.get("FAX")));
			
			userList.add(user);
		}
		
		return userList;
	}

}
