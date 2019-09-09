package com.maywide.biz.prv.web.action;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.maywide.biz.core.entity.ApkPackage;
import com.maywide.biz.prv.service.PrvApkpackageService;
import com.maywide.common.web.action.BaseController;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.service.BaseService;


@MetaData(value = "安装包版本管理")
public class PrvApkpackageController extends BaseController<ApkPackage, Long>{

	@Autowired
	private PrvApkpackageService apkPackageService;
	
	@Override
	protected BaseService<ApkPackage, Long> getEntityService() {
		return apkPackageService;
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
//    	catalogCondtionService.checkEntity(bindingEntity);
    	apkPackageService.checkEntity(bindingEntity);
        return super.doSave();
    }

    @Override
    @MetaData("删除")
    public HttpHeaders doDelete() {
        return super.doDelete();
    }

    @Override
    @MetaData("查询")
    public HttpHeaders findByPage() {
        return super.findByPage();
    }

}
