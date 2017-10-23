package com.chengxin.bfip.model;

import java.util.Date;

import com.chengxin.common.BaseModel;
import com.chengxin.common.DateTimeUtil;

public class Hots extends BaseModel{

	private int id;
    private String title;
    private int kind;
    private int visit_cnt;
	private int comment_cnt;
	private int remark_cnt;
	private int share_cnt;
	private Date up_time;
    private Date down_time;
    private String content;
    private int st;
    private int account_id;
    private Date write_time;
    private String kind_name;
    private String st_name;
    private int xyleixing_level1_id;
    private String xyleixing_level1_name;
    

    public Hots() {
		super();
		this.title = "";
		this.kind = 0;
		this.visit_cnt = 0;
		this.comment_cnt = 0;
		this.remark_cnt = 0;
		this.share_cnt = 0;
		this.up_time = null;
		this.down_time = null;
		this.content = "";
		this.st = 0;
		this.account_id = 0;
		this.write_time = new Date();
	}
    
    public Hots(String title, int kind, String content) {
		super();
		this.title = title;
		this.kind = kind;
		this.visit_cnt = 0;
		this.comment_cnt = 0;
		this.remark_cnt = 0;
		this.share_cnt = 0;
		this.content = content;
		this.st = 0;
		this.account_id = 0;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getKind_name() {
		return kind_name;
	}
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	public String getSt_name() {
		return st_name;
	}
	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}
	
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getVisit_cnt() {
		return visit_cnt;
	}
	public void setVisit_cnt(int visit_cnt) {
		this.visit_cnt = visit_cnt;
	}
	public int getComment_cnt() {
		return comment_cnt;
	}
	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
	public int getRemark_cnt() {
		return remark_cnt;
	}
	public void setRemark_cnt(int remark_cnt) {
		this.remark_cnt = remark_cnt;
	}
	public int getShare_cnt() {
		return share_cnt;
	}
	public void setShare_cnt(int share_cnt) {
		this.share_cnt = share_cnt;
	}
	public Date getUp_time() {
		return up_time;
	}
	public void setUp_time(Date up_time) {
		this.up_time = up_time;
	}
	public Date getDown_time() {
		return down_time;
	}
	public void setDown_time(Date down_time) {
		this.down_time = down_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public Date getWrite_time() {
		return write_time;
	}
	public void setWrite_time(Date write_time) {
		this.write_time = write_time;
	}
	public int getXyleixing_level1_id() {
		return xyleixing_level1_id;
	}
	public void setXyleixing_level1_id(int xyleixing_level1_id) {
		this.xyleixing_level1_id = xyleixing_level1_id;
	}
	public String getXyleixing_level1_name() {
		return xyleixing_level1_name;
	}
	public void setXyleixing_level1_name(String xyleixing_level1_name) {
		this.xyleixing_level1_name = xyleixing_level1_name;
	}

    
    
}
