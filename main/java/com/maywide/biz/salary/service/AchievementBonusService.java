package com.maywide.biz.salary.service;

import com.maywide.biz.salary.dao.AchievementBonusDao;
import com.maywide.biz.salary.entity.AchievementBonus;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *<p> 
 *  运维薪酬
 *<p>
 *
 */
@Service
@Transactional
public class AchievementBonusService extends BaseService<AchievementBonus,Long>{


	@Autowired
	private AchievementBonusDao achievementBonusDao;

	@Autowired
	private PersistentService persistentService;
	
	@Override
	protected BaseDao<AchievementBonus, Long> getEntityDao() {
		return achievementBonusDao;
	}

	@SuppressWarnings("unchecked")
	public PageImpl<AchievementBonus> findByPage(AchievementBonus achievementBonus,
			Pageable pageable) throws Exception {
		PageImpl<AchievementBonus> pageResult = null;
        Page<AchievementBonus> page = new Page<AchievementBonus>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("select * from SALARY_EXPLICATION_CONFIG where 1=1");
        if(achievementBonus!=null){
			if(StringUtils.isNotEmpty(achievementBonus.getCity())){
				sql.append(" and city=?");
				paramList.add(achievementBonus.getCity());
			}
			if(StringUtils.isNotEmpty(achievementBonus.getAreaid())){
				sql.append(" and areaid=?");
				paramList.add(achievementBonus.getAreaid());
			}
			if(StringUtils.isNotEmpty(achievementBonus.getGrid())){
				sql.append(" and grid=?");
				paramList.add(achievementBonus.getGrid());
			}
        }
        sql.append(" order by id desc");

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
		List<AchievementBonus> resultList = page.getResult();
		if (null != page && null != resultList) {
			int total = page.getTotalCount();
			pageResult = new PageImpl<AchievementBonus>(resultList, pageable, total);
		}else{
			pageResult = new PageImpl<AchievementBonus>(new ArrayList<AchievementBonus>(), pageable, 0);
		}
        return pageResult;
	}

	public Boolean exists(String grid,Long operid,String dateMonth) throws Exception {
		if(StringUtils.isEmpty(grid) || operid == null ||
				StringUtils.isEmpty(dateMonth)){
			throw new Exception("参数不完整,无法校验!");
		}
		String sql="select 1 from salary_achievement_bonus where grid=? and operid=? and date_month=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(grid);
		paramList.add(operid);
		paramList.add(dateMonth);
		Long count = persistentService.count(sql.toString(), paramList.toArray());
		if(count>0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
