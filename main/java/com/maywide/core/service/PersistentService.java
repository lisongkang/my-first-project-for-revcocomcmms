package com.maywide.core.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.maywide.core.cons.Constant;
import com.maywide.core.dao.support.EscColumnToBean;
import com.maywide.core.dao.support.Page;
import com.maywide.core.util.BeanUtils;
import com.maywide.core.util.DAOUtil;
import com.maywide.core.util.PubUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class PersistentService {
	private static final String ENTITY_ID = "id";
	private static final String PACKAGE_PREFIX = "com.maywide.";
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final boolean DEBUG_FLAG = false;
	private static Map<String, Long> idMap = new HashMap<String, Long>();

	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * 通过EntityManager的entityManager.getDelegate()获得session.
	 * 
	 * @return Session
	 */
	public Session getSession() throws Exception {
		return (Session) entityManager.getDelegate();
	}

	public void flushSession() throws Exception {
		getSession().flush();
	}

	public void clear() throws Exception {
		getSession().clear();
	}
	
	public void evit(Object entity) throws Exception {
		getSession().evict(entity);
	}

	public void save(Object entity) throws Exception {
		getSession().save(entity);
	}

	public void batchSave(List  entitys) throws Exception {
		if (entitys != null) {
			for (Object entity : entitys) {
				save(entity);
			}
		}
	}

	public void saveOrUpdate(Object entity) throws Exception {
		getSession().saveOrUpdate(entity);
	}
	
	public void delete(Object entity) throws Exception {
		entityManager.remove(entityManager.merge(entity));
//		entityManager.remove(entity);
	}

	public Object find(Class cls, Serializable pk) throws Exception {
		return entityManager.find(cls, pk);
	}

	/**
	 * 根据实体查询，返回java.util.List对象
	 * 
	 * See BaseDAO
	 * 
	 * @param entity
	 *            实体对象，包含要保存数据，可以是Param（继承实体），支持_le_,_lt_,_ge_,_gt_,_ne_,_like_
	 * @return List 列表数据
	 * @throws Exception
	 */
	public List find(Object entity) throws Exception {
		return find(entity, false);
	}

	public List find(Object entity, boolean cacheable) throws Exception {
		Assert.notNull(entity);
		Criteria c = createCriteria(entity);
		c.setCacheable(cacheable);
		return c.list();
	}

	public Object findUniqueObject(String sql, Object... params)
			throws Exception {
		return createQuery(sql, params).uniqueResult();
	}

	public List findObjectList(String sql, Object... params) throws Exception {
		return createQuery(sql, params).list();
	}

	/**
	 * 根据SQL查询数据，返回java.util.List对象 封装对象支持级联VO，需要封装的数据必须在sql的查询里面满足以下条件：
	 * 1、对于没有类似t.xxx，直接是xxx的查询结果，xxx必须与VO里面对应的field同名（大小写敏感）
	 * 2、对于有类似t.xxx这样的结果，必须采用别名，别名的格式必须是field1#field2，
	 * 其中field1为封装的class里面对应的field
	 * （对象），field2为field1对象里面相应的field，支持多级级联，多级之间以“#”分开
	 * 
	 * @param sql
	 *            sql语句
	 * @param cls
	 *            数据要封装的对象
	 * @param params
	 *            参数对象，不限制类型，对应sql中的"?"
	 * @return List 列表数据
	 * @throws Exception
	 */
	public List find(String sql, Class cls, Object... params) throws Exception {
		try {
			long start = System.currentTimeMillis();

			SQLQuery query = createQuery(sql, params);
			List l = null;
			if (sql.indexOf("#") > 0) {
				l = DAOUtil.convert(query.list(), sql, cls);
			} else {
				query.setResultTransformer(new EscColumnToBean(cls));
				l = query.list();
			}
			long end = System.currentTimeMillis();
			if (DEBUG_FLAG) {
				System.out.println("find:" + (end - start) + "ms");
			}
			return l;
		} catch (Exception e) {
			throw new Exception("查询数据库失败:" + e.getMessage(), e);
		}
	}

	/**
	 * 根据HQL查询数据，返回java.util.List对象
	 * 
	 * @param hql
	 *            hql语句
	 * @param params
	 *            参数对象，不限制类型，对应hql中的"?"
	 * @throws Exception
	 */
	public List find(String hql, Object... params) throws Exception {
		return createHqlQuery(hql, params).list();
	}

	/**
	 * 根据sql语句取得记录总数
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数对象，不限制类型，对应sql的"?"
	 * @return long 记录总数
	 * @throws Exception
	 */
	public long count(String sql, Object... params) throws Exception {
		String countSql = "";
		if (sql.toUpperCase().indexOf("UNION") > 0
				|| sql.toUpperCase().indexOf("INTERSECT") > 0) {
			countSql = "SELECT COUNT(*) FROM (" + sql + ")";
		} else {
			countSql = "SELECT COUNT(*) FROM "
					+ sql.substring(sql.toUpperCase().indexOf("FROM") + 4);
		}
		SQLQuery countQuery = createQuery(countSql, params);
		return totalCount(countQuery);
	}

	/**
	 * 根据实体查询记录总数 实体可以是Param（继承实体），支持_le_,_lt_,_ge_,_gt_,_ne_,_like_
	 * 
	 * @param entity
	 *            实体数据
	 * @return long 记录总数
	 * @throws Exception
	 */
	public long count(Object entity) throws Exception {
		Assert.notNull(entity);
		Criteria c = createCriteria(entity);
		return totalCount(c);
	}

	/**
	 * 调用存储过程（不包括任何返回值）
	 * 
	 * @param procedure
	 *            存储过程名字
	 * @param params
	 *            参数对象，不限制类型，对应procedure里面的"?"
	 * @throws Exception
	 */
	public void callProc(String procedure, Object... params) throws Exception {
		try {
			String sql = "{call " + procedure + "(";
			if (params != null) {
				for (Object param : params) {
					sql += "?, ";
				}
				if (sql.indexOf(",") > 0) {
					sql = sql.substring(0, sql.lastIndexOf(","));
				}
			}
			sql += ")}";
			int i = 0;
			SQLQuery query = getSession().createSQLQuery(sql);

			// javax.persistence.Query query =
			// entityManager.createNativeQuery(sql);
			if (params != null) {
				for (Object param : params) {
					query.setParameter(i++, param);
				}
			}
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("执行存储过程失败:{}", e.getMessage());
			throw e;
		}
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的参数.
	 * 
	 * @return 唯一对象.
	 */
	public Object findUnique(String hql, Object... values) throws Exception {
		return createHqlQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 * @return List 查询结果
	 */
	public int executeUpdate(String hql, Object... values) throws Exception {
		return createHqlQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行SQL操作.
	 * 
	 * @param sql
	 *            sql语句
	 * @param values
	 *            数量可变的参数
	 * @return int
	 */
	public int executeSql(String sql, Object... values) throws Exception {
		return createQuery(sql, values).executeUpdate();
	}

	/**
	 * 按属性查找对象列表.
	 * 
	 * @param cls
	 *            数据要封装的对象
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * 
	 * @return List 查询结果
	 */
	public List findByProperty(Class cls, String propertyName, Object value)
			throws Exception {
		Assert.hasText(propertyName);
		Criteria criteria = null;
		criteria = getSession().createCriteria(cls);
		criteria.setCacheable(true);
		return criteria.add(Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 按属性查找唯一对象.
	 * 
	 * @param cls
	 *            数据要封装的对象
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * 
	 * @return T 查询结果
	 */
	public Object findUniqueByProperty(Class cls, String propertyName,
			Object value) throws Exception {
		Assert.hasText(propertyName);
		Criteria criteria = getSession().createCriteria(cls);
		criteria.setCacheable(true);
		return criteria.add(Restrictions.eq(propertyName, value))
				.uniqueResult();
	}

	/**************************************** 私有方法 ***************************************************/

	/**
	 * 根据指定的实体及属性创建Criteria,后续可进行更多处理,辅助函数.
	 * 
	 * @param entity
	 *            保存了查询条件的entity对象.
	 * 
	 * @return Criteria
	 */
	protected Criteria createCriteria(Object entity) throws Exception {
		return createCriteria(null, null, entity);
	}

	/**
	 * 根据指定的实体及属性创建Criteria,后续可进行更多处理,辅助函数.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param criteria
	 *            Hibernate条件查询对象
	 * @param entity
	 *            保存了查询条件的entity对象.
	 * 
	 * @return Criteria
	 */
	private Criteria createCriteria(String propertyName, Criteria criteria,
			Object entity) throws Exception {
		if (criteria == null) {
			criteria = getSession().createCriteria(entity.getClass());
			//criteria = getSession().createCriteria(BeanUtils.getSuperClass(entity.getClass()));
		}
		
		String prefix = (StringUtils.isEmpty(propertyName)) ? "" : propertyName
				+ ".";
		// 如果属性不为空且不是主键，创建别名
		if (StringUtils.isNotEmpty(propertyName)
				&& !ENTITY_ID.equals(propertyName))
			criteria.createAlias(propertyName, propertyName);
		List<Field> fields = BeanUtils.getDeclaredFields(entity.getClass());

		for (Field property : fields) {
			try {
				String tmpPropertyName = property.getName();
				String entityProperty = BeanUtils
						.getPropertyName(tmpPropertyName);
				Object value = PropertyUtils.getProperty(entity,
						tmpPropertyName);
				if (value != null) {
					if (value instanceof String
							&& StringUtils.isEmpty(value.toString().trim()))
						continue;
					if ((value.getClass().getName().indexOf(PACKAGE_PREFIX) > -1)) {
						if (BeanUtils.isNullObject(value))
							continue;
						createCriteria(tmpPropertyName, criteria, value);
					} else if (tmpPropertyName.startsWith("_le_")) {
						criteria.add(Property.forName(prefix + entityProperty)
								.le(value));
					} else if (tmpPropertyName.startsWith("_lt_")) {
						criteria.add(Property.forName(prefix + entityProperty)
								.lt(value));
					} else if (tmpPropertyName.startsWith("_ge_")) {
						criteria.add(Property.forName(prefix + entityProperty)
								.ge(value));
					} else if (tmpPropertyName.startsWith("_gt_")) {
						criteria.add(Property.forName(prefix + entityProperty)
								.gt(value));
					} else if (tmpPropertyName.startsWith("_ne_")) {
						criteria.add(Property.forName(prefix + entityProperty)
								.ne(value));
					} else if (tmpPropertyName.startsWith("_like_")
							&& value instanceof String) {
						criteria.add(Property.forName(prefix + entityProperty)
								.like(value.toString(), MatchMode.ANYWHERE));
					} else if (tmpPropertyName.startsWith("_null_")) {
						criteria.add(Property.forName(prefix + entityProperty)
								.isNull());
					} else if (tmpPropertyName.startsWith("_notnull_")) {
						criteria.add(Property.forName(prefix + entityProperty)
								.isNotNull());
					} else if (tmpPropertyName.startsWith("_in_")
							&& value instanceof Set) {
						criteria.add(Property.forName(prefix + entityProperty)
								.in((Set) (value)));
					} else if (tmpPropertyName.startsWith("_notin_")
							&& value instanceof Set) {// 子查询
						DetachedCriteria dc = DetachedCriteria
								.forClass(BeanUtils.getSuperClass(entity
										.getClass()));
						dc.add(Property.forName(entityProperty).in(
								(Set) (value)));
						dc.setProjection(Property.forName(entityProperty));
						criteria.add(Property.forName(prefix + entityProperty)
								.notIn(dc));
					} else if (tmpPropertyName.startsWith("_empty_")) {// 子查询，相当于not
																		// exists
						criteria.add(Property.forName(prefix + entityProperty)
								.isEmpty());
					} else if (tmpPropertyName.startsWith("_not_empty_")) {// 子查询，相当于exists
						criteria.add(Property.forName(prefix + entityProperty)
								.isNotEmpty());
					} else if (tmpPropertyName.startsWith("_set_eq_")) {// 子查询目前只实现第一级
						String[] array = PubUtil.split(
								tmpPropertyName.substring("_set_eq_".length()),
								"_");
						String cascadePrefix = "";
						for (int i = 0; i < array.length - 1; i++) {
							criteria.createCriteria(array[i], array[i]);
							cascadePrefix += array[i] + ".";
						}
						criteria.add(Property.forName(
								cascadePrefix + array[array.length - 1]).eq(
								value));
					} else if (tmpPropertyName.startsWith("_orderby_")) {
						String orderby = (String) value;
						if (Constant.ORDER_DESC.equals(orderby))
							criteria.addOrder(Order.desc(prefix
									+ entityProperty));
						else
							criteria.addOrder(Order
									.asc(prefix + entityProperty));
					} else if (tmpPropertyName.equals(Constant.NOTNULL_FIELDS)
							&& value instanceof String) {
						addCriteriaField(criteria, prefix, (String) value,
								Constant.FLAG_NOTNULL);
					} else if (tmpPropertyName.equals(Constant.NULL_FIELDS)
							&& value instanceof String) {
						addCriteriaField(criteria, prefix, (String) value,
								Constant.FLAG_NULL);
					} else {
						if (value instanceof List || value instanceof Set
								|| value instanceof Map)
							continue;
						criteria.add(Property.forName(prefix + entityProperty)
								.eq(value));
					}
				}
			} catch (Exception ex) {
				logger.error("创建属性[" + property.getName()
						+ "]的查询条件出错，请确定属性名称及类型与查询对象一致");
				throw ex;
			}
		}
		return criteria;
	}

	private void addCriteriaField(Criteria criteria, String prefix,
			String value, int flag) throws Exception {
		String[] fields = PubUtil.split(value, Constant.COMMA);
		for (int i = 0; i < fields.length; i++) {
			if (flag == Constant.FLAG_NULL)
				criteria.add(Property.forName(prefix + fields[i]).eq(""));
			else
				criteria.add(Property.forName(prefix + fields[i]).ne(""));
		}
	}

	/**
	 * 根据HQL和参数生成Query
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	protected SQLQuery createQuery(String sql, Object... params)
			throws Exception {
		SQLQuery query = getSession().createSQLQuery(sql);
		int i = 0;
		if (params != null) {
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		return query;
	}

	/**
	 * 根据HQL和参数生成Query
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	protected Query createHqlQuery(String hql, Object... params)
			throws Exception {
		Query query = getSession().createQuery(hql);
		int i = 0;
		if (params != null) {
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}
		return query;
	}

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @param page
	 *            分页对象.
	 * @param c
	 *            查询条件
	 * @return page对象中的totalCount属性将赋值.
	 */
	protected int totalCount(Criteria c) throws Exception {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) BeanUtils.getFieldValue(impl, "orderEntries");
			BeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		Object cntObj = c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCountL = (Long) cntObj;
		int totalCount = (int) totalCountL;
		if (totalCount < 1)
			return -1;

		// 将之前的Projection和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}

		try {
			BeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @param page
	 *            分页对象.
	 * @param c
	 *            查询条件
	 * @return page对象中的totalCount属性将赋值.
	 */
	protected Long totalCount(SQLQuery query) throws Exception {
		Object cntObj = query.uniqueResult();

		if(cntObj instanceof java.math.BigDecimal){
			return ((java.math.BigDecimal)cntObj).longValue();
		}
		java.math.BigInteger totalCountBigInt = (java.math.BigInteger) cntObj;
		Long totalCount = totalCountBigInt.longValue();

		return totalCount;
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page find(Page page, Object entity) throws Exception {
		return find(page, entity, false);
	}

	public Page find(Page page, Object entity, boolean cacheable)
			throws Exception {
		try {
			long start = System.currentTimeMillis();

			Assert.notNull(entity);
			Criteria c = createCriteria(entity);
			c.setCacheable(cacheable);
			Page p = findByCriteria(page, c);

			long end = System.currentTimeMillis();
			// if (DEBUG_FLAG) {
			// System.out.println("find:" + (end - start) + "ms");
			// }
			return p;
		} catch (Exception e) {
			logger.error("查询数据库失败:{}", e.getMessage());
			throw e;
		}

	}

	protected Page findByCriteria(Page page, Criteria c) throws Exception {
		if (page.isAutoCount()) {
			page.setTotalCount(totalCount(c));
		}
		if (page.isFirstSetted()) {
			c.setFirstResult(page.getFirst());
		}
		if (page.isPageSizeSetted()) {
			c.setMaxResults(page.getPageSize());
		}

		if (page.isOrderBySetted()) {
			if (page.getInverseOrder().endsWith("asc"))
				c.addOrder(Order.asc(page.getOrderBy()));
			else {
				c.addOrder(Order.desc(page.getOrderBy()));
			}
			page.setOrder(page.getInverseOrder());
		}
		if (page.getTotalCount() > 0) {
			if (page.getTotalCount() < (page.getPageNo() - 1)
					* page.getPageSize() + 1)
				page.setPageNo(page.getPageNo() - 1);
			page.setResult(c.list());
		}

		return page;
	}

	public Page find(Page page, String sql, Class cls, Object... params)
			throws Exception {
		try {

			long start = System.currentTimeMillis();

			SQLQuery query = createQuery(sql, params);

			String countSql = "";
			countSql = "SELECT COUNT(*) FROM "
					+ sql.substring(sql.toUpperCase().indexOf("FROM") + 4);

			Pattern p = Pattern.compile("(?i)ORDER[\\s]*(?i)BY[^)]*");
			Matcher m = p.matcher(countSql);
			countSql = m.replaceAll("");
			if(countSql.contains("GROUP BY") || countSql.contains("group by")){
				StringBuffer sb = new StringBuffer(countSql);
				int position = countSql.indexOf("FROM")+4;
				sb.insert(position, "(SELECT 1 FROM");
				sb.append(") v");
				countSql = sb.toString();
			}
			SQLQuery countQuery = createQuery(countSql, params);
			Page pag = findByQuery(page, query, countQuery, cls, sql);

			long end = System.currentTimeMillis();
			if (DEBUG_FLAG) {
				System.out.println("find:" + (end - start) + "ms");
			}
			return pag;
		} catch (Exception e) {
			logger.error("查询数据库失败:{}", e.getMessage());
			throw e;
		}
	}

	protected Page findByQuery(Page page, SQLQuery query, SQLQuery countQuery,
			Class cls, String sql) throws Exception {
		if (page.isAutoCount()) {
			page.setTotalCount(totalCount(countQuery).intValue());
		}
		if (page.isFirstSetted()) {
			query.setFirstResult(page.getFirst());
		}
		if (page.isPageSizeSetted()) {
			query.setMaxResults(page.getPageSize());
		}

		if (page.getTotalCount() > 0) {
			if (sql.indexOf("#") > 0) {
				List list = query.list();
				page.setResult(DAOUtil.convert(list, sql, cls));
			} else {
				query.setResultTransformer(new EscColumnToBean(cls));
				page.setResult(query.list());
			}

		}

		return page;
	}

	public void update(Object entity) throws Exception {
		try {
			// entity = DAOUtil.cleanEntity(entity);
			Serializable pk = (Serializable) BeanUtils.getFieldValue(entity,
					"id");

			update(entity, pk, "id");
		} catch (Exception e) {
			this.logger.error("单表更新的时候出错：" + e.getMessage());
			throw new Exception("更新实体失败:" + e.getMessage(), e);
		}
	}

	public void update(Object entity, Serializable pk, String pkName)
			throws Exception {
		try {
			long start = System.currentTimeMillis();

			//entity = DAOUtil.cleanEntity(entity);
			Object o = getSession().load(entity.getClass(), pk);

			List<Field> field = BeanUtils.getDeclaredFields(entity.getClass());
			for (Field property : field) {
				if (!property.getName().equals(pkName)) {
					Object value = BeanUtils.getFieldValue(entity,
							property.getName());
					if (value != null) {
						Method setter = BeanUtils.getSetter(entity.getClass(),
								property);

						setter.invoke(o, new Object[] { value });
					}
				}
			}
			getSession().merge(o);
			flushSession();
			long end = System.currentTimeMillis();
			if (DEBUG_FLAG) {
				System.out.println("update:" + (end - start) + "ms");
			}
		} catch (Exception e) {
			throw new Exception("更新数据库失败:" + e.getMessage(), e);
		}
	}

	public Long getSequence(String seqName) throws Exception {
		try {
			String sql = "SELECT  NEXTVAL('" + seqName.trim()
					+ "') as newserialid  FROM dual";
			SQLQuery query = getSession().createSQLQuery(sql);
			query.addScalar("newserialid", LongType.INSTANCE);
			return ((Long) query.uniqueResult());
		} catch (Exception e) {
			throw new Exception("取得sequence失败:" + e.getMessage(), e);
		}
	}
	
	public Long getNextId(String tableName, String colunmName) {
		Long nextId = 1L;
		String key = tableName + "_" + colunmName;
		Long value = idMap.get(key);
		if (value == null) {
			String sql = "SELECT MAX(" + colunmName + ") FROM " + tableName;
			try {
				Object obj = findUniqueObject(sql, null);
				
				if (obj == null) {
					nextId = 0L;
				} else {
					nextId = new Long(obj.toString());
				}
				nextId += 1;
			} catch (Exception e) {
				return null;
			}
			
		} else {
			nextId = value + 1;
		}
		
		idMap.put(key, nextId);
    	
    	return nextId;
    }
}
