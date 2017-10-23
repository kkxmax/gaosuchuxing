package com.chengxin.bfip.model;

import com.chengxin.common.BaseModel;
import com.chengxin.common.IExtendField;

public class Services extends BaseModel{

	private int id;
	private String num;
	private String booknum;
	private String name;
	private String comment;
	private String weburl;
	private String addr;
	private String up_time;
	private String down_time;
	private String contact_name;
	private String contact_mobile;
	private String contact_weixin;
	private String issue;
	private int booktype;
	private int st;
	private String img_path1;
	private String img_path2;
	private String img_path3;
	private String img_path4;
	private String img_path5;
	private String booktype_name;
	private String st_name;
	private String write_time;


	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public String getBooktype_name() {
		return booktype_name;
	}
	public void setBooktype_name(String booktype_name) {
		this.booktype_name = booktype_name;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}

	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public int getBooktype() {
		return booktype;
	}
	public void setBooktype(int booktype) {
		this.booktype = booktype;
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
	@IExtendField(description="")
	private String testStatusName;

	@IExtendField(description="")
	private String banStatusName;

	@IExtendField(description="")
	private String enterKindName;    


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getBooknum() {
		return booknum;
	}
	public void setBooknum(String booknum) {
		this.booknum = booknum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getWeburl() {
		return weburl;
	}
	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getUp_time() {
		return up_time;
	}
	public void setUp_time(String up_time) {
		this.up_time = up_time;
	}
	public String getDown_time() {
		return down_time;
	}
	public void setDown_time(String down_time) {
		this.down_time = down_time;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_mobile() {
		return contact_mobile;
	}
	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}
	public String getContact_weixin() {
		return contact_weixin;
	}
	public void setContact_weixin(String contact_weixin) {
		this.contact_weixin = contact_weixin;
	}
	public String getImg_path1() {
		return img_path1;
	}
	public void setImg_path1(String img_path1) {
		this.img_path1 = img_path1;
	}
	public String getImg_path2() {
		return img_path2;
	}
	public void setImg_path2(String img_path2) {
		this.img_path2 = img_path2;
	}
	public String getImg_path3() {
		return img_path3;
	}
	public void setImg_path3(String img_path3) {
		this.img_path3 = img_path3;
	}
	public String getImg_path4() {
		return img_path4;
	}
	public void setImg_path4(String img_path4) {
		this.img_path4 = img_path4;
	}
	public String getImg_path5() {
		return img_path5;
	}
	public void setImg_path5(String img_path5) {
		this.img_path5 = img_path5;
	}



}
