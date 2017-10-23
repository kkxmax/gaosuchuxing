package com.chengxin.bfip.model;

import java.util.Date;

import com.chengxin.common.BaseModel;
import com.chengxin.common.IExtendField;

public class Item extends BaseModel {
    private int id;
    private String num;
    private int accountId;
    private String name;
    private String addr;
    private String comment;
    private String need;
    private String weburl;
    private String upTime;
    private String downTime;
    private String contactName;
    private String contactMobile;
    private String enterName;
    private String imgPath1;
    private String imgPath2;
    private String imgPath3;
    private String imgPath4;
    private String imgPath5;
    private String writeTime;
    
    @IExtendField(description="")
    private String account;
    
    @IExtendField(description="")
    private int akind;
    
    @IExtendField(description="")
    private String akindName;
    
    @IExtendField(description="")
    private int enterKind;
    
    @IExtendField(description="")
    private String bossWeixin;

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnterName() {
		return enterName;
	}

	public void setEnterName(String enterName) {
		this.enterName = enterName;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getNeed() {
		return need;
	}

	public void setNeed(String need) {
		this.need = need;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	public String getDownTime() {
		return downTime;
	}

	public void setDownTime(String downTime) {
		this.downTime = downTime;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getImgPath1() {
		return imgPath1;
	}

	public void setImgPath1(String imgPath1) {
		this.imgPath1 = imgPath1;
	}

	public String getImgPath2() {
		return imgPath2;
	}

	public void setImgPath2(String imgPath2) {
		this.imgPath2 = imgPath2;
	}

	public String getImgPath3() {
		return imgPath3;
	}

	public void setImgPath3(String imgPath3) {
		this.imgPath3 = imgPath3;
	}

	public String getImgPath4() {
		return imgPath4;
	}

	public void setImgPath4(String imgPath4) {
		this.imgPath4 = imgPath4;
	}

	public String getImgPath5() {
		return imgPath5;
	}

	public void setImgPath5(String imgPath5) {
		this.imgPath5 = imgPath5;
	}

	public String getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getAkind() {
		return akind;
	}

	public void setAkind(int akind) {
		this.akind = akind;
	}

	public String getAkindName() {
		return akindName;
	}

	public void setAkindName(String akindName) {
		this.akindName = akindName;
	}

	public int getEnterKind() {
		return enterKind;
	}

	public void setEnterKind(int enterKind) {
		this.enterKind = enterKind;
	}

	public String getBossWeixin() {
		return bossWeixin;
	}

	public void setBossWeixin(String bossWeixin) {
		this.bossWeixin = bossWeixin;
	}
    
}