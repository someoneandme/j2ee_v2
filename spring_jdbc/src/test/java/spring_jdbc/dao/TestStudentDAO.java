package spring_jdbc.dao;

import java.sql.SQLException;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import spring_jdbc.entity.Student;

/**
 * 2014年5月19日 11:11:07
 * @ContextConfiguration locations 不支持匹配符*，如果多个要放在{"", ""}中
 * @Transactional 使得测试方法支持@Rollback，不对数据库做实质修改，然后又可以测试
 */
@ContextConfiguration(locations = "classpath:applicationContext-jdbc.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestStudentDAO {

	/**
	 * 首先至少要有一个StudentDAO的实现类注解了@Component
	 * 其次，如果StudentDAO只有一个实现，那么@Qualifier可以省略
	 */
	@Autowired
	@Qualifier("studentDAOImpl")
	private StudentDAO studentDAO;
	
	@Test
	@Rollback(true) // 改成false会对数据库有实质修改
	public void testInsertStudent() throws SQLException {
		Student student = new Student();
		student.setId((long) new Random().nextInt());
		student.setAge(26);
		student.setName("nick");

		studentDAO.insert(student);
	}
	
	/**
	 * 测试查询功能，不会对数据库有实质修改
	 * @throws SQLException
	 */
	@Test
	@Rollback(true)
	public void testQuery() throws SQLException {
		long randomId = (int) new Random().nextInt();
		
		Student student = new Student();
		student.setId(randomId);
		student.setAge(26);
		student.setName("nick");

		studentDAO.insert(student);
		
		Student student2 = studentDAO.getById(randomId);
		System.out.println(student2);
	}
	
}
