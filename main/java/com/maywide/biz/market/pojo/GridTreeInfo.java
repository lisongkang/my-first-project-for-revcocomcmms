package com.maywide.biz.market.pojo;

import com.maywide.biz.cons.BizConstant;

/**
 * <p>
 * 网格树对象
 * <p>
 * Create at 2015年9月21日
 * 
 * @author pengjianqiang
 */
public class GridTreeInfo {
    private Long    id;
    private String  gridcode;
    private String  name;
    private Long    gtype;   // 0-管理网格;1-小区网格;2-地址网格
    private Long    previd;
    private Long    statid;
    private String city;

    private String  iconSkin; // ztree图标css
    private boolean isHidden; // ztree节点是否隐藏(网格树配置功能不需要处理该属性，全部都显示)

    public GridTreeInfo() {
    }

    public GridTreeInfo(Long id, String gridcode, String name, Long gtype, Long previd, Long statid,String city) {
        this.id = id;
        this.gridcode = gridcode;
        this.name = name;
        this.gtype = gtype;
        this.previd = previd;
        this.statid = statid;
        this.city = city;
        int gtypeInt = gtype.intValue();
        this.iconSkin = BizConstant.GridType.GRID_TYPE_SKINS[gtypeInt];
        if (gtypeInt == BizConstant.GridType.PATCH_GRID) {
            this.isHidden = true;
        } else {
            this.isHidden = false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGridcode() {
        return gridcode;
    }

    public void setGridcode(String gridcode) {
        this.gridcode = gridcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGtype() {
        return gtype;
    }

    public void setGtype(Long gtype) {
        this.gtype = gtype;
    }

    public Long getPrevid() {
        return previd;
    }

    public void setPrevid(Long previd) {
        this.previd = previd;
    }

    public Long getStatid() {
        return statid;
    }

    public void setStatid(Long statid) {
        this.statid = statid;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
    
    
}