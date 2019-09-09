package com.maywide.biz.inter.pojo.wflqueequipdetinfo;

import java.io.Serializable;
import java.util.List;

public class WflQueEquipdetinfoBossResp implements Serializable {
	/*private List<WflQueEquipdetinfoInterChileResp> list;

	public List<WflQueEquipdetinfoInterChileResp> getList() {
		return list;
	}

	public void setList(List<WflQueEquipdetinfoInterChileResp> list) {
		this.list = list;
	} */
	/**
     * currentPage : 1
     * pagesize : 10
     * queEquipDetBo : [{"addr":"广东省广州市天河区车陂街道东圃大马路雅怡街138号雅怡居D2栋203房","apptime":"2019-04-01T00:00:00","areaid":100,"bizcode":"BIZ_USER_NEW","bizmemo":null,"biztime":"2019-04-01T14:15:58","channel":"BOSS","custid":3600037216,"custname":"刘伟","custorderid":"71941454","feeStatus":"N","fees":"488","houseid":6518000,"isappoint":"N","markno":"GZ117002024","mobile":"18665555568","opdepart":"2866010","operid":"104126","patchid":1505,"payway":null,"phone":null,"salespkgname":"U169增值_网关新装(0)(直属)_200","serialno":"610412600000000006Gt","servorderid":123731901},{"addr":"广东省广州市天河区车陂街道东圃大马路雅怡街138号雅怡居D2栋203房","apptime":"2019-04-01T00:00:00","areaid":100,"bizcode":"BIZ_USER_NEW","bizmemo":null,"biztime":"2019-04-01T14:15:58","channel":"BOSS","custid":3600037216,"custname":"刘伟","custorderid":"71941454","feeStatus":"N","fees":"488","houseid":6518000,"isappoint":"N","markno":"GZ117002024","mobile":"18665555568","opdepart":"2866010","operid":"104126","patchid":1505,"payway":null,"phone":null,"salespkgname":"U49增值_新装主机(0)(直属)_288","serialno":"610412600000000006Gt","servorderid":123731898}]
     * totalRecords : 2
     */

    private int currentPage;
    private int pagesize;
    private int totalRecords;
    private List<QueEquipDetBoBean> queEquipDetBo;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<QueEquipDetBoBean> getQueEquipDetBo() {
        return queEquipDetBo;
    }

    public void setQueEquipDetBo(List<QueEquipDetBoBean> queEquipDetBo) {
        this.queEquipDetBo = queEquipDetBo;
    }
}
