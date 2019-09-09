package com.maywide.biz.market.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maywide.core.entity.PersistableEntity;

@Entity
@Table(name = "GRID_RELATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GridRelation extends PersistableEntity<Long> implements Persistable<Long> {
    private Long id;
	private Grid grid = new Grid();
	private GridOssInfo gridOssInfo = new GridOssInfo();
	
	@Override
    @Transient
    public String getDisplay() {
        return null;
    }
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "recid")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "gridid", nullable = false)
    @NotFound(action=NotFoundAction.IGNORE)
    public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ossgridid", nullable = false)
    @NotFound(action=NotFoundAction.IGNORE)
    public GridOssInfo getGridOssInfo() {
		return gridOssInfo;
	}

	public void setGridOssInfo(GridOssInfo gridOssInfo) {
		this.gridOssInfo = gridOssInfo;
	}
}
