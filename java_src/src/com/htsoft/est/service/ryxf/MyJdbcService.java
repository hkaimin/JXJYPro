package com.htsoft.est.service.ryxf;

import java.util.List;
import java.util.Map;

import com.htsoft.core.service.impl.CommonService;
import com.htsoft.est.model.jxjy.JxjyXmgl;

public interface MyJdbcService extends CommonService{
    public List<JxjyXmgl> getXmgl();
    /**
     * 获取学分类别
     */
    public List<Map<String,Object>> getXflb();
}
