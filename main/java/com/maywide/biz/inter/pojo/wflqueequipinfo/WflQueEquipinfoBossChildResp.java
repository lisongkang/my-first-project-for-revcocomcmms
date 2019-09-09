package com.maywide.biz.inter.pojo.wflqueequipinfo;

import java.util.List;

public class WflQueEquipinfoBossChildResp {

    private String addr;
    private Long custorderid;
    private String memo;
    private String name;
    private Long operseq;
    private String opname;
    private Long servofferid;
    private Long servorderid;
    private int stepid;
    private List<RollDevListBean> rollDevList;
    private List<StepinfolistBean> stepinfolist;
    private String salespkgname;
    
    public String getSalespkgname() {
		return salespkgname;
	}

	public void setSalespkgname(String salespkgname) {
		this.salespkgname = salespkgname;
	}

	public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }


    public Long getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(Long custorderid) {
		this.custorderid = custorderid;
	}

	public Long getOperseq() {
		return operseq;
	}

	public void setOperseq(Long operseq) {
		this.operseq = operseq;
	}

	public Long getServofferid() {
		return servofferid;
	}

	public void setServofferid(Long servofferid) {
		this.servofferid = servofferid;
	}

	public Long getServorderid() {
		return servorderid;
	}

	public void setServorderid(Long servorderid) {
		this.servorderid = servorderid;
	}

	public int getStepid() {
        return stepid;
    }

    public void setStepid(int stepid) {
        this.stepid = stepid;
    }

    public List<RollDevListBean> getRollDevList() {
        return rollDevList;
    }

    public void setRollDevList(List<RollDevListBean> rollDevList) {
        this.rollDevList = rollDevList;
    }

    public List<StepinfolistBean> getStepinfolist() {
        return stepinfolist;
    }

    public void setStepinfolist(List<StepinfolistBean> stepinfolist) {
        this.stepinfolist = stepinfolist;
    }

}
