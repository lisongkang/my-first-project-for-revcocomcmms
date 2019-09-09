package com.maywide.biz.inter.pojo.quecustorder;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.inter.pojo.IPrintCondition;
import com.maywide.biz.inter.pojo.queOrderPkgInfo.QueOrderPkgInfoResp;

public class CustordersBO implements java.io.Serializable,IPrintCondition {
    private String         custorderid;    // 订单id
    private String         ordercode;      // 订单编号
    private String         syncmode;       // 同步方式
    private String         bossserialno;   // boss流水号
    private String         orderstatus;    // 订单状态
    private String         orderstatusname; // 订单状态名称
    private String         canceltime;     // 取消时间
    private String         lockoper;       // 锁定操作员
    private String         lockopername;   // 锁定操作员名称
    private String         custid;         // 客户id
    private String         custname;       // 客户姓名
    private String         opcode;         // 操作代码
    private String         opcodename;     // 操作代码名称
    private String         optime;         // 操作时间
    private String         operator;       // 操作员
    private String         operatorname;   // 操作员姓名
    private String         oprdep;         // 操作员部门
    private String         oprdepname;     // 操作员部门名称
    private String         areaid;         // 业务区
    private String         areaname;       // 业务区名称
    private String         gridid;         // 网格id
    private String         gridname;       // 网格名称
    private String         descrip;        // 业务说明
    private String         addr;           // 订单地址
    private String         city;           // 所属分公司
    private String         cityname;       // 所属分公司名称
    private String         fees;           // 订单金额
    private String         paystatus;      // 支付状态
    private String         paystatusname;  // 支付状态名

    private String         keyno;          // 缴费设备号
    private String         owefees;        // 缴纳欠费金额
    private String         payway;         // 支付方式
    private String         paywayname;     // 支付方式名称

    private String         salePkgs;       // 套餐信息， 多个套餐以逗号分隔
    private String         logicdevno;     // 智能卡号/设备号
    private String         bossResult;     //BOSS处理返回结果
    private String 		   describe;       //业务注释说明
    private Long 		   resporderid;
    
    private String 		  serialno;
    private String 		  verifyphone;		//接收验证码的手机号
    private String 		  hasscreenshot;	//是否存在屏幕截图
    private String		  printShow;		//是否显示无纸化打印按钮
    private String 		  printed;			//是否进行过无纸化打印
    private String 		  printedinv;		//是否打印了发票
    private String		  extras;			
    private String        rollbackEnable;
    private String		  muiltpay;
    private String			payFees;
    private ApplyBankBO    applyBankBO;    // 银行信息

    private CustorderDetBO custorderDet;
    
    private List<QueOrderPkgInfoResp> pkgInfos;

    public String getCustorderid() {
        return custorderid;
    }

