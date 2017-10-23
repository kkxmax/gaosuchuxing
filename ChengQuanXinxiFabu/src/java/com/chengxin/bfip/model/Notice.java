package com.chengxin.bfip.model;

import com.chengxin.common.BaseModel;
import com.chengxin.common.IExtendField;

public class Notice extends BaseModel {
    private int id;
    private int kind;
    private String content;
    private String occurTime;
    private int status;
    
    @IExtendField(description="")
    private String kindName;
    
    @IExtendField(description="")
    private String statusName;

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
    

}