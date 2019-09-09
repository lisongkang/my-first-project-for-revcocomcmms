package com.maywide.common.datatransfer.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.common.datatransfer.dao.MappingConfigDao;
import com.maywide.common.datatransfer.entity.MappingConfig;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class MappingConfigService extends BaseService<MappingConfig, Long> {
	@Autowired
    private MappingConfigDao queryMappingDao;
	@Autowired
    private PersistentService persistentService;
	
	@Override
    protected BaseDao<MappingConfig, Long> getEntityDao() {
		return queryMappingDao;
	}
	
	public List queryData(String className) throws Exception {
		List<MappingConfig> mappingList = persistentService.find(
				"FROM MappingConfig WHERE className = ? ", className);
		
		Criteria criteria = createCriteria(className, mappingList);
		
		return criteria.list();
	}
	
	private Criteria createCriteria(String className, List<MappingConfig> mappingList) throws Exception {
		Class clazz = Class.forName(className);
		Criteria criteria = persistentService.getSession().createCriteria(clazz);
		
		if (!mappingList.isEmpty()) {
			Object entityObj = clazz.newInstance();
			
			for (MappingConfig mapping : mappingList) {
				BeanUtil.setFieldValue(entityObj, mapping.getFieldName(), mapping.getFieldValue());
				
				Object value = PropertyUtils.getProperty(entityObj, mapping.getFieldName());
				
				if (value != null) {
					if (SysConstant.MatchType.LE.equals(mapping.getConditionType())) {
						criteria.add(Property.forName(mapping.getFieldName()).le(value));
					}
					if (SysConstant.MatchType.LT.equals(mapping.getConditionType())) {
						criteria.add(Property.forName(mapping.getFieldName()).lt(value));
					}
					if (SysConstant.MatchType.GT.equals(mapping.getConditionType())) {
						criteria.add(Property.forName(mapping.getFieldName()).gt(value));
					}
					if (SysConstant.MatchType.EQ.equals(mapping.getConditionType())) {
						criteria.add(Property.forName(mapping.getFieldName()).eq(value));
					}
					if (SysConstant.MatchType.GE.equals(mapping.getConditionType())) {
						criteria.add(Property.forName(mapping.getFieldName()).ge(value));
					}
					if (SysConstant.MatchType.NE.equals(mapping.getConditionType())) {
						criteria.add(Property.forName(mapping.getFieldName()).ne(value));
					}
					if (SysConstant.MatchType.LIKE.equals(mapping.getConditionType())) {
						criteria.add(Property.forName(mapping.getFieldName()).like(value.toString(), MatchMode.ANYWHERE));
					}
					if (SysConstant.MatchType.IN.equals(mapping.getConditionType())) {
						Set paramSet = new HashSet(Arrays.asList(value.toString().split(",")));
						criteria.add(Property.forName(mapping.getFieldName()).in(paramSet));
					}
					if (SysConstant.MatchType.NOTIN.equals(mapping.getConditionType())) {
						Set paramSet = new HashSet(Arrays.asList(value.toString().split(",")));
						
						DetachedCriteria dc = DetachedCriteria.forClass(clazz);
						dc.add(Property.forName(mapping.getFieldName()).in((Set) (value)));
						dc.setProjection(Property.forName(mapping.getFieldName()));
						criteria.add(Property.forName(mapping.getFieldName()).notIn(dc));
					}
				}
				
				if (SysConstant.MatchType.NOTNULL.equals(mapping.getConditionType())) {
					criteria.add(Property.forName(mapping.getFieldName()).isNotNull());
				}
				if (SysConstant.MatchType.NULL.equals(mapping.getConditionType())) {
					criteria.add(Property.forName(mapping.getFieldName()).isNull());
				}
			}
		}
		
		return criteria;
	}
}
