package spring_jdbc.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void test() {
		List<StudentDO> list = dbHelper.getList(StudentDO.class);
		System.out.println(list.size());
		for(StudentDO studentDO : list) {
			System.out.println(studentDO);
		}
	}
	
	public static void main(String[] args) {
	}
	
}
