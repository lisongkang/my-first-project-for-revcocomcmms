package com.maywide.common.sys.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class MenuVO.
 */
public class NavMenuVO {

    private String id;
    
    /** 菜单代码，用于前端JS控制 */
    private String code;

    /** 菜单名称，菜单显示的字面值. */
    private String name;

    /** 图标，基于Bootstrap3，如fa-user */
    private String icon;

    /**
     * 菜单URL.
     */
    private String url = "";

    /** 父节点. */
    private NavMenuVO parent;

    /** 孩子节点. */
    private List<NavMenuVO> children = new ArrayList<NavMenuVO>();

    /** 是否默认展开菜单组 */
    private Boolean open = Boolean.FALSE;

    /** 显示标识 */
    private Boolean show = Boolean.FALSE;
    
    //子系统
    private Integer sysid;

    public void setShow(Boolean show) {
        this.show = show;
        if (show == true & this.getParent() != null) {
            this.getParent().setShow(true);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NavMenuVO getParent() {
        return parent;
    }

    public void setParent(NavMenuVO parent) {
        this.parent = parent;
    }

    public List<NavMenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<NavMenuVO> children) {
        this.children = children;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getShow() {
        return show;
    }

    public int getChildrenSize() {
        return children == null ? 0 : children.size();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isHasIcon() {
        return StringUtils.isNotBlank(icon);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public Integer getSysid() {
		return sysid;
	}

	public void setSysid(Integer sysid) {
		this.sysid = sysid;
	}
}