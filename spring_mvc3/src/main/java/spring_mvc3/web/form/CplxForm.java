package spring_mvc3.web.form;

import java.util.List;

/**
 * 2013年3月18日 16:30:45
 */
public class CplxForm {

	private String name;

	private List<Student> students;

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
