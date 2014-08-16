package com.pugwoo.practice.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.pugwoo.practice.ibatis.entity.Student;

public class TestOperatingDB {

	public static void main(String[] args) throws SQLException, IOException {
		Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
		SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		
		Student student = (Student) sqlMap.queryForObject("getStudentById", 1);
		System.out.println(student);
	}

}
