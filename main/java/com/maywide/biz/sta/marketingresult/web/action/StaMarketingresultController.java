package com.maywide.biz.sta.marketingresult.web.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.inter.pojo.quecustorder.CustordersBO;
import com.maywide.biz.sta.marketingresult.bo.StaMarketResultParamBO;
import com.maywide.biz.sta.marketingresult.bo.StaMarketingResultBO;
import com.maywide.biz.sta.marketingresult.service.StaMarketingResultService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.exception.WebException;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.util.CheckUtils;
import com.maywide.core.util.DateUtils;
import com.maywide.core.web.SimpleController;
import com.maywide.tool.excel.ExportHelper;

@MetaData("[com.maywide].biz.sta.marketingresult统计")
public class StaMarketingresultController extends SimpleController {
	
    @Autowired
    private StaMarketingResultService smrService;
    
    @Autowired
	private ParamService paramService;
    
    private StaMarketResultParamBO staMarketResultParamBo;
    
    private Map<String, String> dictDataMap = new LinkedHashMap<String, String>();
    
    public StaMarketResultParamBO getStaMarketResultParamBo() {
        return staMarketResultParamBo;
    }

    public void setStaMarketResultParamBo(StaMarketResultParamBO staMarketResultParamBo) {
        this.staMarketResultParamBo = staMarketResultParamBo;
    }

    @MetaData("营销业务单查询")
    public HttpHeaders querySaleOrderList() throws Exception {
        try {
            Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
            setModel(smrService.querySaleOrderList(staMarketResultParamBo, pageable));
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    @MetaData("导出营销业务单查询")
    public HttpHeaders exportExcel() throws Exception {
    	//String result = "";
        try {
			ExportHelper export = new ExportHelper();
			String[] cols = new String[] { "订单编号", "客户名称","客户编号", "订单状态",
					"业务", "操作部门", "操作人", "操作时间", "产品/营销方案/商品", "金额", "支付状态",
					"支付时间","业务区","网格名称", "网格编码", };

			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE,
					PropertyFilter.buildSortFromHttpRequest(getRequest()));

			PageImpl<StaMarketingResultBO> page = smrService
					.querySaleOrderList(staMarketResultParamBo, pageable);
			if (page.getContent().size() > 0) {
				List<String[]> rowArr = new ArrayList<String[]>();
				for (StaMarketingResultBO bo : page.getContent()) {
					String[] row = new String[] {
							bo.getOrderid() + "",
							bo.getCustomer(),
                            bo.getCustid(),
							getCacheDictData("BIZ_CUSTORDER_ORDERSTATUS",
									bo.getOrderStatus()),
							getCacheDictData("BIZ_OPCODE", bo.getOpCode()),
							bo.getDepart(),
							bo.getOperator(),
							DateUtils.getFormatDateString(bo.getOptime(),
									"yyyy-MM-dd"), bo.getSaleName(),
							bo.getFees(),
							getCacheDictData("BIZ_PORTAL_ORDER_STATUS", bo.getPayStatus()),
							bo.getPaytime(),
                            bo.getAreaName(),
                            bo.getGridname(),
                            bo.getGridcode() };
					rowArr.add(row);
				}
				export.exportExcel("营销业务单", cols, rowArr, getResponse());
				//result = "success";
			}
           
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        return buildDefaultHttpHeaders();
    }
    
    
    private String getCacheDictData(String gcode,String mcode){
    	
        try {
        	if(dictDataMap.get(gcode+mcode)!=null){
        		return dictDataMap.get(gcode+mcode);//先从缓存里面取
        	}
        	List<PrvSysparam> list = paramService.getData(gcode,null,null,null);
        	if(null != list && list.size()>0){
        		for(PrvSysparam param : list){
        			dictDataMap.put(gcode+param.getMcode(),param.getMname());
        		}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dictDataMap.get(gcode+mcode);
    }
    

    @MetaData("查看详情")
    public HttpHeaders detailPage() {
        String custorderid = getParameter("custorderid");
        CustordersBO custOrder = null;
        try {
            CheckUtils.checkNull(custorderid, "订单编号不能为空");
            custOrder = smrService.queCustOrderDetail(Long.parseLong(custorderid)).get(0);
            setModel(custOrder);
        } catch (Exception e) {
            throw new WebException(e.getMessage(), e);
        }
        boolean isPayment = (custOrder != null && "BIZ_FEEIN".equals(custOrder.getOpcode()));
        return buildDefaultHttpHeaders(isPayment ? "order-paymentBasic" : "order-detail");
    }
}