    public void setCustorderid(String custorderid) {
        this.custorderid = custorderid;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getSyncmode() {
        return syncmode;
    }

    public void setSyncmode(String syncmode) {
        this.syncmode = syncmode;
    }

    public String getBossserialno() {
        return bossserialno;
    }

    public String getMuiltpay() {
		return muiltpay;
	}

	public void setMuiltpay(String muiltpay) {
		this.muiltpay = muiltpay;
	}

	public String getPayFees() {
		return payFees;
	}

	public void setPayFees(String payFees) {
		this.payFees = payFees;
	}

	public void setBossserialno(String bossserialno) {
        this.bossserialno = bossserialno;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getOrderstatusname() {
        return orderstatusname;
    }

    public void setOrderstatusname(String orderstatusname) {
        this.orderstatusname = orderstatusname;
    }

    public String getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(String canceltime) {
        this.canceltime = canceltime;
    }

    public String getLockoper() {
        return lockoper;
    }

    public void setLockoper(String lockoper) {
        this.lockoper = lockoper;
    }

    public String getLockopername() {
        return lockopername;
    }

    public void setLockopername(String lockopername) {
        this.lockopername = lockopername;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getOpcodename() {
        return opcodename;
    }

    public void setOpcodename(String opcodename) {
        this.opcodename = opcodename;
    }

    public String getOptime() {
        return optime;
    }

    public void setOptime(String optime) {
        this.optime = optime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getOprdep() {
        return oprdep;
    }

    public void setOprdep(String oprdep) {
        this.oprdep = oprdep;
    }

    public String getOprdepname() {
        return oprdepname;
    }

    public void setOprdepname(String oprdepname) {
        this.oprdepname = oprdepname;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getGridid() {
        return gridid;
    }

    public void setGridid(String gridid) {
        this.gridid = gridid;
    }

    public String getGridname() {
        return gridname;
    }

    public void setGridname(String gridname) {
        this.gridname = gridname;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public CustorderDetBO getCustorderDet() {
        return custorderDet;
    }

    public void setCustorderDet(CustorderDetBO custorderDet) {
        this.custorderDet = custorderDet;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getPaystatusname() {
        return paystatusname;
    }

    public void setPaystatusname(String paystatusname) {
        this.paystatusname = paystatusname;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getOwefees() {
        List<ApplyArrearBO> applyArrears = getCustorderDet().getApplyArrears();
        if (applyArrears != null && applyArrears.size() > 0) {
            ApplyArrearBO applyArrearBO = applyArrears.get(0);
            owefees = applyArrearBO.getFees();
        }
        return owefees;
    }

    public void setOwefees(String owefees) {
        this.owefees = owefees;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getPaywayname() {
        return paywayname;
    }

    public void setPaywayname(String paywayname) {
        this.paywayname = paywayname;
    }

    public String getKeyno() {
        List<ApplyArrearBO> applyArrears = getCustorderDet().getApplyArrears();
        if (applyArrears != null && applyArrears.size() > 0) {
            ApplyArrearBO applyArrearBO = applyArrears.get(0);
            keyno = applyArrearBO.getKeyno();
        }
        return keyno;
    }

    public void setKeyno(String keyno) {
        this.keyno = keyno;
    }

    public String getSalePkgs() {
        salePkgs = "";

        // 如果是点通/体验授权，则取ApplyTmpopenBO的plan
        if (BizConstant.BossInterfaceService.BIZ_TEMPOPEN.equals(opcode)) {
            List<ApplyTmpopenBO> applyTmpopenBOs = getCustorderDet().getApplyTmpopens();
            if (applyTmpopenBOs != null && applyTmpopenBOs.size() > 0) {
                ApplyTmpopenBO applyTmpopenBO = applyTmpopenBOs.get(0);
                salePkgs = applyTmpopenBO.getPlanname();
            }

            return salePkgs;
        }

        List<ApplyProductBO> applyProductBOs = getCustorderDet().getApplyProducts();
        if (applyProductBOs != null && applyProductBOs.size() > 0) {
            for (ApplyProductBO applyProductBO : applyProductBOs) {
                if (!StringUtils.isEmpty(applyProductBO.getKnowname())) {
                    salePkgs += "," + applyProductBO.getKnowname();
                } else if (!StringUtils.isEmpty(applyProductBO.getPname())) {
                    salePkgs += "," + applyProductBO.getPname();
                }
            }

            if (!StringUtils.isEmpty(salePkgs)) {
                salePkgs = salePkgs.substring(1, salePkgs.length());
            }
        }
        return salePkgs;
    }

    public void setSalePkgs(String salePkgs) {
        this.salePkgs = salePkgs;
    }

    public String getLogicdevno() {
        // 如果是缴费，则取ApplyArrearBO的keyno
        if (BizConstant.BossInterfaceService.BIZ_FEEIN.equals(opcode)) {
            return getKeyno();
        }

        // 否则取ApplyProductBO的logicdevno
        List<ApplyProductBO> applyProductBOs = getCustorderDet().getApplyProducts();
        logicdevno = "";
        if (applyProductBOs != null && applyProductBOs.size() > 0) {
            ApplyProductBO applyProductBO = applyProductBOs.get(0);
            logicdevno = applyProductBO.getLogicdevno();
        }

        return logicdevno;
    }

    public void setLogicdevno(String logicdevno) {
        this.logicdevno = logicdevno;
    }
    
    

    public String getBossResult() {
		return bossResult;
	}

	public void setBossResult(String bossResult) {
		this.bossResult = bossResult;
	}

	public ApplyBankBO getApplyBankBO() {
        List<ApplyBankBO> applyBankBOs = getCustorderDet().getApplyBanks();
        applyBankBO = null;
        if (applyBankBOs != null && applyBankBOs.size() > 0) {
            applyBankBO = applyBankBOs.get(0);

        }
        return applyBankBO;
    }

    public void setApplyBankBO(ApplyBankBO applyBankBO) {
        this.applyBankBO = applyBankBO;
    }

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Long getResporderid() {
		return resporderid;
	}

	public void setResporderid(Long resporderid) {
		this.resporderid = resporderid;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}

	public String getHasscreenshot() {
		return hasscreenshot;
	}

	public void setHasscreenshot(String hasscreenshot) {
		this.hasscreenshot = hasscreenshot;
	}


	public String getPrinted() {
		return printed;
	}

	public void setPrinted(String printed) {
		this.printed = printed;
	}

	public String getPrintedinv() {
		return printedinv;
	}

	public void setPrintedinv(String printedinv) {
		this.printedinv = printedinv;
	}

	@Override
	public String getPrintShow() {
		return printShow;
	}

	@Override
	public void setPrintShow(String printShow) {
		this.printShow = printShow;
	}
	

	public List<QueOrderPkgInfoResp> getPkgInfos() {
		return pkgInfos;
	}

	public void setPkgInfos(List<QueOrderPkgInfoResp> pkgInfos) {
		this.pkgInfos = pkgInfos;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public String getRollbackEnable() {
		return rollbackEnable;
	}

	public void setRollbackEnable(String rollbackEnable) {
		this.rollbackEnable = rollbackEnable;
	}
	
	
    
    
}
