package com.maywide.biz.remind.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.market.entity.MarketBatch;

public class MarketBatchsBO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3787527413286866231L;
	
	private List<MarketBatch> marketBatchs = new ArrayList<MarketBatch>();

	public List<MarketBatch> getMarketBatchs() {
		return marketBatchs;
	}

	public void setMarketBatchs(List<MarketBatch> marketBatchs) {
		this.marketBatchs = marketBatchs;
	}
	
	
}
