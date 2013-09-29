package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.system.PositionDao;
import com.htsoft.est.model.system.Position;

@SuppressWarnings("unchecked")
public class PositionDaoImpl extends BaseDaoImpl<Position> implements PositionDao{

	public PositionDaoImpl() {
		super(Position.class);
	}
	
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Position> getByParent(Long parentId){
		String hql="from Position p where p.posSupId=?";
		return findByHql(hql, new Object[]{parentId});
	}
	/**
	 * 按路径查找所有节
	 * @param path
	 * @return
	 */
	public List<Position> getByPath(String path){
		String hql="from Position p where p.path like ?";
		return findByHql(hql,new Object[]{path+"%"});
	}
	
	@Override
	public List<Position> getUnderLing(Long posId, PagingBean pb) {
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select vo from Position vo where 1=1 ");
		if(posId != null && posId > 0){ // 根据posId查询所有
			params.add(posId);
			hql.append("and vo.posSupId = ? ");
		}
		return findByHql(hql.toString(), params.toArray(), pb);
	}
	
	/**
	 * 根据某岗位取得相应岗位
	 * @param 
	 * posId 当前岗位
	 * posSupId 上级岗位
	 * lvFlag
	 * 		1 同级岗位 
	 * 		2 同级及下属
	 * 		3 下属
	 * @return
	 */
	public List<Position> getUnderling(Long posId, Long posSupId, int lvFlag, PagingBean pb) {
		List<Position> plist = new ArrayList<Position>();
		String path = "";
		
		// 根据上级ID取得其下一级直属职位
		StringBuffer sb = new StringBuffer("select p from Position p where p.posSupId=?");
		Query query = getSession().createQuery(sb.toString());
		query.setLong(0, posSupId);
		List<Position> plist3 = query.list();
		if(plist3!=null&&plist3.size()>0){
			for(Position p:plist3){
				plist.add(p);
			}
		}
		
		// 根据当前职位ID取得其已关联的同级职位
		// 如果lvFlag==1，则删除
		sb = new StringBuffer("select p from Position p where p.posId " +
				"in(select ps.subPositionId from PositionSub ps where ps.mainPositionId = "+posId+")");
		List<Position> plist5 = getSession().createQuery(sb.toString()).list();
		if(plist5!=null&&plist5.size()>0){
			for(Position p:plist5){
				if(plist.contains(p)){
					if(lvFlag==1)
						plist.remove(p);
				}
			}
		}
		
		sb = new StringBuffer("select p from Position p where p.posId " +
				"in(select ps.mainPositionId from PositionSub ps where ps.subPositionId = "+posId+")");
		List<Position> plist6 = getSession().createQuery(sb.toString()).list();
		if(plist6!=null&&plist6.size()>0){
			for(Position p:plist6){
				if(plist.contains(p)){
					if(lvFlag==1)
						plist.remove(p);
				}
			}
		}
		
		// 如果lvFlag==3，则清空plist
		if(plist5.size()==0&&plist6.size()==0&&lvFlag==3){plist.clear();}

		// 如果lvFlag==1，则返回plist
		if(lvFlag==1){
			int page = plist.size()/pb.getPageSize();
			return plist.subList(pb.getFirstResult(),
					pb.getPageSize()*page+plist.size()%pb.getPageSize());
		}
		
		// 找当前职位的同级职位之间的对应关系
		// 如果lvFlag==3，取得其路径
		if(plist!=null&&plist.size()>0){
			for(Position p:plist3){
				
				sb = new StringBuffer("select p from Position p where p.posId " +
						"in(select ps.subPositionId from PositionSub ps where ps.mainPositionId = "+p.getPosId()+")");
				List<Position> plist1 = getSession().createQuery(sb.toString()).list();
				if(plist1!=null&&plist1.size()>0){
					for(Position p1:plist1){
						if(plist.contains(p1)){
							if(lvFlag==2){
								plist.remove(p1);
								if(path.length()>0)
									path+=",";
								path += p1.getPath();
							}else if(lvFlag==3){
								if(path.length()>0)
									path+=",";
								path += p1.getPath();
							}
						}
					}
				}
				
				sb = new StringBuffer("select p from Position p where p.posId " +
						"in(select ps.mainPositionId from PositionSub ps where ps.subPositionId = "+p.getPosId()+")");
				List<Position> plist2 = getSession().createQuery(sb.toString()).list();
				if(plist2!=null&&plist2.size()>0){
					for(Position p2:plist2){
						if(plist.contains(p2)){
							if(lvFlag==2){
								plist.remove(p2);
								if(path.length()>0)
									path+=",";
								path += p2.getPath();
							}else if(lvFlag==3){
								if(path.length()>0)
									path+=",";
								path += p2.getPath();
							}
						}
					}
				}
				
			}
		}
		
		// 根据路径取得所有下属
		StringBuffer rsb = new StringBuffer("");
		if(path.length()>0){
			rsb.append("select p from Position p ");
			String[] pths = path.split(",");
			
			for(int index=0;index<pths.length;index++){
				if(index==0){
					if(lvFlag==2){
						rsb.append("where p.path like '"+pths[0]+"%' ");
					}
					else{
						rsb.append("where (p.path like '"+pths[index]+"%' and p.path <> '"+pths[index]+"') ");
					}
				}else{
					if(lvFlag==2){
						rsb.append("or p.path like '"+pths[index]+"%' ");
					}else{
						rsb.append("or (p.path like '"+pths[index]+"%' and p.path <> '"+pths[index]+"') ");
					}
				}
			}
			if(lvFlag==3){ // 把上级删除
				Position supPos = get(posSupId);
				String supPosPath = supPos==null?"0.":supPos.getPath();
				rsb.append("and p.path <> '"+supPosPath+"' ");
			}
		}
//		System.out.println(rsb.toString());
		
		// lvFlag==3，没有取得任何下属时。取回本职位所有下属
		if(lvFlag==3&&path.length()==0){
			Position curPos = get(posId);
			String curPath = curPos==null?"0.":curPos.getPath();
			String hql = "select p from Position p where p.path like '"+curPath+"%' and p.path <> '"+curPath+"') ";
			plist = findByHql(hql, new Object[]{}, pb);
		}
		
		return lvFlag==2||path.length()==0?plist:findByHql(rsb.toString(), new Object[]{}, pb);
	}

}