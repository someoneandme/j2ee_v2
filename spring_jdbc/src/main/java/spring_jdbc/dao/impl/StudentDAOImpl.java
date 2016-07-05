package spring_jdbc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import entity.Student;
import spring_jdbc.dao.StudentDAO;

/**
 * 2014年5月19日 09:44:34 通过注解提供Spring自动依赖注入，因此需要@Component
 */
@Component
public class StudentDAOImpl implements StudentDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void insert(Student student) throws SQLException {
		/**
		 * jdbctemplate的?形式参数注入是直接用java的PreparedStatement来实现的
		 * 经过实验可以发现：'?'和"?"在sql中是可以正确被处理为字符串的
		 * 除此之外，?都会被替换
		 */
//		jdbcTemplate.update("insert into t_student(id,name) values(?,\"?'?\")",
//				student.getId());
//		jdbcTemplate.update("insert into t_student(id,name,age)values(?,?,?)",
//				student.getId(), student.getName(), student.getAge());
		// 最后3个参数这样也行：new Object[] { student.getId(), student.getName(),
		// student.getAge() });
		// jdbcTemplate不支持save(Object obj)这样的类似hibernate的ORM方法
		
		// jdbcTemplate也支持name参数，这样就不用维护参数的顺序了
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", student.getId());
		params.put("name", student.getName());
		params.put("age", student.getAge());
		
		/**
		 * 同样的，:xx的标记在''和""中也会被处理为字符串
		 * 除此之外，都会被处理。
		 * 实际上，:xx这种符号会被替换成?，然后走PreparedStatement
		 */
		int affected = namedParameterJdbcTemplate.update(
				"insert into t_student(id,name,age) values(:id,:name,:age)",
				params);
		System.out.println(affected);
		
		// 目前之所以用named的方式，因为这种方式支持 param in (:param) 用List注入的方式，而?不支持
		
		// 经过实验，namedParameterJdbcTemplate 是不支持 ? ? 这种方式的
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Student getById(long id) throws SQLException {
		Student student = (Student) jdbcTemplate.queryForObject(
				"select * from t_student where id=?", new BeanPropertyRowMapper(
						Student.class), id);
		return student;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Student> getByName(String name) throws SQLException {
		List<Student> list = (List<Student>) jdbcTemplate.query(
			 "select * from t_student where name=?", 
			 new BeanPropertyRowMapper(Student.class), name);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Student> getAll() throws SQLException {
		List<Student> list = jdbcTemplate.query("select * from t_student",
		/* 这里可以用BeanPropertyRowMapper，也可以自己实现 */
		new RowMapper() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Student student = new Student();
				student.setId(rs.getLong("id"));
				student.setName(rs.getString("name"));
				student.setAge(rs.getInt("age"));
				return student;
			}
		});
		return list;
	}

	/**
	 * 这里使用@Transactional注解，使得这个方法的执行是原子性的，会自动安排好事务
	 * 
	 * 【Any RuntimeException triggers rollback, and any checked Exception does not.】
	 * http://stackoverflow.com/questions/7125837/why-does-transaction-roll-back-on-runtimeexception-but-not-sqlexception
	 */
	@Transactional
	public void insertAtomicity(List<Student> students) throws Exception {
		if(students == null || students.isEmpty()) {
			return;
		}
		for(Student student : students) {
			jdbcTemplate.update("insert into t_student(id,name,age) values(?,?,?)",
					student.getId(), student.getName(), student.getAge());
			// 故意插入第一条之后就抛出异常
			// throw new Exception(); // 这条没有导致回滚
			// throw new RuntimeException(); // 需要抛出RuntimeException才会回滚
			
			// 【重要！】下面这种写法是手工让当前方法所在的事务回滚，非常实用,还有其他控制事务的方法
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

}
