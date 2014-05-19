package spring_jdbc.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import spring_jdbc.dao.StudentDAO;
import spring_jdbc.entity.Student;

/**
 * 2014年5月19日 09:44:34 通过注解提供Spring自动依赖注入，因此需要@Component
 */
@Component
public class StudentDAOImpl implements StudentDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insert(Student student) throws SQLException {
		jdbcTemplate.update("insert into student(id,name,age) values(?,?,?)",
				student.getId(), student.getName(), student.getAge());
		// 最后3个参数这样也行：new Object[] { student.getId(), student.getName(),
		// student.getAge() });
		// jdbcTemplate不支持save(Object obj)这样的类似hibernate的ORM方法
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Student getById(long id) throws SQLException {
		Student student = (Student) jdbcTemplate.queryForObject(
				"select * from student where id=?", new BeanPropertyRowMapper(
						Student.class), id);
		return student;
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

}
