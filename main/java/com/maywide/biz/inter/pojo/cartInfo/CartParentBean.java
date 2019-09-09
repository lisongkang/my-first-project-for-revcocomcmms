package com.maywide.biz.inter.pojo.cartInfo;

public class CartParentBean {
	
	private String keyNo;
	
	private String addr;

	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	

	public CartParentBean( String keyNo, String addr) {
		super();
		this.keyNo = keyNo;
		this.addr = addr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addr == null) ? 0 : addr.hashCode());
		result = prime * result + ((keyNo == null) ? 0 : keyNo.hashCode());
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
		CartParentBean other = (CartParentBean) obj;
		if (addr == null) {
			if (other.addr != null)
				return false;
		} else if (!addr.equals(other.addr))
			return false;
		if (keyNo == null) {
			if (other.keyNo != null)
				return false;
		} else if (!keyNo.equals(other.keyNo))
			return false;
		return true;
	}
	
	

}
