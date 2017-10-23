package com.chengxin.bfip.model;

import com.chengxin.common.BaseModel;

public class Products extends BaseModel{
	private int id;
	private String num;
	private String booknum;
	private String enter_name;
	private int status;
	private String name;
	private Double price;
	private int pleixing_id;
	private String comment;
	private String weburl;
	private String sale_weburl;
	private String up_time;
	private String down_time;
	private String img_path1;
	private String img_path2;
	private String img_path3;
	private String img_path4;
	private String img_path5;
	private int account_id;
	private String write_time;
	private String products_write_time;
	public String getProduct_write_time() {
		return products_write_time;
	}
	public void setProduct_write_time(String product_write_time) {
		this.products_write_time = product_write_time;
	}
	private String status_name;
	private String akind_name;
	private String type_name;
	private String account;

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
	public String getEnter_name() {
		return enter_name;
	}
	public void setEnter_name(String enter_name) {
		this.enter_name = enter_name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getPleixing_id() {
		return pleixing_id;
	}
	public void setPleixing_id(int pleixing_id) {
		this.pleixing_id = pleixing_id;
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
	public String getSale_weburl() {
		return sale_weburl;
	}
	public void setSale_weburl(String sale_weburl) {
		this.sale_weburl = sale_weburl;
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
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public String getAkind_name() {
		return akind_name;
	}
	public void setAkind_name(String akind_name) {
		this.akind_name = akind_name;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}


}
