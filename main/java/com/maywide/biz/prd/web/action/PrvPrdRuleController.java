package com.maywide.biz.prd.web.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.prd.entity.PrvPrdRule;
import com.maywide.biz.prd.service.PrvPrdRuleService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.PinYinUtils;
import com.maywide.core.web.view.OperationResult;

@MetaData("[com.maywide].biz.prd.entity.PrvPrdRule管理")
public class PrvPrdRuleController extends BaseController<PrvPrdRule, Long> {

	@Autowired
	private PrvPrdRuleService prvPrdRuleService;
	@Autowired
	private PersistentService persistentService;

	public HttpHeaders index() {
		return buildDefaultHttpHeaders("index");
	}

	@Override
	protected BaseService<PrvPrdRule, Long> getEntityService() {
		return prvPrdRuleService;
	}

	@Override
	protected void checkEntityAclPermission(PrvPrdRule entity) {
	}

	@MetaData("[TODO方法作用]")
	public HttpHeaders todo() {
		setModel(OperationResult.buildSuccessResult("TODO操作完成"));
		return buildDefaultHttpHeaders();
	}

	@Override
	@MetaData("创建")
	public HttpHeaders doCreate() {
		return super.doCreate();
	}

	@Override
	@MetaData("更新")
	public HttpHeaders doUpdate() {
		return super.doUpdate();
	}

	@Override
	@MetaData("保存")
	public HttpHeaders doSave() {
		PrvPrdRule entity = (PrvPrdRule) bindingEntity;
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		if (entity.isNew()) {
			entity.setCreateoperid(loginInfo.getOperid());
			entity.setCreatetime(new Date());
		}
		try {
			getEntityService().save(entity);
			setModel(OperationResult.buildSuccessResult("数据保存成功", entity));
		} catch (Exception e) {
			e.printStackTrace();
			String exceMsg = e.getMessage();
			setModel(OperationResult.buildFailureResult(exceMsg));
		}
		return buildDefaultHttpHeaders();
	}

	@Override
	@MetaData("删除")
	public HttpHeaders doDelete() {
		return super.doDelete();
	}

	@Override
	@MetaData("查询")
	public HttpHeaders findByPage() {
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try {
			setModel(prvPrdRuleService.findPrdRuleByPage(pageable));
		} catch (Exception e) {
			setModel(OperationResult.buildFailureResult(e.getMessage()));
			e.printStackTrace();
		}
		return buildDefaultHttpHeaders();
	}

	public Map<String, String> getCityAreaMap() {
		Map<String, String> areaMap = new LinkedHashMap<String, String>();
		String hql = "FROM PrvArea WHERE city = ? ORDER BY id";
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		List<PrvArea> areaList;

		try {
			areaList = persistentService.find(hql, loginInfo.getCity());

			Collections.sort(areaList, new Comparator<PrvArea>() {
				public int compare(PrvArea o1, PrvArea o2) {
					PrvArea param1 = (PrvArea) o1;
					PrvArea param2 = (PrvArea) o2;

					String pinyin1 = PinYinUtils.converterToFirstSpell(param1.getName());
					String pinyin2 = PinYinUtils.converterToFirstSpell(param2.getName());

					int rtnval = pinyin1.compareTo(pinyin2);

					return rtnval;
				}
			});

			for (PrvArea param : areaList) {
				areaMap.put(param.getId().toString(), param.getName());
			}

			return areaMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaMap;

	}

}