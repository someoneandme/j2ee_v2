package com.pugwoo.practice.spring_mybatis.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pugwoo.practice.spring_mybatis.dao.StudentDao;
import com.pugwoo.practice.spring_mybatis.entity.Student;

@Component
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private SqlSession sqlSession;

	public List<Student> findAllStudents() throws SQLException {
		/**
		 * 当StudentMapper.xml中的map sql id和这个方法名一致是，可以直接
		 * getMapper，否则就得手工指定，通过sqlSession的select/update之类的指定
		 */
		return sqlSession.getMapper(StudentDao.class).findAllStudents();
	}

	public int deleteStudentById(int id) throws SQLException {
		// 这里可以做一些参数判断
		return sqlSession.getMapper(StudentDao.class).deleteStudentById(id);
	}

	public void addStudent(Student student) throws SQLException {
		sqlSession.getMapper(StudentDao.class).addStudent(student);
	}

}
