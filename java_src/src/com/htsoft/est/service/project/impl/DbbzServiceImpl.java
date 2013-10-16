package com.htsoft.est.service.project.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jfree.util.Log;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.project.CreditDao;
import com.htsoft.est.dao.project.DbbzDao;
import com.htsoft.est.dao.project.DbryDao;
import com.htsoft.est.dao.system.AppUserDao;
import com.htsoft.est.model.jxjy.JxjyDbbz;
import com.htsoft.est.model.jxjy.JxjyDbry;
import com.htsoft.est.model.jxjy.JxjyXflb;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.project.DbbzService;

public class DbbzServiceImpl extends BaseServiceImpl<JxjyDbbz> implements DbbzService{

	@Resource
	private DbbzDao dao;
	@Resource
	private AppUserDao appUserDao;
	@Resource
	private CreditDao creditDao;
	@Resource
	private DbryDao dbryDao;
	
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

	@Override
	public void multiDel(String[] ids) {
		// TODO Auto-generated method stub
		for(String id : ids) {
			this.dao.remove(new Long(id));
		}
	}

	@Override
	public void checkPerson(String userNO, String yearNo, String flage) {
		// TODO Auto-generated method stub
		
		//注意，缺少根据年份加载相应的项目----------------------------------
		//Log.debug("注意，缺少根据年份加载相应的项目");
		
		AppUser user = this.appUserDao.getByFx(userNO);
		List<JxjyDbbz> bzList = this.dao.getBzByNf(yearNo, user.getUserId());
		Map<Long, JxjyDbbz> byMap = new HashMap<Long, JxjyDbbz>();
		Map<Long, JxjyDbbz> zgMap = new HashMap<Long, JxjyDbbz>();
		for(JxjyDbbz bz : bzList) {
			//必要条件
			if(bz.getTjlx().equals("1")) {
				if(byMap.get(bz.getKhnrId()) == null) {
					byMap.put(bz.getKhnrId(), bz);
				} else {
					Log.debug("重复：" + bz.getKhnr());
				}
			} else if(bz.getTjlx().equals("2")) {
				if(zgMap.get(bz.getKhnrId()) == null) {
					zgMap.put(bz.getKhnrId(), bz);
				} else {
					Log.debug("重复：" + bz.getKhnr());
				}
			}
		}
		
		boolean isPass = true;
		
		Set<Long> key = byMap.keySet();
		if(key.size() <=0) {
			Log.debug("还没设置标准");
		}
    	for (Iterator it = key.iterator(); it.hasNext();) {
    		
    		Long s = (Long) it.next();
    		JxjyDbbz bz = byMap.get(s);
    		
    		List<JxjyXflb> xflbList = this.creditDao.getLb(bz.getKhnrId());
    		
    		//计算大类别学分总和
    		Long sum = 0l;
    		for(JxjyXflb xflb : xflbList) {
    			Long xf = this.dao.getXfByLb(xflb.getXflbid(), user.getUserId(), yearNo, flage);
    			
    			logger.debug("算到" + xflb.getMc() + "：" + xf);
    			
    			//判断此学分类别是否有最高分限制
    			if(zgMap.get(xflb.getXflbid())  != null) {
    				Long zgf = zgMap.get(xflb.getXflbid()).getZgf();
    				if(zgf > xf) { //超过最高分
    					xf = zgf;
    				}
    			}
    			sum += xf;
    		}
    		
    		//判断大类别的总分是否达到标准
    		if(sum >= bz.getZgf()) {
    			isPass = true;
    		} else {  //不通过
    			isPass = false;
    		}
    		
    		//只要有一个不通过则为不同，直接返回，不判断下面的条件了
    		if(isPass == false) {
    			JxjyDbry ryPass = new JxjyDbry();
    			ryPass.setRybh(user.getFax());
    			ryPass.setDb("不达标");
    			ryPass.setXm(user.getFullname());
    			ryPass.setNf(yearNo);
    			ryPass.setType(flage);
    			this.dbryDao.saveOrInsert(ryPass);
    			logger.debug(user.getFamilyName() + "不达标");
    			return;
    		}
    	}
    	
    	//全部必要条件通过
    	if(isPass == true) {
    		JxjyDbry ryPass = new JxjyDbry();
			ryPass.setRybh(user.getFax());
			ryPass.setDb("达标");
			ryPass.setXm(user.getFullname());
			ryPass.setNf(yearNo);
			ryPass.setType(flage);
			this.dbryDao.saveOrInsert(ryPass);
			logger.debug(user.getFamilyName() + "达标");
    	}
	}

	@Override
	public void checkOrg(String orgId, String zc, String yearNo, String flage) {
		// TODO Auto-generated method stub
		List<AppUser> userList = this.dao.getUserByOrgAndZc(new Long(orgId), zc);
		for(AppUser user : userList) {
			this.checkPerson(user.getFax(), yearNo, flage);
		}
	}

}
