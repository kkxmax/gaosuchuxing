package com.chengxin.bfip.model;

import com.chengxin.common.BaseModel;
import com.chengxin.common.IExtendField;

public class Account extends BaseModel {
    private int id;
    private int akind;
    private String account;
    private String code;
    private int useful;
    private int elect;
    private String logo;
    private int enterKind;
    private String enterName;
    private int xyleixingId;
    private String addr;
    private String weburl;
    private String weixin;
    private String mainJob;
    private String certImage;
    private String comment;
    private String recommend;
    private String bossName;
    private String bossJob;
    private String bossMobile;
    private String bossWeixin;
    private int testStatus;
    private int banStatus;
    private String writeTime;
    
    private String testStatusName;
    private String banStatusName;
    private String enterKindName;    
    
	
    
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public int getXyleixingId() {
		return xyleixingId;
	}
	public void setXyleixingId(int xyleixingId) {
		this.xyleixingId = xyleixingId;
	}
	public String getMainJob() {
		return mainJob;
	}
	public void setMainJob(String mainJob) {
		this.mainJob = mainJob;
	}
	public String getCertImage() {
		return certImage;
	}
	public void setCertImage(String certImage) {
		this.certImage = certImage ;
	}
	public String getBossJob() {
		return bossJob;
	}
	public void setBossJob(String bossJob) {
		this.bossJob = bossJob;
	}
	public String getBossMobile() {
		return bossMobile;
	}
	public void setBossMobile(String bossMobile) {
		this.bossMobile = bossMobile;
	}
	public String getBossWeixin() {
		return bossWeixin;
	}
	public void setBossWeixin(String bossWeixin) {
		this.bossWeixin = bossWeixin;
	}
	public int getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(int testStatus) {
		this.testStatus = testStatus;
	}
	public int getBanStatus() {
		return banStatus;
	}
	public void setBanStatus(int banStatus) {
		this.banStatus = banStatus;
	}
	public String getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}
	public String getTestStatusName() {
		return testStatusName;
	}
	public void setTestStatusName(String testStatusName) {
		this.testStatusName = testStatusName;
	}
	public String getBanStatusName() {
		return banStatusName;
	}
	public void setBanStatusName(String banStatusName) {
		this.banStatusName = banStatusName;
	}
	public String getEnterKindName() {
		return enterKindName;
	}
	public void setEnterKindName(String enterKindName) {
		this.enterKindName = enterKindName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAkind() {
		return akind;
	}
	public void setAkind(int akind) {
		this.akind = akind;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getUseful() {
		return useful;
	}
	public void setUseful(int useful) {
		this.useful = useful;
	}
	public int getElect() {
		return elect;
	}
	public void setElect(int elect) {
		this.elect = elect;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getEnterKind() {
		return enterKind;
	}
	public void setEnterKind(int enterKind) {
		this.enterKind = enterKind;
	}
	public String getEnterName() {
		return enterName;
	}
	public void setEnterName(String enterName) {
		this.enterName = enterName;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getWeburl() {
		return weburl;
	}
	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

}