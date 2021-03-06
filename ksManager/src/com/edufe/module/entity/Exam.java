package com.edufe.module.entity;

import java.io.Serializable;

/* 
 *  
 * Fri Sep 14 18:09:57 CST 2018 
 */

public class Exam implements Serializable {

	private static final long serialVersionUID = 2L;

	private String examBegintime;
	private String examEndtime;
	private String examName;
	private Integer id;
	private float passScore;
	private String introduce;
    
    public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
    public float getPassScore() {
		return passScore;
	}

	public void setPassScore(float passScore) {
		this.passScore = passScore;
	}
	public String getExamBegintime() {
		return this.examBegintime;
	}

	public void setExamBegintime(String examBegintime) {
		this.examBegintime = examBegintime;
	}

	public String getExamEndtime() {
		return this.examEndtime;
	}

	public void setExamEndtime(String examEndtime) {
		this.examEndtime = examEndtime;
	}

	public String getExamName() {
		return this.examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}