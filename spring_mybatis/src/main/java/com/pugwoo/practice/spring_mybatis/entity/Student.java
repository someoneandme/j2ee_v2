package com.pugwoo.practice.spring_mybatis.entity;

/**
 * 简单的bean，对应于
 * docs/sql/create_table.sql里面写的创建表语句，
 * 需要手工把里面的sql执行一下。
 */
public class Student {

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
