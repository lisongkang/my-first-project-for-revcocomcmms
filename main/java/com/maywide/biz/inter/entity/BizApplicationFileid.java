package com.maywide.biz.inter.entity;

import com.maywide.core.entity.PersistableEntity;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

/**
 * Created by lisongkang on 2019/6/8 0001.
 */
@Entity
@Table(name = "BIZ_APPLICATION_ACCEPTFILEIS")
public class BizApplicationFileid extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id; // 序列号
    private String proid;

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }
    private String filename;
    private String filepath;
    private String aplytime;
    private String realfilename;
    private String isimage;
    @Override
    @Transient
    public String getDisplay() {
        return null;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACFILEID", unique = true, length = 16)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getAplytime() {
        return aplytime;
    }

    public void setAplytime(String aplytime) {
        this.aplytime = aplytime;
    }

    public String getRealfilename() {
        return realfilename;
    }

    public void setRealfilename(String realfilename) {
        this.realfilename = realfilename;
    }

    public String getIsimage() {
        return isimage;
    }

    public void setIsimage(String isimage) {
        this.isimage = isimage;
    }
}
