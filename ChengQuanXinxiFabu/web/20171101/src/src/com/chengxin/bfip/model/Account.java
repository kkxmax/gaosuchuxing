package com.chengxin.bfip.model;

import java.util.Date;

import com.chengxin.common.BaseModel;

public class Account extends BaseModel {
	
    private int id;
    private String mobile = "";
    private String password = "";
    private int reqCodeId = 0;
    private String token = "";
    private int akind = 1;
    private String code = "";
    private int viewCnt = 0;
    private String logo = "";
    private String realname = "";
    private int enterKind = 0;
    private String enterName = "";
    private int xyleixingId = 0;
    private int cityId = 0;
    private String addr = "";
    private String job = "";
    private String weburl = "";
    private String weixin = "";
    private String mainJob = "";
    private String certNum = "";
    private String certImage = "";
    private String enterCertNum = "";
    private String enterCertImage = "";
    private String experience = "";
    private String history = "";
    private String comment = "";
    private String recommend = "";
    private String bossName = "";
    private String bossJob = "";
    private String bossMobile = "";
    private String bossWeixin = "";
    private int testStatus = 0;
    private int banStatus = 1;
    private Date writeTime = new Date();
    
    private int credit = 0;
    private int electCnt = 0;
    private int feedbackCnt = 0;
    private int reqCodeSenderId;
    private String reqCodeSenderRealname;
    private String reqCodeSenderEnterName;
    private String testStatusName;
    private String banStatusName;
    private String enterKindName;    
    private String friend1;
    private String friend2;
    private String friend3;
    private String cityName;
    private int provinceId;
    private String provinceName;
    private String xyName;
    private int interested = 0;
    
    
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getReqCodeId() {
		return reqCodeId;
	}
	public void setReqCodeId(int reqCodeId) {
		this.reqCodeId = reqCodeId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
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
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	public int getElectCnt() {
		return electCnt;
	}
	public void setElectCnt(int electCnt) {
		this.electCnt = electCnt;
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
	public String getFriend1() {
		return friend1;
	}
	public void setFriend1(String friend1) {
		this.friend1 = friend1;
	}
	public String getFriend2() {
		return friend2;
	}
	public void setFriend2(String friend2) {
		this.friend2 = friend2;
	}
	public String getFriend3() {
		return friend3;
	}
	public void setFriend3(String friend3) {
		this.friend3 = friend3;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getEnterCertNum() {
		return enterCertNum;
	}
	public void setEnterCertNum(String enterCertNum) {
		this.enterCertNum = enterCertNum;
	}
	public String getEnterCertImage() {
		return enterCertImage;
	}
	public void setEnterCertImage(String enterCertImage) {
		this.enterCertImage = enterCertImage;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getXyName() {
		return xyName;
	}
	public void setXyName(String xyName) {
		this.xyName = xyName;
	}
	public int getReqCodeSenderId() {
		return reqCodeSenderId;
	}
	public void setReqCodeSenderId(int reqCodeSenderId) {
		this.reqCodeSenderId = reqCodeSenderId;
	}
	public String getReqCodeSenderRealname() {
		return reqCodeSenderRealname;
	}
	public void setReqCodeSenderRealname(String reqCodeSenderRealname) {
		this.reqCodeSenderRealname = reqCodeSenderRealname;
	}
	public String getReqCodeSenderEnterName() {
		return reqCodeSenderEnterName;
	}
	public void setReqCodeSenderEnterName(String reqCodeSenderEnterName) {
		this.reqCodeSenderEnterName = reqCodeSenderEnterName;
	}
	public int getInterested() {
		return interested;
	}
	public void setInterested(int interested) {
		this.interested = interested;
	}
	public int getFeedbackCnt() {
		return feedbackCnt;
	}
	public void setFeedbackCnt(int feedbackCnt) {
		this.feedbackCnt = feedbackCnt;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}

}