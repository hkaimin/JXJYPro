package com.htsoft.core.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 通用Dao封装
 * 
 * @version 1.0
 * @author zhangtianmiao
 */
public interface BasicDao<T, ID extends Serializable> {
	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            对象
	 * @throws DataAccessException
	 *             运行时异常
	 */
	public void saveNoRe(T entity);

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            对象
	 * @throws DataAccessException
	 *             运行时异常
	 * @return T 返回带有id的保存后实体对象
	 */
	public T saveRe(T entity);

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            对象
	 * @throws DataAccessException
	 *             运行时异常
	 * @return void
	 */

	public void delete(T entity);

	/**
	 * 根据clazz和实体id删除实体对象
	 * 
	 * @param entityClass
	 *            clazz
	 * @param id
	 *            实体对象id
	 * @throws DataAccessException
	 *             运行时异常
	 * @return void
	 */

	public void deleteById(Class<T> entityClass, ID id);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体
	 * @throws DataAccessException
	 *             运行时异常
	 * @return void
	 */

	public void update(T entity);

	/**
	 * 根据clazz和id查找实体对象
	 * 
	 * @param entityClass
	 *            clazz
	 * @param id
	 *            实体主键值
	 * @throws DataAccessException
	 *             运行时异常
	 * @return object
	 */

	public T findById(Class<T> entityClass, ID id);

	/**
	 * 根据clazz查询全部记录
	 * 
	 * @param entityClass
	 *            clazz
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list<T>
	 */

	public List<T> findAll(Class<T> entityClass);

	/**
	 * 根据对象更新或保存
	 * 
	 * @param entity
	 *            对象
	 * @throws DataAccessException
	 *             运行时异常
	 * @return void
	 */

	public void saveOrUpdate(T entity);

	/**
	 * 根据DetachedCriteria查询条件查询记录
	 * 
	 * @param dc
	 *            DetachedCriteria
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list<T>
	 */

	public List<T> findByProperties(DetachedCriteria dc);

	/**
	 * 获取序列值
	 * 
	 * @param seqenceName
	 *            seqence名称
	 * @throws DataAccessException
	 *             运行时异常
	 * @return String
	 */

	public String getSequence(final String seqenceName);

	/**
	 * 分页查询
	 * 
	 * @param hql
	 *            hql字符串
	 * @param offset
	 * @param length
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */

	public List<T> queryForPage(final String hql, final int offset,
			final int length);

	/**
	 * 查询记录数
	 * 
	 * @param hql
	 *            String
	 * @throws DataAccessException
	 *             运行时异常
	 * @return int
	 */

	public int getAllRowCount(String hql);

	/**
	 * 查询带有参数的hql记录数
	 * 
	 * @param hql
	 *            Sting
	 * @param map
	 *            查询字段及对应值对
	 * @throws DataAccessException
	 *             运行时异常
	 * @return object
	 */

	public Object queryNum(final String hql, final Map<String, Object> args);

	/**
	 * 查询带有参数的hql记录数
	 * 
	 * @param hql
	 *            Sting
	 * @param map
	 *            查询字段及对应值对
	 * @throws DataAccessException
	 *             运行时异常
	 * @return int
	 */

	public int getTotalCnt(Class<T> clazz, final String hqlwhere);

	/**
	 * 查询带有参数的hql记录数
	 * 
	 * @param hql
	 *            Sting
	 * @param map
	 *            查询字段及对应值对
	 * @throws DataAccessException
	 *             运行时异常
	 * @return int
	 */

	public int getTotalCnt(Class<T> clazz, final String hqlwhere,
			final Map<String, Object> args);

	/**
	 * 查询带有参数的hql记录
	 * 
	 * @param hql
	 *            Sting
	 * @param map
	 *            查询字段及对应值对
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */
	public List<T> load(final Class<T> clazz, final String hqlwhere);

	/**
	 * 查询带有参数的hql记录
	 * 
	 * @param hql
	 *            Sting
	 * @param map
	 *            查询字段及对应值对
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */
	public List<T> load(final Class<T> clazz, final String hqlwhere,
			final int start, final int size);

	/**
	 * 分页查询带有参数的hql记录
	 * 
	 * @param hql
	 *            Sting
	 * @param map
	 *            查询字段及对应值对
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */
	public List<T> load(final Class<T> clazz, final String hqlwhere,
			final Map<String, Object> args, final int start, final int size);

	/**
	 * 查询带有参数的hql记录
	 * 
	 * @param hql
	 *            Sting
	 * @param map
	 *            查询字段及对应值对
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */
	public List<T> load(final Class<T> clazz, final String hqlwhere,
			final Map<String, Object> args);

	/**
	 * 执行hql语句
	 * 
	 * @param hql
	 *            Sting
	 * @param map
	 *            查询字段及对应值对
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */
	public Object execute(final String hql, final Map<String, Object> args);

	/**
	 * 根据clazz和id数据删除全部记录
	 * 
	 * @param entityClass
	 *            clazz
	 * @throws DataAccessException
	 *             运行时异常
	 * @return void
	 */
	public void deleteByIds(Class<T> clazz, String[] ids);

	/**
	 * 根据clazz和id串删除全部记录
	 * 
	 * @param entityClass
	 *            clazz
	 * @param ids
	 *            使用,分割的id串
	 * @throws DataAccessException
	 *             运行时异常
	 * @return void
	 */
	public void deleteByIds(Class<T> clazz, String ids);

	/**
	 * 根据clazz删除全部记录
	 * 
	 * @param entityClass
	 *            clazz
	 * @throws DataAccessException
	 *             运行时异常
	 * @return void
	 */
	public void deleteAll(Class<T> clazz);

	/**
	 * 根据clazz删除全部记录
	 * 
	 * @param entityClass
	 *            clazz
	 * @throws DataAccessException
	 *             运行时异常
	 * @return void
	 */
	public List<T> loadAll(Class<T> clazz);
	
	/**
	 * 根据sql查询全部记录
	 * 
	 * @param entityClass
	 *            clazz ,String sql
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */
	
	public List<T> loadBySql(final Class<T> clazz, final String sql,
			final int start, final int size);
	
	/**
	 * 根据sql查询全部记录
	 * 
	 * @param entityClass
	 *            clazz ,String sql
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */
	
	public List<T> loadBySql(final Class<T> clazz, final String sql);
	/**
	 * 根据sql查询全部记录
	 * 
	 * @param entityClass
	 *            clazz ,String sql
	 * @throws DataAccessException
	 *             运行时异常
	 * @return list
	 */
	public Object queryTotalCntBySql(final String sql);
	
	/**
	 * 	注意：必须提供属性的SETTER,由于oracle中字段必然为大写，因此必须提供方法set大写
	 * @param sql  标准SQL
	 * @return
	 */
	public List<T> loadByForTransf(final String sql,final Class<T> clazz);
	
	/**
	 * 	
	 * @param sql  标准SQL
	 * @return 
	 */
	public List loadByForTransfReturnListMap(final String sql,final int start, final int size);
	
	public List loadByForTransfReturnListMap(final String sql);
	
	public int loadPageCountByForTransfReturnListMap(final String sql);
	
	/**
	 * 按标准SQL返回Map包装的对象。
	 * @param sql
	 * @param start
	 * @param size
	 * @return
	 */
	public List loadPageByForTransfReturnListMap(final String sql,final int start, final int size);
	
	//执行SQL的删除操作
	public void deleteBySql(final String sql);
}

