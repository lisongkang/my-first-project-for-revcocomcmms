package com.maywide.biz.ass.target.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maywide.biz.ass.target.bo.AssTargetPatchBo;
import com.maywide.biz.ass.target.entity.AssTargetStore;
import com.maywide.biz.ass.target.entity.AssTargetTogrid;
import com.maywide.biz.ass.target.service.AssTargetStoreService;
import com.maywide.biz.ass.target.service.AssTargetTocityService;
import com.maywide.biz.ass.target.service.AssTargetTogridService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtil;
import com.maywide.core.util.DateUtils;
import com.maywide.core.web.view.OperationResult;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@MetaData("[com.maywide].biz.ass.store.entity.AssTargetTogrid管理")
public class AssTargetTogridController extends BaseController<AssTargetTogrid,Long> {

	@Autowired
    private AssTargetStoreService assTargetStoreService;
	
    @Autowired
    private AssTargetTocityService assTargetTocityService;
    
    @Autowired
    private AssTargetTogridService assTargetTogridService;
    
    @Autowired
    private PersistentService persistentService;
    
    private AssTargetPatchBo assTargetPatchBo;
    
    public AssTargetPatchBo getAssTargetPatchBo() {
		return assTargetPatchBo;
	}

	public void setAssTargetPatchBo(AssTargetPatchBo assTargetPatchBo) {
		this.assTargetPatchBo = assTargetPatchBo;
	}

	@Override
    protected BaseService<AssTargetTogrid, Long> getEntityService() {
        return assTargetTogridService;
    }
    
    @Override
    protected void checkEntityAclPermission(AssTargetTogrid entity) {
        // TODO Add acl check code logic
    }

	public HttpHeaders index(){
		return buildDefaultHttpHeaders("index");
	}
	
	
    @MetaData("[TODO方法作用]")
    public HttpHeaders todo() {
        //TODO
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
    public HttpHeaders doSave()  {
    	
    	HttpHeaders httpHeaders = buildDefaultHttpHeaders();
		try {
			// json串转换AssTargetPatchBo对象
			String dataJson = getParameter("data");
			AssTargetPatchBo bo = (AssTargetPatchBo) BeanUtil.jsonToObject(dataJson, AssTargetPatchBo.class);
			
			if (bo.getId()!=null) {
				AssTargetTogrid togrid=assTargetTogridService.findOne(bo.getId());
				if(togrid.getStatus()==1){
					throw new Exception("数据更新失败，指标已启用不能修改");
				}
				togrid.setAssNum(bo.getAssNum());
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				togrid.setCycleNum(DateUtils.parseDate(bo.getCycleNumStr(), sdf));
				togrid.setGridId(Long.valueOf(bo.getGridId()));
				
				assTargetTogridService.save(togrid);
				
				setModel(OperationResult.buildSuccessResult("数据更新成功"));
				
				return httpHeaders;
			}
			else{
				throw new Exception("数据更新失败，id不存在");
			}

		} catch (Exception e) {
			e.printStackTrace();
			setModel(OperationResult.buildFailureResult(e.getMessage()));
			return httpHeaders;
		}
    }
    
    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
    	
    	try {
    		String[] ids=getParameterIds();
    		assTargetTogridService.doDel(ids);//逻辑删除
		}
    	catch (Exception e) {
			setModel(OperationResult.buildFailureResult("删除异常，请联系管理员"));
			e.printStackTrace();
		}
    	
    	setModel(OperationResult.buildSuccessResult("删除成功"));
    	
    	return buildDefaultHttpHeaders();
    }
    
