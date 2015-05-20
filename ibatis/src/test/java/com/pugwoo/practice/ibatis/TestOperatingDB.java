package com.pugwoo.practice.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

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
	
	private static SqlMapClient sqlMapClient;
	
	// 说明，@BeforeClass在Class构建时执行一次，@Before在每次@Test方法执行前执行一次
	@BeforeClass
	public static void before() throws IOException {
		Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
		sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
	}
	
	@Test
	public void testSelect() throws SQLException {
		Student student = (Student) sqlMapClient.queryForObject(
				"Student.getStudentById", 3);
		System.out.println(student);
		
		// 启用namespace之后就只能用上面那种，一般大型项目都会用namespace的
		// 没有用namespace的如下：
//		student = (Student) sqlMap.queryForObject("getStudentById", 1);
//		System.out.println(student);
	}

}
