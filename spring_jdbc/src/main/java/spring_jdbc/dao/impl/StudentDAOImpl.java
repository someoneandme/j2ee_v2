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

import spring_jdbc.dao.StudentDAO;
import spring_jdbc.entity.Student;

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
//		jdbcTemplate.update("insert into student(id,name,age) values(?,?,?)",
//				student.getId(), student.getName(), student.getAge());
		// 最后3个参数这样也行：new Object[] { student.getId(), student.getName(),
		// student.getAge() });
		// jdbcTemplate不支持save(Object obj)这样的类似hibernate的ORM方法
		
		// jdbcTemplate也支持name参数，这样就不用维护参数的顺序了
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", student.getId());
		params.put("name", student.getName());
		params.put("age", student.getAge());
		
		namedParameterJdbcTemplate.update(
				"insert into student(id,name,age) values(:id,:name,:age)",
				params);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Student getById(long id) throws SQLException {
		Student student = (Student) jdbcTemplate.queryForObject(
				"select * from student where id=?", new BeanPropertyRowMapper(
						Student.class), id);
		return student;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Student> getByName(String name) throws SQLException {
		List<Student> list = (List<Student>) jdbcTemplate.query(
			 "select * from student where name=?", 
			 new BeanPropertyRowMapper(Student.class), name);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Student> getAll() throws SQLException {
		List<Student> list = jdbcTemplate.query("select * from student",
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
	 */
	@Transactional
	public void insertAtomicity(List<Student> students) throws SQLException {
		if(students == null || students.isEmpty()) {
			return;
		}
		for(Student student : students) {
			jdbcTemplate.update("insert into student(id,name,age) values(?,?,?)",
					student.getId(), student.getName(), student.getAge());
		}
	}

}
