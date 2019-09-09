package com.maywide.biz.market.pojo;

import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;

/**
 * <p>
 * 地址网格的关联地址对象，因为按地市分表，所以不建指定实体表:
 * <p>
 * Create at 2015年11月24日
 * 
 * @author pengjianqiang
 */
public class BizGridHouse extends PersistableEntity<Long> implements Persistable<Long> {
    /**
     * 
     */
    private static final long serialVersionUID = -8201712920146489757L;

    @MetaData(value = "BOSS地址ID")
    private Long              houseid;

    @MetaData(value = "OSS地址ID")
    private String            addrid;

    @MetaData(value = "住宅状态")
    private String            status;

    @MetaData(value = "所属片区")
    private Long              patchid;

    @MetaData(value = "所属业务区")
    private Long              areaid;

    @MetaData(value = "网络结构")
    private String            netstruct;

    @MetaData(value = "完整住宅地址")
    private String            whladdr;

    @MetaData(value = "维护网格代码")
    private String            whgridcode;

    @MetaData(value = "业务网格编号")
    private String            ywgridcode;

    @MetaData(value = "网格ID")
    private Long              gridid;

    private String            areaName;
    private String            patchName;

    @Override
    public Long getId() {
        return houseid;
    }

    @Override
    public String getDisplay() {
        return null;
    }

    public Long getHouseid() {
        return houseid;
    }

    public void setHouseid(Long houseid) {
        this.houseid = houseid;
    }

    public String getAddrid() {
        return addrid;
    }

    public void setAddrid(String addrid) {
        this.addrid = addrid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPatchid() {
        return patchid;
    }

    public void setPatchid(Long patchid) {
        this.patchid = patchid;
    }

    public Long getAreaid() {
        return areaid;
    }

    public void setAreaid(Long areaid) {
        this.areaid = areaid;
    }

    public String getNetstruct() {
        return netstruct;
    }

    public void setNetstruct(String netstruct) {
        this.netstruct = netstruct;
    }

    public String getWhladdr() {
        return whladdr;
    }

    public void setWhladdr(String whladdr) {
        this.whladdr = whladdr;
    }

    public String getWhgridcode() {
        return whgridcode;
    }

    public void setWhgridcode(String whgridcode) {
        this.whgridcode = whgridcode;
    }

    public String getYwgridcode() {
        return ywgridcode;
    }

    public void setYwgridcode(String ywgridcode) {
        this.ywgridcode = ywgridcode;
    }

    public Long getGridid() {
        return gridid;
    }

    public void setGridid(Long gridid) {
        this.gridid = gridid;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }
}