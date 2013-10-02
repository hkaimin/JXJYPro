package com.htsoft.core.dao.impl;



import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.htsoft.core.dao.BasicDao;

public class BasicDaoImpl<T, ID extends Serializable> extends
		HibernateDaoSupport implements BasicDao<T, ID> {
    
	protected Log log=LogFactory.getLog(BasicDaoImpl.class);

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void deleteById(Class<T> entityClass, ID id) {
		delete(this.findById(entityClass, id));

	}

	public T findById(Class<T> entityClass, ID id) {
		return (T)this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll(Class<T> entityClass) {
		String name = entityClass.getName();
		return this.getHibernateTemplate().find("from " + name);
	}

	public List<T> findByProperties(DetachedCriteria dc) {
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	public void saveNoRe(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public T saveRe(T entity) {
		if (entity == null) {
			return null;
		}
		this.getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public String getSequence(final String sequenceName) {
		String o = (String) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String sql = "select " + sequenceName
								+ ".nextval as seq from dual";
						SQLQuery query = session.createSQLQuery(sql);
						query.addScalar("seq", Hibernate.STRING);
						return query.uniqueResult();
					}
				});
		return o;
	}

	public List queryForPage(final String hql, final int offset,
			final int length) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				List list = query.list();
				return list;
			}
		});
		return list;
	}

	public int getAllRowCount(String hql) {
		return getHibernateTemplate().find(hql).size();
	}

	public Object queryNum(final String hql, final Map<String, Object> args) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				if (log.isDebugEnabled()) {
					log.debug("queryNum :" + hql);
				}
				Query q = session.createQuery(hql);
				if (args != null) {
					for (String k : args.keySet()) {
						q.setParameter(k, args.get(k));
					}
				}
				return q.uniqueResult();// q.executeUpdate()
			}
		});
	}

	@SuppressWarnings("unchecked")
	public int getTotalCnt(Class<T> clazz, final String hqlwhere) {
		String hql = "select count(*) from " + clazz.getName() + " where "
				+ hqlwhere;
		if (log.isDebugEnabled()) {
			log.debug("getTotalCnt :" + hql);
		}
		Object rt = queryNum(hql, new HashMap());
		return Integer.parseInt(rt.toString());
	}

	public int getTotalCnt(Class<T> clazz, final String hqlwhere,
			final Map<String, Object> args) {
		String hql = "select count(*) from " + clazz.getName() + " where "
				+ hqlwhere;
		if (log.isDebugEnabled()) {
			log.debug("getTotalCnt :" + hql);
		}
		Object rt = queryNum(hql, args);
		return Integer.parseInt(rt.toString());
	}

	@SuppressWarnings("unchecked")
	public List<T> load(final Class<T> clazz, final String hqlwhere) {
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						String hql = "from " + clazz.getName() + " where " + hqlwhere;
						if (log.isDebugEnabled()) {
							log.debug("load :" + hql);
						}
						return session.createQuery(hql).list();
					}
				});
		return rt;
	}

	@SuppressWarnings("unchecked")
	public List<T> load(final Class<T> clazz, final String hqlwhere,
			final int start, final int size) {
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						String hql = "from " + clazz.getName() + " where "
								+ hqlwhere;
						
						log.debug("load :" + hql);
						
						Query q = session.createQuery(hql);
						q.setMaxResults(size);
						q.setFirstResult(start);
						return q.list();
					}
				});
		return rt;
	}

	@SuppressWarnings("unchecked")
	public List<T> load(final Class<T> clazz, final String hqlwhere,
			final Map<String, Object> args, final int start, final int size) {
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						String hql = "from " + clazz.getName() + " where "
								+ hqlwhere;
						if (log.isDebugEnabled()) {
							log.debug("load :" + hql);
						}
						Query q = session.createQuery(hql);
						q.setMaxResults(size);
						q.setFirstResult(start);
						for (String k : args.keySet()) {
							q.setParameter(k, args.get(k));
						}
						return q.list();
					}
				});
		return rt;
	}

	@SuppressWarnings("unchecked")
	public List<T> load(final Class<T> clazz, final String hqlwhere,
			final Map<String, Object> args) {
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						String hql = "from " + clazz.getName() + " where "
								+ hqlwhere;
							log.debug("load :" + hql);
						Query q = session.createQuery(hql);
						for (String k : args.keySet()) {
							q.setParameter(k, args.get(k));
						}
						return q.list();
					}
				});
		return rt;
	}

	@SuppressWarnings("unchecked")
	public Object execute(final String hql, final Map<String, Object> args) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				if (log.isDebugEnabled())
					log.debug("execute :" + hql);
				Query q = session.createQuery(hql);
				if (args != null) {
					for (String k : args.keySet()) {
						q.setParameter(k, args.get(k));
					}
				}
				return q.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void deleteByIds(Class<T> clazz, String[] ids) {
		if (ids.length < 1)
			return;
		StringBuilder sb = new StringBuilder();
		sb.append("id in (");
		for (int j = 0; j < ids.length; j++) {
			sb.append("'" + ids[j] + "'");
			if (j < (ids.length - 1))
				sb.append(",");
		}
		sb.append(")");
		execute("delete " + clazz.getName() + " where " + sb.toString(), null);
	}

	public void deleteByIds(Class<T> clazz, String ids) {
		if (ids == null || ids.equals(""))
			return;
		String[] idArr = ids.split(",");
		StringBuilder sb = new StringBuilder();
		sb.append("id in (");
		for (int j = 0; j < idArr.length; j++) {
			sb.append("'" + idArr[j] + "'");
			if (j < (idArr.length - 1))
				sb.append(",");
		}
		sb.append(")");
		execute("delete " + clazz.getName() + " where " + sb.toString(), null);
	}

	@SuppressWarnings("unchecked")
	public void deleteAll(Class<T> clazz) {
		execute("delete " + clazz.getName(), null);

	}

	@SuppressWarnings("unchecked")
	public List<T> loadAll(Class<T> clazz) {
		return this.getHibernateTemplate().loadAll(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> loadBySql(final Class<T> clazz, final String sql,
			final int start, final int size) {
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query q = session.createSQLQuery(sql).addEntity(clazz);
						q.setMaxResults(size);
						q.setFirstResult(start);
						return q.list();
					}
				});
		return rt;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> loadBySql(final Class<T> clazz, final String sql) {
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query q = session.createSQLQuery(sql).addEntity(clazz);
						return q.list();
					}
				});
		return rt;
	}
	
	public Object queryTotalCntBySql(final String sql) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				if (log.isDebugEnabled()) {
					log.debug("queryNum :" + sql);
				}
				Query q = session.createSQLQuery(sql);
				
				return q.uniqueResult();// q.executeUpdate()
			}
		});
	}

	public List<T> loadByForTransf(final String sql,final Class<T> clazz) {
		// TODO Auto-generated method stub
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createSQLQuery(sql)
						.setResultTransformer(Transformers.aliasToBean(clazz)).list();
					}
				});
		
		return rt;


	}

	public List loadByForTransfReturnListMap(final String sql,final int start, final int size) {
		// TODO Auto-generated method stub
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query=session.createSQLQuery(sql);
						query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
						query.setMaxResults(size);
						query.setFirstResult(start);
				        List<Map<String,Object>> results=query.list();
				        return results;
					}
				});
		return rt;        
	}
	
	public List loadByForTransfReturnListMap(final String sql) {
		// TODO Auto-generated method stub
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query=session.createSQLQuery(sql);
						query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
				        List<Map<String,Object>> results=query.list();
				        return results;
					}
				});
		return rt;        
	}

	public List loadPageByForTransfReturnListMap(final String sql,final int start, final int size) {
		// TODO Auto-generated method stub
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query=session.createSQLQuery(sql);
						query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
						query.setMaxResults(size);
						query.setFirstResult(start);
				        List<Map<String,Object>> results=query.list();
				        return results;
					}
				});
		return rt;        
	}	
	
	public int loadPageCountByForTransfReturnListMap(final String sql) {
		// TODO Auto-generated method stub
		List rt = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query=session.createSQLQuery(sql);
						return query.list();
					}
				});
		return rt.size();        
	}	
	
	
	public void deleteBySql(final String sql) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query=session.createSQLQuery(sql);
						return query.executeUpdate();
					}
				});
		     
	}		
}
