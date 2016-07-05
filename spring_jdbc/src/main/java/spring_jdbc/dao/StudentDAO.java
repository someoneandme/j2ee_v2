package spring_jdbc.dao;

import java.sql.SQLException;
import java.util.List;

import entity.Student;

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
	 * 原子性插入全部，要么全部成功，要么全部失败
	 * @param students
	 * @throws SQLException
	 */
	public void insertAtomicity(List<Student> students) throws Exception;
	
	/**
	 * 根据student的Id获得student对象
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Student getById(long id) throws SQLException;
	
	/**
	 * 根据名称查找学生
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public List<Student> getByName(String name) throws SQLException;
	
	/**
	 * 查询所有的学生列表
	 * @return
	 * @throws SQLException
	 */
	public List<Student> getAll() throws SQLException;

}
