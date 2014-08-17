package com.pugwoo.practice.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.pugwoo.practice.ibatis.entity.Student;

/**
 * 注意：如果要使用ibatis的namespace特性，记得sqlmapConfig.xml中的settings
 * 设置useStatementNamespaces为true。此时就只能使用namespace的方式，二选一。
 *
 */
public class TestOperatingDB {

	public static void main(String[] args) throws SQLException, IOException {
		Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
		SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		
		Student student = (Student) sqlMap.queryForObject("Student.getStudentById", 1);
		System.out.println(student);
		
		// 启用namespace之后就只能用上面那种，一般大型项目都会用namespace的
//		student = (Student) sqlMap.queryForObject("getStudentById", 1);
//		System.out.println(student);
	}

}
