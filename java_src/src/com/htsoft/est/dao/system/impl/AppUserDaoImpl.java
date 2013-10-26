package com.htsoft.est.dao.system.impl;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.htsoft.core.Constants;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.util.ContextUtil;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.system.AppUserDao;
import com.htsoft.est.model.system.AppUser;
import com.htsoft.est.model.system.Department;
import com.htsoft.est.model.system.Organization;
import com.htsoft.est.model.system.Position;
import com.htsoft.est.model.system.PositionSub;
import com.htsoft.est.model.system.RelativeUser;
import com.htsoft.est.model.system.UserOrg;
import com.htsoft.est.model.system.UserPosition;

/**
 * @description 用户信息管理
 * @class AppUserDaoImpl
 * @author 宏天软件
 * @updater YHZ
 * @company www.jee-soft.cn
 * @data 2010-12-27AM
 */
@SuppressWarnings("unchecked")
public class AppUserDaoImpl extends BaseDaoImpl<AppUser> implements AppUserDao,
		UserDetailsService {
	

	public AppUserDaoImpl() {
		super(AppUser.class);
	}

	@Override
	public AppUser findByUserName(String username) {
		String hql = "from AppUser au where au.username=?";
		Object[] params = {username};
		List<AppUser> list = findByHql(hql, params);
		AppUser user = null;
		if (list.size() != 0) {
			user = list.get(0);
			
		}

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
		AppUser user = (AppUser) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						String hql = "from AppUser ap where ap.username=? and ap.delFlag = ?";
						Query query = session.createQuery(hql);
						query.setString(0, username);
						query.setShort(1, Constants.FLAG_UNDELETED);
						AppUser user = null;
						user = (AppUser) query.uniqueResult();

						if (user != null) {
							Hibernate.initialize(user.getRoles());
							Hibernate.initialize(user.getDepartment());
							user.init();
						}
						return user;
					}
		});
		//初始化其对应的公司
		if(user.getDepartment()!=null){
			String path=user.getDepartment().getPath();
			if(path!=null){
				String[]ids=path.split("[.]");
				for(String id : ids){
					if(!"0".equals(id) && StringUtils.isNotEmpty(id)){
						Organization org=(Organization)findUnique("from Organization org where org.orgId=?", new Object[]{new Long(id)});
						if(Organization.ORG_TYPE_COMPANY.equals(org.getOrgType())){
							user.setCompany(org);
							break;
						}
					}
				}
			}
		}
		
		//若用户当前不属于任何公司，则其为公共部门下的。
		if(user.getCompany()==null){
			Organization org=(Organization)findUnique("from Organization org where org.orgId=?", new Object[]{new Long(Organization.PUBLIC_COMPANY_ID)});
			user.setCompany(org);
		}
		
		return user;
	}

	/**
	 * 根据部门PATH属性查找
	 */
	@Override
	public List findByDepartment(String path, PagingBean pb) {
		List list = new ArrayList();
		String hql = new String();
		if ("0.".equals(path)) {
			hql = "from AppUser vo2 where vo2.delFlag = ?";
			list.add(Constants.FLAG_UNDELETED);
		} else {
			//TODO
			hql="select distinct au from AppUser au where au.department.path like ? and au.delFlag=? ";
//			hql = "select DISTINCT vo2 from Department vo1,AppUser vo2,DepUsers vo3 where 1=1"
//					+ " and vo3.appUser=vo2"
//					+ " and vo3.department=vo1"
//					+ " and vo1.path like ? and vo2.delFlag = ? order by vo3.sn";
			list.add(path + "%");
			list.add(Constants.FLAG_UNDELETED);
		}
		return findByHql(hql, list.toArray(), pb);
	}

	@Override
	public List findByDepartment(Department department) {
		String hql = "select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department and vo1.path like ? and vo2.delFlag = ?";
		Object[] params = { department.getPath() + "%",
				Constants.FLAG_UNDELETED };
		return findByHql(hql, params);
	}

	@Override
	public List findByRole(Long roleId) {
		String hql = "select vo from AppUser vo join vo.roles roles where roles.roleId=? and vo.delFlag = ?";
		Object[] objs = { roleId, Constants.FLAG_UNDELETED };
		return findByHql(hql, objs);
	}
	
	@Override
	public List findByRoleName(String roleName) {
		String hql = "select vo from AppUser vo join vo.roles roles where roles.roleName like ? and vo.delFlag = ?";
		Object[] objs = {"%" + roleName + "%", Constants.FLAG_UNDELETED };
		return findByHql(hql, objs);
	}

	@Override
	public List findByRole(Long roleId, PagingBean pb) {
		String hql = "select vo from AppUser vo join vo.roles roles where roles.roleId=? and vo.delFlag = ?";
		Object[] objs = { roleId, Constants.FLAG_UNDELETED };
		return findByHql(hql, objs, pb);
	}

	@Override
	public List<AppUser> findByDepartment(String path) {
		String hql = "select vo2 from Department vo1,AppUser vo2 where vo1.depId=vo2.depId and vo1.path like ? and vo2.delFlag =?";
		Object[] params = { path + "%", Constants.FLAG_UNDELETED };
		return findByHql(hql, params);
	}

	public List findByRoleId(Long roleId) {
		String hql = "select vo from AppUser vo join vo.roles as roles where roles.roleId=? and vo.delFlag =?";
		return findByHql(hql, new Object[] { roleId, Constants.FLAG_UNDELETED });
	}

	public List findByUserIds(Long... userIds) {
		String hql = "select vo from AppUser vo where vo.delFlag=? ";

		if (userIds == null || userIds.length == 0)
			return null;
		hql += " where vo.userId in (";
		int i = 0;
		for (@SuppressWarnings("unused") Long userId : userIds) {
			if (i++ > 0) {
				hql += ",";
			}
			hql += "?";
		}
		hql += " )";

		return findByHql(hql,
				new Object[] { Constants.FLAG_UNDELETED, userIds });
	}

	@Override
	public List<AppUser> findSubAppUser(String path, Set<Long> userIds,
			PagingBean pb) {
		String st = "";
		if (userIds.size() > 0) {
			Iterator<Long> it = userIds.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				sb.append(it.next().toString() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			st = sb.toString();
		}

		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		if (path != null) {
			hql
					.append("select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department ");
			hql.append(" and vo1.path like ?");
			list.add(path + "%");
		} else {
			hql.append("from AppUser vo2 where 1=1 ");
		}
		if (st != "") {
			hql.append(" and vo2.userId not in (" + st + ")");
		}
		hql.append(" and vo2.delFlag = ?");
		list.add(Constants.FLAG_UNDELETED);
		return findByHql(hql.toString(), list.toArray(), pb);
	}

	@Override
	public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds,
			PagingBean pb) {
		String st = "";
		if (userIds.size() > 0) {
			Iterator<Long> it = userIds.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				sb.append(it.next().toString() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			st = sb.toString();
		}
		StringBuffer hql = new StringBuffer(
				"select vo from AppUser vo join vo.roles roles where roles.roleId=?");
		List list = new ArrayList();
		list.add(roleId);
		if (st != "") {
			hql.append(" and vo.userId not in (" + st + ")");
		}
		hql.append(" and vo.delFlag =?");
		list.add(Constants.FLAG_UNDELETED);
		return findByHql(hql.toString(), list.toArray(), pb);
	}

	@Override
	public List<AppUser> findByDepId(Long depId) {
		String hql = "from AppUser vo where vo.delFlag=0 and vo.department.depId=?";
		Object[] objs = { depId };
		return findByHql(hql, objs);
	}

	/**
	 * 查找某组角色列表下所有的用户
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<AppUser> findUsersByRoleIds(String roleIds) {
		if (StringUtils.isEmpty(roleIds)) {
			return new ArrayList();
		}
		String hql = "select distinct au from AppUser as au join au.roles as roles where roles.roleId in ("
				+ roleIds + ") and au.delFlag =?";

		return findByHql(hql, new Object[] { Constants.FLAG_UNDELETED });
	}

	/**
	 * @description 取得同级或下级
	 * @author YHZ
	 * @data 2010-12-23PM
	 * @param userId
	 *            用户id
	 * @return List<AppUser>
	 */
	public List<AppUser> findRelativeUsersByUserId(String sameLevel) {
		
		// 按Position取下属
		StringBuffer sb = new StringBuffer("select up " +
			"from AppUser au, UserPosition up " +
			"where au.userId = ? " +
			"and au.userId = up.appUser.userId " +
			"and au.delFlag = 0");
		
		Query query = getSession().createQuery(sb.toString());
		query.setLong(0, ContextUtil.getCurrentUserId());
		List<UserPosition> uplist = query.list();
		List<Long> pidList = new ArrayList();
		for(UserPosition up:uplist){
			if(!pidList.contains(up.getPosition().getPosId())){
				pidList.add(up.getPosition().getPosId());
			}
			String upHql = "select ps from PositionSub ps " +
				"where ps.mainPositionId = "+up.getPosition().getPosId();
			query = getSession().createQuery(upHql);
			List<PositionSub> pslist = query.list();
			for(PositionSub ps:pslist){
				if(!pidList.contains(ps.getSubPositionId())){
					pidList.add(ps.getSubPositionId());
				}
			}
			upHql = "select ps from PositionSub ps " +
				"where ps.subPositionId = "+up.getPosition().getPosId();
			query = getSession().createQuery(upHql);
			pslist = query.list();
			for(PositionSub ps:pslist){
				if(!pidList.contains(ps.getMainPositionId())){
					pidList.add(ps.getMainPositionId());
				}
			}
		}
		
		String paths = "";
		if(pidList.size()>0){
			for(Long posid:pidList){
				String posHql = "select p from Position p where p.posId = "+posid;
				query = getSession().createQuery(posHql);
				List<Position> plist = query.list();
				if(paths.length()>0)
					paths += ",";
				paths += plist.get(0).getPath();
			}
		}
		
		// 取得路径对应posId
		sb = new StringBuffer("select p from Position p ");
		String[] pths = paths.split(",");
		for(int index=0;index<pths.length;index++){
			if("true".equals(sameLevel)){ // 同级职位
				if(index==0){
					sb.append("where (p.path = '"+pths[0]+"') ");
				}else{
					sb.append("or (p.path = '"+pths[index]+"') ");
				}
			}else{ // 下级职位
				if(index==0){
					sb.append("where (p.path like '"+pths[0]+"%' and p.path <> '"+pths[0]+"') ");
				}else{
					sb.append("or (p.path like '"+pths[index]+"%' and p.path <> '"+pths[index]+"') ");
				}
			}
		}
		
		// 得到对应用户
		String pathHql = sb.toString();
		sb = new StringBuffer("select distinct au from AppUser au,UserPosition up " +
				"where au.userId = up.appUser.userId " +
				"and up.position.posId in ("+pathHql+") " +
				"and au.delFlag = 0");
		
		List<AppUser>users=findByHql(sb.toString());
		
		// 按Relative_job取下属
		sb = new StringBuffer("select distinct au0 from AppUser au0 where au0.userId " +
				"in (select ru.jobUser.userId from AppUser au, RelativeUser ru " +
				"where au.userId = ? and au.delFlag = 0 " +
				"and au.userId = ru.appUser.userId) ");
		
		users.addAll(findByHql(sb.toString(),new Object[]{ContextUtil.getCurrentUserId()}));
		
		return users;
	}

	@Override
	public List<AppUser> findByDepartment(String path, String userIds,
			PagingBean pb) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		if ("0.".equals(path)) {
			hql.append("from AppUser vo2 where vo2.delFlag = ? ");
			list.add(Constants.FLAG_UNDELETED);
		} else {
			hql.append( "select DISTINCT vo2 from Department vo1,AppUser vo2,DepUsers vo3 where 1=1"
					+ " and vo3.appUser=vo2"
					+ " and vo3.department=vo1"
					+ " and vo1.path like ? and vo2.delFlag = ? ");
			list.add(path + "%");
			list.add(Constants.FLAG_UNDELETED);
		}
		//删除userIds中的数据
		if(userIds != null && !userIds.equals("")){
			hql.append("and vo2.userId in (?) ");
			list.add(userIds);
		}
		hql.append("order by vo3.sn "); //排序
		logger.debug("自定义AppUserDaoImpl : " + hql.toString());
		return findByHql(hql.toString(), list.toArray(), pb);
	}
	
	/**
	 * 按角色取得用户列表
	 * @param roleId
	 * @return
	 */
	public List<AppUser> getUsersByRoleId(Long roleId){
		String hql="from AppUser au join au.roles as role where role.roleId=?";
		return (List<AppUser>)findByHql(hql, new Object[]{roleId});
	}
	
	/**
	 * 按部门取得用户列表
	 * @param orgPath
	 * @return
	 */
	@Override
	public List<AppUser> getDepUsers(String orgPath,PagingBean pb) {
		if(!"0.".equals(orgPath)){
			String hql = "select au from AppUser au where au.userId in " +
					"(select distinct uo.appUser.userId from UserOrg uo where uo.organization.orgId in " +
					"(select o.orgId from Organization o where o.path like ?)) and au.delFlag = 0";
			
			return findByHql(hql, new Object[]{orgPath+"%"}, pb);
		}
		
		return findByHql("from AppUser au where au.delFlag=0", new Object[]{}, pb);
	}
	
	/**
	 * 取得相对岗位用户列表
	 * @param reJobId
	 * @return
	 */
	public List<AppUser> getReLevelUser(String reJobId){
		Long userId = ContextUtil.getCurrentUserId();
		
		List<Position> plist = new ArrayList<Position>();
		
		String hql = "select distinct p from UserPosition up, Position p " +
			"where up.position.posId = p.posId " +
			"and up.appUser.userId = ? ";
		
		Query query = getSession().createQuery(hql);
		query.setLong(0, userId);
		List<Position> plist3 = query.list();
		
		if(plist3!=null&&plist3.size()>0){
			for(Position p:plist3){
				plist.add(p);
			}
		}
		
		if(Integer.parseInt(reJobId)<0){
			if(plist!=null&&plist.size()>0){
				for(Position p:plist3){
					
					hql = "select p from Position p where p.posId in(select ps.subPositionId from PositionSub ps where ps.mainPositionId = "+p.getPosId()+")";
					List<Position> plist1 = getSession().createQuery(hql).list();
					if(plist1!=null&&plist1.size()>0){
						for(Position p1:plist1){
							if(!plist.contains(p1)){
								plist.add(p1);
							}
						}
					}
					
					hql = "select p from Position p where p.posId in(select ps.mainPositionId from PositionSub ps where ps.subPositionId = "+p.getPosId()+")";
					List<Position> plist2 = getSession().createQuery(hql).list();
					if(plist2!=null&&plist2.size()>0){
						for(Position p2:plist2){
							if(!plist.contains(p2)){
								plist.add(p2);
							}
						}
					}
					
				}
			}
		}
		
		int tmpReJobId = Integer.parseInt(reJobId)*(-1);
		String rePath = "";
		int ltFlag = 0;
		if(plist!=null&&plist.size()>0){
			for(int index=0;index<plist.size();index++){
				String path = ((Position)plist.get(index)).getPath();
				
				String[] curLvArr = path.substring(0, path.length()-1).split("\\.");
				int curLevel = curLvArr.length;
				int aftLevel = curLevel + tmpReJobId;
				if(aftLevel<curLevel){
					for(int index0=0;index0<aftLevel;index0++){
						rePath += curLvArr[index0] + ".";
					}
				}else if(aftLevel>curLevel){
					rePath += path;
					for(int index0=0;index0<aftLevel-curLevel;index0++){
						rePath += "%.";
					}
				}else{
					rePath += path;
				}
				if(index==0){ltFlag = aftLevel-curLevel;}
				rePath += ",";
			}
		}
		
//		System.out.println("rePath:"+rePath);
		
		// 取得路径对应posId
		StringBuffer sb = null;
		String[] pths = rePath.length()==0?new String[0]:rePath.substring(0, rePath.length()-1).split(",");
		if(ltFlag>0){
			
			sb = new StringBuffer("select p.posId from Position p ");
			for(int index=0;index<pths.length;index++){
				query = getSession().createQuery(
						"select p from Position p where p.path like '"+pths[index]+"' ");
				plist = query.list();
				int matchFlag = pths[index].split("\\.").length;
				if(plist!=null&&plist.size()>0){
					for(int index1=0;index1<plist.size();index1++){
						Position p = plist.get(index1);
						if(matchFlag==p.getPath().split("\\.").length){
							if(index==0&&index1==0){
								sb.append("where p.path = '"+p.getPath()+"' ");
							}else{
								sb.append("or p.path = '"+p.getPath()+"' ");
							}
						} 
					}
//					sb.append(") and p.posId in ("+hql+")");
				}
			}
		}else{
			sb = new StringBuffer("select p.posId from Position p where p.posSupId in " +
				"(select p0.posSupId from Position p0 ");
			for(int index=0;index<pths.length;index++){
				if(index==0){
					sb.append("where p0.path = '"+pths[0]+"' ");
				}else{
					sb.append("or p0.path = '"+pths[index]+"' ");
				}
			}
			sb.append(")");
//			sb.append(") and p0.posId in ("+hql+") )");
		}
		
//		System.out.println(sb.toString());
		String pathHql = sb.toString().length()==0?"0": sb.toString();
		String[] rehql = null;
		if("0".equals(reJobId)){
			rehql = new String[1];
			rehql[0] = pathHql;
		}else{
			rehql = new String[3];
			rehql[0]="select distinct ps.subPositionId from PositionSub ps where ps.mainPositionId in ("+pathHql+") ";
			rehql[1]="select distinct ps.mainPositionId from PositionSub ps where ps.subPositionId in ("+pathHql+") ";
			rehql[2] = pathHql;
		}
		
		List<AppUser> list = new ArrayList<AppUser>();
		for(int index=0;index<rehql.length;index++){
			sb = new StringBuffer("select distinct au from AppUser au,UserPosition up " +
				"where au.userId = up.appUser.userId " +
				"and up.position.posId in ("+rehql[index]+") " +
				"and au.delFlag=0 ");
//			List<AppUser> list = findByHql(sb.toString());
		
			for(AppUser au:findByHql(sb.toString())){
				if(!list.contains(au))
					list.add(au);
			}
		}
		
		if("0".equals(reJobId)&&list.size()==0){
			list.add(ContextUtil.getCurrentUser());
		}
		
		return list;
	}
	
	/**
	 * 取得组织主要负责人
	 * @param userOrg
	 * @return
	 */
	@Override
	public List<AppUser> getChargeOrgUsers(Set userOrgs) {
		String isChargeUser = "";
		Iterator<UserOrg> it = userOrgs.iterator();
		while(it.hasNext()){
			UserOrg userOrg = it.next();
			if(userOrg.getIsCharge()!=null&&userOrg.getIsCharge()==1){
				isChargeUser += userOrg.getUserid()+",";
			}
		}
		isChargeUser = isChargeUser.length()==0?"0":isChargeUser.substring(0, isChargeUser.length()-1);
		String hql = "from AppUser au where au.userId in ("+isChargeUser+") and au.delFlag=0";
		return findByHql(hql);
	}

	@Override
	public AppUser getByFx(String fx) {
		// TODO Auto-generated method stub
		String hql = "from AppUser au where au.fax=?";
		Object[] params = {fx};
		List<AppUser> list = findByHql(hql, params);
		AppUser user = null;
		if (list.size() != 0) {
			user = list.get(0);
		} 
		return user;
	}

	@Override
	public void yjActivity() {
		String hql = " update AppUser au set au.status='1' ";
		Object[] params = new Object[]{};
		update(hql, params);
		
	}

}
