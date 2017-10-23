package com.chengxin.bfip.model;

import com.chengxin.common.BaseModel;

public class ClickHistory extends BaseModel{
	private int id;
	private int type;
	private int account_id;
	private String write_time;
	private int personal_cnt;
	private int enterprise_cnt;
	private String click_date;
	private int shop_cnt;
	private int item_cnt;
	private int service_cnt;
	private int personal_detail_cnt;
	private int enterprise_detail_cnt;
	private int report_cnt;
	private int buy_cnt;
	private int request_cnt;
	
	
	
	public int getPersonal_detail_cnt() {
		return personal_detail_cnt;
	}
	public void setPersonal_detail_cnt(int personal_detail_cnt) {
		this.personal_detail_cnt = personal_detail_cnt;
	}
	public int getEnterprise_detail_cnt() {
		return enterprise_detail_cnt;
	}
	public void setEnterprise_detail_cnt(int enterprise_detail_cnt) {
		this.enterprise_detail_cnt = enterprise_detail_cnt;
	}
	public int getReport_cnt() {
		return report_cnt;
	}
	public void setReport_cnt(int report_cnt) {
		this.report_cnt = report_cnt;
	}
	public int getBuy_cnt() {
		return buy_cnt;
	}
	public void setBuy_cnt(int buy_cnt) {
		this.buy_cnt = buy_cnt;
	}
	public int getRequest_cnt() {
		return request_cnt;
	}
	public void setRequest_cnt(int request_cnt) {
		this.request_cnt = request_cnt;
	}
	public int getShop_cnt() {
		return shop_cnt;
	}
	public void setShop_cnt(int shop_cnt) {
		this.shop_cnt = shop_cnt;
	}
	public int getItem_cnt() {
		return item_cnt;
	}
	public void setItem_cnt(int item_cnt) {
		this.item_cnt = item_cnt;
	}
	public int getService_cnt() {
		return service_cnt;
	}
	public void setService_cnt(int service_cnt) {
		this.service_cnt = service_cnt;
	}
	public String getClick_date() {
		return click_date;
	}
	public void setClick_date(String click_date) {
		this.click_date = click_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public int getPersonal_cnt() {
		return personal_cnt;
	}
	public void setPersonal_cnt(int personal_cnt) {
		this.personal_cnt = personal_cnt;
	}
	public int getEnterprise_cnt() {
		return enterprise_cnt;
	}
	public void setEnterprise_cnt(int enterprise_cnt) {
		this.enterprise_cnt = enterprise_cnt;
	}
	
	
}
