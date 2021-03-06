package spring_jdbc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import entity.Student;

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
	@Rollback(false) // 改成false会对数据库有实质修改
	public void testInsertStudent() throws SQLException {
		Student student = new Student();
		student.setId((long) new Random().nextInt());
		student.setAge(26);
		student.setName("测试名称");

		studentDAO.insert(student);
	}
	
	/**
	 * 这个测试是特殊的，由于两个student的主键相同，所以它们应该无法插入数据库
	 * 而由于insertAtomicity是事务的，所以两个student要么全部插入，要么全部插不入数据库
	 * @throws SQLException
	 */
	@Test
	@Rollback(false)
	public void testInsertStuduentAtomicity() throws Exception {
		List<Student> students = new ArrayList<Student>();
		
		Student student1 = new Student();
		student1.setId(3L);
		student1.setAge(26);
		student1.setName("nick");
		students.add(student1);
		
		Student student2 = new Student();
		student2.setId(3L);
		student2.setAge(26);
		student2.setName("nick");
//		students.add(student2);// 这个是故意造一个主键id相同的情况，如果这个打开，会造成整个事务回滚
		
		Student student3 = new Student();
		student3.setId(4L);
		student3.setAge(29);
		student3.setName("nick11");
		students.add(student3);
		
		boolean result = studentDAO.insertAtomicity(students);
		
		// 如果是TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		// 那么下面这行会打印出来，异常也会记录到log中
		// 如果是通过抛出RuntimeException来抛出异常，那么返回值就不会打印到这一行
		// 所以，手工让事务回滚也有很不错的使用方法
		System.out.println("================run done================" + result);
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
	
	@Test
	@Rollback(true)
	public void testQueryList() throws SQLException {
		// 【重要】对于jdbcTemplate，也不支持where name=?时当传入的参数为null的情况，还是要明确指明is null
		// 真实执行的sql是where name=null，查到的数据是空的，不会抛异常
		
		// 由此可以看出MyBatis的SQL中<isNotNull>之类的标签的作用还是挺实在的
		
		List<Student> students = studentDAO.getByName(null);
		System.out.println(students.size());
		for(Student st : students) {
			System.out.println(st);
		}
	}
		
}
