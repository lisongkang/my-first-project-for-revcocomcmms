package com.maywide.common.pubpost.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.AttachmentableEntity;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;
import com.maywide.core.web.json.DateTimeJsonSerializer;

@Entity
@Table(name = "prv_pub_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "公告", comments = "用于向应用所有用户显示的公告消息，不做用户或权限区分控制")
public class PubPost extends PersistableEntity<String> implements AttachmentableEntity {

    private static final long serialVersionUID = 2544390748513253055L;

    @MetaData(value = "标题")
    private String htmlTitle;

    @MetaData(value = "发布时间")
    private Date publishTime;

    @MetaData(value = "到期时间")
    private Date expireTime;

    @MetaData(value = "前端显示")
    private Boolean frontendShow = Boolean.FALSE;

    @MetaData(value = "后台显示")
    private Boolean backendShow = Boolean.TRUE;

    @MetaData(value = "外部链接")
    @EntityAutoCode(order = 40)
    private String externalLink;

    @MetaData(value = "公告内容")
    @EntityAutoCode(order = 45)
    private String htmlContent;

    @MetaData(value = "总计查看用户数")
    @EntityAutoCode(order = 50)
    private Integer readUserCount;

    @MetaData(value = "排序号", tooltips = "数字越大显示越靠上")
    @EntityAutoCode(order = 50)
    private Integer orderRank = 100;
    
    private String id;
    
    private Boolean isRead;
    
    private Long operid;
    private String city;

    @Id
    @Column(length = 40)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        //容错处理id以空字符提交参数时修改为null避免hibernate主键无效修改
        if (StringUtils.isBlank(id)) {
            this.id = null;
        } else {
            this.id = id;
        }
    }

    @Override
    @Transient
    public String getDisplay() {
        return htmlTitle;
    }

    @Column(nullable = false)
    public String getHtmlTitle() {
        return htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Lob
    @Type(type="text")
    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getReadUserCount() {
        return readUserCount;
    }

    public void setReadUserCount(Integer readUserCount) {
        this.readUserCount = readUserCount;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public Boolean getFrontendShow() {
        return frontendShow;
    }

    public void setFrontendShow(Boolean frontendShow) {
        this.frontendShow = frontendShow;
    }

    public Boolean getBackendShow() {
        return backendShow;
    }

    public void setBackendShow(Boolean backendShow) {
        this.backendShow = backendShow;
    }

    public Integer getOrderRank() {
        return orderRank;
    }

    public void setOrderRank(Integer orderRank) {
        this.orderRank = orderRank;
    }

    @Transient
    public boolean isInternal() {
        return StringUtils.isBlank(externalLink);
    }
    
    @MetaData(value="关联附件个数",comments="用于列表显示和关联处理附件清理判断")
    private Integer attachmentSize;

    @Override
    public Integer getAttachmentSize() {
        return attachmentSize;
    }

    public void setAttachmentSize(Integer attachmentSize) {
        this.attachmentSize = attachmentSize;
    }
    
    @Transient
	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

    public Long getOperid() {
        return operid;
    }

    public void setOperid(Long operid) {
        this.operid = operid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
