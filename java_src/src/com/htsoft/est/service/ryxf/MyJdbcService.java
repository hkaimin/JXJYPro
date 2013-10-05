package com.htsoft.est.service.ryxf;

import java.util.List;
import java.util.Map;

import com.htsoft.core.service.impl.CommonService;
import com.htsoft.est.model.jxjy.JxjySfbzmx;
import com.htsoft.est.model.jxjy.JxjySfbzsz;
import com.htsoft.est.model.jxjy.JxjyXmgl;

public interface MyJdbcService extends CommonService{
	
    public List<JxjyXmgl> getXmgl();
    /**
     * 获取学分类别
     */
    public List<Map<String,Object>> getXflb();
    /**
     * 获取人员信息表-byktid 
     */
    public List<Map<String,Object>> getRyxfByKtid(Long userid,Long ktid);
    /**
     * 获取授分标准设置
     */
    public List<JxjySfbzsz> getSfbz();
    public List<JxjySfbzmx> getSfbzmx(Long sfbzszid);
}