    @MetaData("指标下发")
    public HttpHeaders targetToPatch()  {
		try {
			String assId=this.getParameter("assId");
			String gridids=this.getParameter("gridids");
			String cyclenumStr=this.getParameter("cyclenumStr");
			String assNum=this.getParameter("assNum");
			
			this.assTargetPatchBo=new AssTargetPatchBo();
			this.assTargetPatchBo.setAssId(Long.valueOf(assId));
			this.assTargetPatchBo.setAssNum(assNum);
			this.assTargetPatchBo.setCycleNumStr(cyclenumStr);
			this.assTargetPatchBo.setGridids(gridids);
			
			if(!assTargetTogridService.checkBeforePatch(assTargetPatchBo)){
				assTargetTogridService.targetToPatch(assTargetPatchBo);
			}
			else{ 
				throw new Exception("指标下发出现数据重复,请检查参数");
			}
			setModel(OperationResult.buildSuccessResult("指标下发网格成功"));
		}
		catch (Exception e) {
			e.printStackTrace();
			setModel(OperationResult.buildFailureResult(e.getMessage()));
			return buildDefaultHttpHeaders();
		}
		return buildDefaultHttpHeaders();
    }
    
    @MetaData("修改下发状态 启用/停用")
    public HttpHeaders changeSt()  {
    	
    	String ids = getParameter("rowid");
		String status = getParameter("status");
		try{
			
			for(String id:ids.split(",")){
				
				AssTargetTogrid grid=assTargetTogridService.findOne(Long.valueOf(id));
				if(grid.getStatus()!=Integer.valueOf(status)){
					AssTargetPatchBo bo=new AssTargetPatchBo();
					bo.setId(Long.valueOf(id));
					bo.setStatus(Integer.valueOf(status));
					assTargetTogridService.changeSt(bo);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			setModel(OperationResult.buildFailureResult(Integer.valueOf(status)==1?"启用失败，请联系管理员":"停用失败，请联系管理员"));
			return buildDefaultHttpHeaders();
		}
		
		setModel(OperationResult.buildSuccessResult(Integer.valueOf(status)==1?"启用成功":"停用成功"));
		
		return buildDefaultHttpHeaders();
    }
    
    
    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
		Pageable pageable = PropertyFilter
				.buildPageableFromHttpRequest(getRequest());
		try {
			PageImpl<AssTargetPatchBo> pageResult = assTargetTogridService
					.getTargetTogrid(this.assTargetPatchBo, pageable);
			setModel(pageResult);

			return buildDefaultHttpHeaders("index");

		} catch (Exception e) {
			List<AssTargetPatchBo> list = new ArrayList<AssTargetPatchBo>();
			setModel(new PageImpl<AssTargetPatchBo>(list, pageable, 1));
		}
		return buildDefaultHttpHeaders("index");
	
    }
    
    /**
     * 在指标下发界面，获取当前市可下发的指标信息
     * @author:liaoxiangjun
     * @return
     */
    public HttpHeaders getCanPatchStores(){
    	
		List<AssTargetStore> result = new ArrayList<AssTargetStore>();
		try {
			Map<String, String> param = new HashMap<String, String>();
			Pageable pageable = new PageRequest(1, Integer.MAX_VALUE);
			PageImpl<AssTargetStore> pageResult = assTargetTogridService.getPatchStores(param, pageable);
			for(AssTargetStore item : pageResult.getContent()){
				result.add(item);
			}
			setModel(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders();
	}
    
    
    
    /**
     * 获取可下发的指标信息
     * @author:liaoxiangjun
     * @return
     */
    public HttpHeaders getGridStores(){
		List<AssTargetStore> result = new ArrayList<AssTargetStore>();
		try {
			Map<String, String> param = new HashMap<String, String>();
			result = assTargetTogridService.getGridStores(param);
			setModel(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpHeaders();
	}
    
    
    /**
     * 获取最新的指标信息
     * @author:liaoxiangjun
     * @return
     */
    public HttpHeaders toPatchPage(){
		return buildDefaultHttpHeaders("toPatch");
	}
    
    
    public HttpHeaders toGridTreePage(){
		return buildDefaultHttpHeaders("tree");
	}
    
}