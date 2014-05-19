package spring_jdbc.dao;

import java.sql.SQLException;
import java.util.List;

import spring_jdbc.entity.Student;

/**
 * 2013年3月5日 18:06:23
 */
public interface StudentDAO {

	/**
	 * 插入student对象
	 * @param student
	 * @throws SQLException
	 */
	public void insert(Student student) throws SQLException;
	
	/**
	 * 根据student的Id获得student对象
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Student getById(long id) throws SQLException;
	
	/**
	 * 查询所有的学生列表
	 * @return
	 * @throws SQLException
	 */
	public List<Student> getAll() throws SQLException;
	
}
