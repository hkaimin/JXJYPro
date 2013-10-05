package com.htsoft.est.service.ryxf.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.htsoft.core.service.impl.CommonServiceImpl;
import com.htsoft.core.util.ContextUtil;
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
	public List<JxjyXmgl> getXmgl() {
		
		AppUser user = ContextUtil.getCurrentUser();
		String sql =" select * from jxjy_xmgl t join jxjy_ktgl k on t.xm_id=k.xm_id ";
		String sql2 = " select t.kt_id from jxjy_ryxfgl t where t.rybh="+user.getUserId();//用户选课记录
		List<Map<String,Object>> listVo = this.getBasicDao().loadByForTransfReturnListMap(sql);
		List<Map<String,Object>> listKtid = this.getBasicDao().loadByForTransfReturnListMap(sql2);
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
	
}
