package com.maywide.biz.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name="return_visit_template")
public class ReturnVisitTemplate extends PersistableEntity<Integer> implements Persistable<Integer> {

	private Integer id;
	
	private Integer templateType;
	
	private String templateTitle;
	
	private String templateContent;
	
	@Id
	@Column(name="template_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}
	
	



	public Integer getTemplateType() {
		return templateType;
	}





	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}





	public String getTemplateTitle() {
		return templateTitle;
	}





	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}



	public String getTemplateContent() {
		return templateContent;
	}





	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}






	public void setId(Integer id) {
		this.id = id;
	}



	@Transient
	@Override
	public String getDisplay() {
		return null;
	}
	

	

}
