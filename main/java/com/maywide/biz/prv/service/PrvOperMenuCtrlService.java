package com.maywide.biz.prv.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.maywide.biz.prv.dao.PrvOperMenuCtrlDao;
import com.maywide.biz.prv.entity.PrvMenudef;
import com.maywide.biz.prv.entity.PrvOperMenuCtrl;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
public class PrvOperMenuCtrlService extends BaseService<PrvOperMenuCtrl, Long> {

	@Autowired
	PrvOperMenuCtrlDao dao;

	@Autowired
	PrvMenuService menuService;

	@Autowired
	PrvOperatorService operatorService;
	
	@Override
	protected BaseDao<PrvOperMenuCtrl, Long> getEntityDao() {
		return dao;
	}

	@Override
	public Page<PrvOperMenuCtrl> findByPage(GroupPropertyFilter groupPropertyFilter, Pageable pageable) {
		Page<PrvOperMenuCtrl> page = super.findByPage(groupPropertyFilter, pageable);
		if (page != null && page.hasContent()) {
			if (page.getContent() != null && !page.getContent().isEmpty()) {
				for (int i = 0; i < page.getContent().size(); i++) {
					PrvOperMenuCtrl menuCtrl = page.getContent().get(i);
					PrvMenudef menudef = (PrvMenudef) menuService.findEntity(PrvMenudef.class, menuCtrl.getMenuid());
					String menuname = menudef == null?"":StringUtils.isBlank(menudef.getName())?"":menudef.getName();
					PrvOperator operator = (PrvOperator) operatorService.findEntity(PrvOperator.class, menuCtrl.getOperid());
					String opername = operator == null?"":StringUtils.isBlank(operator.getName())?"":operator.getName();
					menuCtrl.setMenuname(menuname);
					menuCtrl.setOpername(opername);
				}
			}
		}
		return page;
	}

}
