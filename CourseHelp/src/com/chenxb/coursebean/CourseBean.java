package com.chenxb.coursebean;

import java.io.Serializable;

public class CourseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -253521439320176163L;
	private String name;
	private String teacher;
	private String classroom;
	private String time;
	private int day;
	private int section;
	private boolean isEmpty;
	public int getDay() {
		return day;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CourseBean [name=" + name + ", teacher=" + teacher
				+ ", classroom=" + classroom + ", time=" + time + ", day="
				+ day + ", section=" + section + ", isEmpty=" + isEmpty + "]";
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public CourseBean(String name, String teacher, String classroom, String time, int day, int section) {
		this.name = name.replace(" ", "");
		this.teacher = teacher;
		this.classroom = classroom;
		this.time = time;
		this.day = day;
		this.section = section;
		this.isEmpty = false;
	}
	
	public CourseBean() {
		this.isEmpty = true;
	}
	public boolean isEmpty() {
		return isEmpty;
	}
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
	

}
