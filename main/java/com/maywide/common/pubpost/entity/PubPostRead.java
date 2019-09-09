package com.maywide.common.pubpost.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;



import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.BaseUuidEntity;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name = "prv_pub_post_read", uniqueConstraints = @UniqueConstraint(columnNames = { "pub_post_id",
        "read_user_id" }))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@MetaData(value = "公告阅读记录")
public class PubPostRead extends PersistableEntity<String> {

    @MetaData(value = "公告")
    @EntityAutoCode(order = 10)
    private PubPost pubPost;

    @MetaData(value = "阅读用户")
    @EntityAutoCode(order = 40)
    private PrvOperator readUser;

    @MetaData(value = "首次阅读时间")
    @EntityAutoCode(order = 30)
    private Date firstReadTime;

    @MetaData(value = "最后阅读时间")
    @EntityAutoCode(order = 35)
    private Date lastReadTime;

    @MetaData(value = "总计阅读次数")
    @EntityAutoCode(order = 40)
    private Integer readTotalCount = 1;
    
    private String id;
    
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
        return null;
    }

    @ManyToOne
    @JoinColumn(name = "pub_post_id", nullable = false)
    @JsonIgnore
    public PubPost getPubPost() {
        return pubPost;
    }

    public void setPubPost(PubPost pubPost) {
        this.pubPost = pubPost;
    }

    @ManyToOne
    @JoinColumn(name = "read_user_id", nullable = false)
    @JsonIgnore
    public PrvOperator getReadUser() {
        return readUser;
    }

    public void setReadUser(PrvOperator readUser) {
        this.readUser = readUser;
    }

    @Transient
    public String getReadUserLabel() {
        return readUser.getLoginname();
    }

    @Column(nullable = false)
    public Date getFirstReadTime() {
        return firstReadTime;
    }

    public void setFirstReadTime(Date firstReadTime) {
        this.firstReadTime = firstReadTime;
    }

    public Date getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(Date lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    @Column(nullable = false)
    public Integer getReadTotalCount() {
        return readTotalCount;
    }

    public void setReadTotalCount(Integer readTotalCount) {
        this.readTotalCount = readTotalCount;
    }
}
