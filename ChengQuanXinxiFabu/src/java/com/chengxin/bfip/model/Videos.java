package com.chengxin.bfip.model;

import com.chengxin.common.BaseModel;

public class Videos extends BaseModel {

	private int id;
	private String videoUrl;
	private String videoName;
	private double videoSize;
	private String title;
	private String comment;
	private String writeTime;

	private int carouselId;

	public int getCarouselId() {
		return carouselId;
	}

	public void setCarouselId(int carouselId) {
		this.carouselId = carouselId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public double getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(double videoSize) {
		this.videoSize = videoSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}

}
