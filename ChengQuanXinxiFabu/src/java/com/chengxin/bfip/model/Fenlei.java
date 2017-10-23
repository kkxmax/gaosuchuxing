package com.chengxin.bfip.model;

import java.util.Date;

import com.chengxin.common.BaseModel;
import com.chengxin.common.DateTimeUtil;

public class Fenlei extends BaseModel{
	private int id;
    private String fenlei;
    private int leixing;
    private String leixingName;
    private Date writeTime;
    
    public Fenlei() {
    	this.fenlei = "";
    	this.leixing = 0;
    	this.writeTime = new Date();
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFenlei() {
		return fenlei;
	}
	public void setFenlei(String fenlei) {
		this.fenlei = fenlei;
	}
	public int getLeixing() {
		return leixing;
	}
	public void setLeixing(int leixing) {
		this.leixing = leixing;
	}
	public String getLeixingName() {
		return leixingName;
	}
	public void setLeixingName(String leixingName) {
		this.leixingName = leixingName;
	}
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
    
}

