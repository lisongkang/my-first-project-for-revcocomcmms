package com.maywide.biz.inter.pojo.querysalespkgknow;

import com.maywide.biz.cons.BizConstant;

public class ImSalepkgKnowsBO extends SalespkgKnowsBO {

	private String unitName;
	
	private int maxNumber;
	
	private int minNumber;
	
	private String scopeflag;
	
	private int defauNumber;
	
	private boolean modify = true;
	
	private boolean isOrdered;
	
	private boolean isChgAble;
	
	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

	public int getDefauNumber() {
		return defauNumber;
	}

	public void setDefauNumber(int defauNumber) {
		this.defauNumber = defauNumber;
	}

	public String getScopeflag() {
		return scopeflag;
	}

	public void setScopeflag(String scopeflag) {
		this.scopeflag = scopeflag;
		if(scopeflag != null && scopeflag.startsWith("1")){
			setModify(false);
			setDefauNumber(1);
		}
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
		if (BizConstant.PrdSalespkgKnowObjtype.SALESPKG.equals(getObjtype())) {
			if (unitName.equals("次")) {
				setMaxNumber(1);
				setMinNumber(0);
			} else if (unitName.equals("周期")) {
				setMaxNumber(12);
				setMinNumber(0);
			}
		} else {
			if (unitName.equals("周期")) {
				setMaxNumber(1);
				setMinNumber(0);
			} else if (unitName.equals("月")) {
				setMaxNumber(12);
				setMinNumber(0);
			}
		}
			
	}

	public int getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}

	public int getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(int minNumber) {
		this.minNumber = minNumber;
	}


	public ImSalepkgKnowsBO() {
		super();
	}
	
	public ImSalepkgKnowsBO(SalespkgKnowsBO bo){
		setBrief(bo.getBrief());
		setCity(bo.getCity());
		setFeeexp(bo.getFeeexp());
		setIcon(bo.getIcon());
		setKnowid(bo.getKnowid());
		setKnowname(bo.getKnowname());
		setKnowObjDet(bo.getKnowObjDet());
		setObjid(bo.getObjid());
		setObjtype(bo.getObjtype());
		setOpcodes(bo.getOpcodes());
		setPrice(bo.getPrice());
		setTocust(bo.getTocust());
		setWordexp(bo.getWordexp());
		setTocustTitle(bo.getTocustTitle());
		setIspostpone(bo.getIspostpone());
	}

	public boolean isChgAble() {
		return isChgAble;
	}

	public void setChgAble(boolean isChgAble) {
		this.isChgAble = isChgAble;
	}
	
	
}
