package com.pugwoo.practice.spring_mybatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pugwoo.practice.spring_mybatis.dao.StudentDao;
import com.pugwoo.practice.spring_mybatis.entity.Student;

/**
 * @ContextConfiguration locations 不支持匹配符*，如果多个要放在{"", ""}中
 * @Transactional 使得测试方法支持@Rollback，不对数据库做实质修改，然后又可以测试
 */
@ContextConfiguration(locations = "classpath:applicationContext-db.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestStudentDao {

	/**
	 * 首先至少要有一个StudentDAO的实现类注解了@Repository 其次，如果StudentDAO只有一个实现，那么@Qualifier可以省略
	 */
	@Autowired
	private StudentDao studentDao;

	@Test
	@Rollback(true) // 改成false会对数据库有实质修改，要求mysql引擎是InnoDB
	public void testInsertStudent() throws SQLException {
		Student student = new Student();
		student.setId(new Random().nextInt());
		student.setName("测试名称");

		studentDao.addStudent(student);
		System.out.println("add student ok");
	}

	@Test
	@Rollback(true) // 改成false会对数据库有实质修改，要求mysql引擎是InnoDB
	public void testGetAllStudent() throws SQLException {

		int id = new Random().nextInt();

		Student student = new Student();
		student.setId(id);
		student.setName("测试名称");

		studentDao.addStudent(student);

		List<Student> list = studentDao.findAllStudents();

		System.out.println("size:" + list.size());
		for (Student s : list) {
			System.out.println("id:" + s.getId() + ",name:" + s.getName());
		}

		Assert.assertTrue(list.size() > 0);
	}

	@Test
	@Rollback(true) // 改成false会对数据库有实质修改，要求mysql引擎是InnoDB
	public void testDeleteStudent() throws SQLException {

		int id = new Random().nextInt();

		Student student = new Student();
		student.setId(id);
		student.setName("测试名称");

		studentDao.addStudent(student);

		int rows = studentDao.deleteStudentById(id);

		Assert.assertTrue(rows == 1);
	}

}
