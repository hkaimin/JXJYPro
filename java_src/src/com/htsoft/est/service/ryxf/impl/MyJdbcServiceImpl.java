package com.htsoft.est.service.ryxf.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.impl.CommonServiceImpl;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.est.model.jxjy.JxjySfbzmx;
import com.htsoft.est.model.jxjy.JxjySfbzsz;
import com.htsoft.est.model.jxjy.JxjyXmgl;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.ryxf.MyJdbcService;

public class MyJdbcServiceImpl extends CommonServiceImpl implements MyJdbcService{

	protected Log logger=LogFactory.getLog(MyJdbcServiceImpl.class);
	public SimpleJdbcTemplate sjt;
	public DataSource dataSource;
	
	@Override
	public DataSource getDataSource() {
		return dataSource; 
	}
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		sjt = new SimpleJdbcTemplate(this.getDataSource());
	}
	@Override
	public SimpleJdbcTemplate getSjt() {
		return sjt;
	}
	@Override
	public List<JxjyXmgl> getXmgl(QueryFilter filter) {
		Object[] params = new Object[]{};
		AppUser user = ContextUtil.getCurrentUser();
		String sql =" select * from jxjy_xmgl t join jxjy_ktgl k on t.xm_id=k.xm_id  ";
		String sql2 = " select t.kt_id from jxjy_ryxfgl t where t.rybh="+user.getUserId() ;//用户选课记录
		List<Map<String,Object>> listVo = this.getBasicDao().loadByForTransfReturnListMap(sql, filter.getPagingBean().getFirstResult(), filter.getPagingBean().getPageSize());
		List<Map<String,Object>> listKtid = this.getBasicDao().loadByForTransfReturnListMap(sql2);

		List<Map<String,Object>> listCount = this.getBasicDao().loadByForTransfReturnListMap(sql);
		filter.getPagingBean().setTotalItems(listCount.size());
		List<JxjyXmgl> xmglVo = new ArrayList<JxjyXmgl>();
		for(Map map : listVo){
			JxjyXmgl xmgl = new JxjyXmgl();
			xmgl.setXmId(new Long((BigDecimal)map.get("XM_ID")+""));
			xmgl.setXmmc((String)map.get("XMMC"));
			xmgl.setXflb((String)map.get("XFLB"));
			xmgl.setHdfs((String)map.get("HDFS"));
			xmgl.setZbdw((String)map.get("ZBDW"));
			xmgl.setKtidVo(new Long((BigDecimal)map.get("KT_ID")+""));
			xmgl.setKtmcVo((String)map.get("KTMC"));
			xmgl.setXfVo((String)map.get("XF"));
			xmgl.setXsVo((String)map.get("XS"));
			xmgl.setSkddVo((String)map.get("SKDD"));
			xmgl.setSksjVo((String)map.get("SKSJ"));
			String bmqk = "未报名";
			if(listKtid.size()>0){
                for(Map mapKt : listKtid){
                	if(((BigDecimal)map.get("KT_ID")+"").equals((BigDecimal)mapKt.get("KT_ID")+"")){
                		bmqk = "已报名";
                		break;
                	}
                }			
			}
			xmgl.setBmqkVo(bmqk);
			xmglVo.add(xmgl);
		}
		return xmglVo;
		
	}
	@Override
	public List<Map<String, Object>> getXflb() {
		String sql = " select t.xflbid,t.mc from jxjy_xflb t ";
		return this.getBasicDao().loadByForTransfReturnListMap(sql);
	}
	
	@Override
	public List<Map<String, Object>> getRyxfByKtid(Long userid, Long ktid) {
		String sql = " select t.id from jxjy_ryxfgl t where t.rybh="+userid+" and t.kt_id="+ktid;
		return this.getBasicDao().loadByForTransfReturnListMap(sql);
	}
	@Override
	public List<JxjySfbzsz> getSfbz(QueryFilter filter) {
		String sql = " select t.id,t.xflbid,t.mx,t.xmmc,l.mc from jxjy_sfbzsz t join jxjy_xflb l on t.xflbid=l.xflbid ";
		List<Map<String,Object>> list = this.getBasicDao().loadByForTransfReturnListMap(sql, filter.getPagingBean().getFirstResult(), filter.getPagingBean().getPageSize());
		List<JxjySfbzsz> listSfbz = new ArrayList<JxjySfbzsz>();
		List<Map<String,Object>> listCount = this.getBasicDao().loadByForTransfReturnListMap(sql);
		filter.getPagingBean().setTotalItems(listCount.size());
		
		for(Map map : list){
			JxjySfbzsz jxjySfbzsz = new JxjySfbzsz();
			jxjySfbzsz.setId(new Long((BigDecimal)map.get("ID")+""));
			jxjySfbzsz.setMx((String)map.get("MX"));
			jxjySfbzsz.setXmmc((String)map.get("XMMC"));
			jxjySfbzsz.setLbmcVo((String)map.get("MC"));
			listSfbz.add(jxjySfbzsz);
		}
		return listSfbz;
	}
	@Override
	public List<JxjySfbzmx> getSfbzmx(Long sfbzszid) {
		
		String sql = " select t.*,z.mx,z.xmmc from jxjy_sfbzmx t join jxjy_sfbzsz z on t.sfbzszid=z.id and z.id="+sfbzszid ;
		List<Map<String,Object>> list = this.getBasicDao().loadByForTransfReturnListMap(sql);
		List<JxjySfbzmx> listSfbzmx =new ArrayList<JxjySfbzmx>();
		for(Map map : list){
			JxjySfbzmx jxjySfbzmx = new JxjySfbzmx();
			jxjySfbzmx.setId(new Long((BigDecimal)map.get("ID")+""));
			jxjySfbzmx.setXmmc((String)map.get("XMMC"));
			jxjySfbzmx.setSz(new Long((BigDecimal)map.get("SZ")+""));
			jxjySfbzmx.setDw((String)map.get("DW"));
			jxjySfbzmx.setXf(new Long((BigDecimal)map.get("XF")+""));
			jxjySfbzmx.setZgz(new Long((BigDecimal)map.get("ZGZ")+""));
			listSfbzmx.add(jxjySfbzmx);
		}
		return listSfbzmx;
	}
	
}
