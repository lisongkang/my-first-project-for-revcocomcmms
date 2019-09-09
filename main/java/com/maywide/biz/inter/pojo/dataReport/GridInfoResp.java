package com.maywide.biz.inter.pojo.dataReport;

public class GridInfoResp {

	private Long gridId; 
	
	private String gridCode;
	
	private String gridName;
	
	private String gType;

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getgType() {
		return gType;
	}

	public void setgType(String gType) {
		this.gType = gType;
	}

	public Long getGridId() {
		return gridId;
	}

	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gridId == null) ? 0 : gridId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GridInfoResp other = (GridInfoResp) obj;
		if (gridId == null) {
			if (other.gridId != null)
				return false;
		} else if (!gridId.equals(other.gridId))
			return false;
		return true;
	}
	
	
	
}
