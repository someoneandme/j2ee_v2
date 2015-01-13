package spring_jdbc.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pugwoo.dbhelper.utils.DBHelper;
import spring_jdbc.entity.StudentDO;

/**
 * 2015年1月13日 11:11:23
 *
 */
@ContextConfiguration(locations = "classpath:applicationContext-jdbc.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestDBHelper {
	
	@Autowired
	private DBHelper dbHelper;

	@Test
	@Rollback(false)
	public void testGetList() {
		List<StudentDO> list = dbHelper.getList(StudentDO.class);
		System.out.println(list.size());
		for(StudentDO studentDO : list) {
			System.out.println(studentDO);
		}
	}
	
	@Test
	public void testGetByKey() {
		StudentDO studentDO = new StudentDO();
		studentDO.setId(2L);
		if(dbHelper.getByKey(studentDO)) {
			System.out.println(studentDO);
		} else {
			System.out.println("not found");
		}
	}
	
	@Test
	@Rollback(false)
	public void testInsert() {
		StudentDO studentDO = new StudentDO();
		studentDO.setId(888L);
		studentDO.setName("nick888");
		studentDO.setAge(28);
		
		int row = dbHelper.insert(studentDO);
		System.out.println("affected rows:" + row);
	}
	
	
	
}
