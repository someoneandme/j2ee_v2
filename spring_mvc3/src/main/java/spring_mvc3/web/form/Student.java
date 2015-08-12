package spring_mvc3.web.form;

public class Student {

	private Long id;
	private String name;
	private String school;
	private Integer age;
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", school=" + school
				+ ", age=" + age + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
