package com.pugwoo.practice.spring_mybatis.dao;

import java.sql.SQLException;
import java.util.List;

import com.pugwoo.practice.spring_mybatis.entity.Student;

public interface StudentDao {

	/**
	 * 获得所有数据
	 * 
	 * @return
	 */
	public List<Student> findAllStudents() throws SQLException;

	/**
	 * 通过id删除行
	 * 
	 * @param id
	 * @return 删除的行数
	 */
	public int deleteStudentById(int id) throws SQLException;

	/**
	 * 新增学生，向数据库插入一条记录。
	 * 学生id必须自行指定，非自增。id不能重复。
	 * 
	 * @param student
	 */
	public void addStudent(Student student) throws SQLException;

}
