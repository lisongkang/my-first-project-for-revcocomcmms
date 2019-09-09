package com.maywide.biz.salary.web.action;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.entity.AchievementBonus;
import com.maywide.biz.salary.entity.BaseWage;
import com.maywide.biz.salary.entity.BeforehandRealAudit;
import com.maywide.biz.salary.entity.OthersKpi;
import com.maywide.biz.salary.pojo.BeforehandRealBO;
import com.maywide.biz.salary.pojo.BeforehandRealDetailBO;
import com.maywide.biz.salary.service.AchievementBonusService;
import com.maywide.biz.salary.service.BaseWageService;
import com.maywide.biz.salary.service.BeforehandRealAuditService;
import com.maywide.biz.salary.service.BeforehandRealService;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 *<p> 
 *  预转实积分审核
 *<p>
 * Create at 2017-3-20
 *
 *@autor zhuangzhitang
 */
public class BeforehandRealAuditController extends SalaryController<BeforehandRealAudit,Long>{


	@Autowired
	private PersistentService persistentService;
	@Autowired
	private BeforehandRealAuditService beforehandRealAuditService;

	private BeforehandRealAudit beforehandRealAudit;  //搜索影射对象

	private BeforehandRealBO beforehandRealBO;
	@Override
	protected BaseService<BeforehandRealAudit, Long> getEntityService() {
		return beforehandRealAuditService;
	}
	/**
	 * 新增修改页
	 * @return
	 */
	public HttpHeaders edit(){
		return buildDefaultHttpHeaders("inputBasic");
	}
	/**
	 * 分页搜索
	 */
	public HttpHeaders findByPage(){
		List<BeforehandRealBO> list = new ArrayList<BeforehandRealBO>();
		Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
		try{
			setModel(beforehandRealAuditService.findByPage(beforehandRealAudit,pageable));
		}catch(Exception e){
			e.printStackTrace();
			setModel(new PageImpl<BeforehandRealBO>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders();
	}

	/**
	 * 新增或者修改
	 * @return
	 */
	@Override
	public HttpHeaders doSave(){
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		try{
				bindingEntity.setUpdateAt(new Date());
				bindingEntity.setUpdateBy(loginInfo.getLoginname());
			if(bindingEntity.getId()==null){
				bindingEntity.setDateMonth(beforehandRealBO.getCycleid());
				bindingEntity.setStatus("0");
				bindingEntity.setCommitUser(loginInfo.getOperid());
				bindingEntity.setOperid(Long.valueOf(beforehandRealBO.getOperid()));
				bindingEntity.setGrid(beforehandRealBO.getGrid());
				bindingEntity.setCity(beforehandRealBO.getCity());
				bindingEntity.setAreaid(beforehandRealBO.getAreaid());
				bindingEntity.setCreateBy(loginInfo.getLoginname());
				bindingEntity.setCreateAt(new Date());
				Boolean bool = beforehandRealAuditService.exists(bindingEntity.getGrid(),bindingEntity.getOperid(),bindingEntity.getDateMonth());
				if (bool) {
					throw new Exception(bindingEntity.getOperid()+"该网格人员在" + bindingEntity.getDateMonth() + "月份下已提交过审批!");
				}
				beforehandRealAuditService.save(bindingEntity);
			}else {
				beforehandRealAuditService.update(bindingEntity);
			}
			setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
		}catch(Exception e){
			e.printStackTrace();
			setModel(OperationResult.buildFailureResult("保存操作失败:"+e.getMessage()));
		}

		return buildDefaultHttpHeaders();
	}

	/**
	 * 批量保存审批记录
	 * @return
	 */
	public HttpHeaders batchAudit() {
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		try {
			List<BeforehandRealAudit> saves = new ArrayList<BeforehandRealAudit>();

			String[] citys = getParameter("citys").split(",");
			String[] areaids = getParameter("areaids").split(",");
			String[] grids = getParameter("grids").split(",");
			String[] operids = getParameter("operids").split(",");
			String[] cycleids = getParameter("cycleids").split(",");
			String auditUser = getParameter("auditUser");
			BeforehandRealAudit audit = null;
			for (int i=0;i<citys.length;i++) {
				audit = new BeforehandRealAudit();
				audit.setCity(citys[i]);
				audit.setAreaid(areaids[i]);
				audit.setGrid(grids[i]);
				audit.setOperid(Long.valueOf(operids[i]));
				audit.setDateMonth(cycleids[i]);
				audit.setAuditUser(Long.valueOf(auditUser));
				audit.setCommitUser(loginInfo.getOperid());
				audit.setStatus("0");
				audit.setCreateAt(new Date());
				audit.setCreateBy(loginInfo.getLoginname());
				audit.setUpdateAt(new Date());
				audit.setUpdateBy(loginInfo.getLoginname());
				Boolean bool = beforehandRealAuditService.exists(audit.getGrid(),audit.getOperid(),audit.getDateMonth());
				if (bool) {
					throw new Exception(audit.getOperid()+"该网格人员在" + audit.getDateMonth() + "月份下已提交过审批!");
				}
				saves.add(audit);
			}
			beforehandRealAuditService.save(saves);
			setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
		} catch (Exception e) {
			e.printStackTrace();
			setModel(OperationResult.buildFailureResult("保存操作失败,"+e.getMessage()));
		}

		return buildDefaultHttpHeaders();
	}

	/**
	 * 批量审批
	 * @return
	 */
	public HttpHeaders batchUpdate() {
		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
		try {
			String status = getParameter("status");
			if(StringUtils.isEmpty(status)){
				throw new Exception("状态不能为空!");
			}
			List<BeforehandRealAudit> saves = new ArrayList<BeforehandRealAudit>();
			String[] ids = getParameter("ids").split(",");

			BeforehandRealAudit audit = null;
			for (int i=0;i<ids.length;i++) {
				audit = new BeforehandRealAudit();
				audit.setId(Long.valueOf(ids[i]));
				audit.setStatus(status);
				audit.setUpdateAt(new Date());
				audit.setUpdateBy(loginInfo.getLoginname());
				beforehandRealAuditService.update(audit);
				saves.add(audit);
			}
			setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
		} catch (Exception e) {
			e.printStackTrace();
			setModel(OperationResult.buildFailureResult("保存操作失败,"+e.getMessage()));
		}

		return buildDefaultHttpHeaders();
	}
	public BeforehandRealAudit getBeforehandRealAudit() {
		return beforehandRealAudit;
	}

	public void setBeforehandRealAudit(BeforehandRealAudit beforehandRealAudit) {
		this.beforehandRealAudit = beforehandRealAudit;
	}

	public BeforehandRealBO getBeforehandRealBO() {
		return beforehandRealBO;
	}

	public void setBeforehandRealBO(BeforehandRealBO beforehandRealBO) {
		this.beforehandRealBO = beforehandRealBO;
	}
}
