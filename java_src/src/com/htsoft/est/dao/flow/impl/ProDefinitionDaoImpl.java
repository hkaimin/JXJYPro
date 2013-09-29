package com.htsoft.est.dao.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.flow.ProDefinitionDao;
import com.htsoft.est.model.flow.ProDefinition;
import com.htsoft.est.model.system.AppRole;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.service.system.GlobalTypeService;

public class ProDefinitionDaoImpl extends BaseDaoImpl<ProDefinition> implements ProDefinitionDao{

	@Resource
	private GlobalTypeService globalTypeService;
	
	public ProDefinitionDaoImpl() {
		super(ProDefinition.class);
	}
	
	public ProDefinition getByDeployId(String deployId){
		String hql="from ProDefinition pd where pd.deployId=?";
		return (ProDefinition)findUnique(hql, new Object[]{deployId});
	}
	
	public ProDefinition getByName(String name){
		String hql="from ProDefinition pd where pd.name=?";
		return (ProDefinition)findUnique(hql, new Object[]{name});
	}

	@Override
	public List<ProDefinition> getByRights(AppUser curUser, ProDefinition proDefinition,QueryFilter filter) {
		String uIds = "%,"+curUser.getUserId()+",%";
		String dIds = "%,"+curUser.getDepartment().getDepId()+",%";
		
		List params = new ArrayList();
		StringBuffer hql =new StringBuffer("select pd from ProDefRights pr right join pr.proDefinition pd  where 1=1 ");
		
		if(proDefinition!=null && proDefinition.getName()!=null){
			hql.append("and pd.name like = ?");
			params.add("%"+proDefinition.getName()+"%");
		}
		
		hql.append("and (pr.userIds like ?  or pr.depIds like ? ");
		params.add(uIds);
		params.add(dIds);
		
		Set<AppRole> roles = curUser.getRoles();
		for(Iterator it = roles.iterator();it.hasNext();){
			AppRole role = (AppRole)it.next();
			hql.append("or pr.roleIds like ? ");
			String rIds = "%,"+role.getRoleId()+",%";
			params.add(rIds);
		}
		
		hql.append(")");
				
		List<ProDefinition> result = findByHql(hql.toString(),params.toArray());
		filter.getPagingBean().setTotalItems(result.size());
		return result;
	}

	@Override
	public boolean checkNameByVo(ProDefinition proDefinition) {
		boolean idIsNull = false;
		if(proDefinition.getDefId() ==null){
			idIsNull = true;
		}
		StringBuffer hql = new StringBuffer("from ProDefinition pd where pd.name = ?");
		List params = new ArrayList();
		params.add(proDefinition.getName());
		if(!idIsNull){
			hql.append("and pd.defId != ?");
			params.add(proDefinition.getDefId());
		}
		List result = findByHql(hql.toString(), params.toArray());
		if(result.size()>0){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public boolean checkProcessNameByVo(ProDefinition proDefinition) {
		boolean idIsNull = false;
		if(proDefinition.getDefId() ==null){
			idIsNull = true;
		}
		StringBuffer hql = new StringBuffer("from ProDefinition pd where pd.processName = ?");
		List params = new ArrayList();
		params.add(proDefinition.getProcessName());
		if(!idIsNull){
			hql.append("and pd.defId != ?");
			params.add(proDefinition.getDefId());
		}
		List result = findByHql(hql.toString(), params.toArray());
		if(result.size()>0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 按Ids 来取到所有的列表
	 * @param docIds
	 * @return
	 */
	private List<ProDefinition> getByIds(List defIds){

		String docHql="from ProDefinition pd where pd.defId in (";
		StringBuffer sbIds=new StringBuffer();
		
		for(int i=0;i<defIds.size();i++){
			sbIds.append(defIds.get(i).toString()).append(",");
		}
		
		if(defIds.size()>0){
			sbIds.deleteCharAt(sbIds.length()-1);
			docHql+=sbIds.toString() + ")";
			return(List<ProDefinition>) findByHql(docHql);
		}else{
			return new ArrayList();
		}
	}
	@Override
	public List<ProDefinition> findRunningPro(ProDefinition proDefinition,
			Short runstate, PagingBean pb) {
		StringBuffer hql=new StringBuffer("select distinct pd.defId from ProcessRun pr join pr.proDefinition pd  where pr.runStatus=?");
		List params=new ArrayList();
		params.add(runstate);
		if(proDefinition!=null&&StringUtils.isNotEmpty(proDefinition.getName())){
			hql.append(" and pd.name like ?");
			params.add("%" +proDefinition.getName()+ "%");
		}
		hql.append(" order by pd.defId desc");
		
		List<Long> defIds=find(hql.toString(),params.toArray(), pb);
		
		return getByIds(defIds);
	}
}