package entity;

/**
 * 2013年3月5日 18:08:59
 * 
 * 这是一个标准的最普通的bean，建议所有数据成员都【不用】基本类型，
 * 这样可以表示数据库的null值。
 */
public class Student {
	private Long id;
	private String name;
	private Integer age;
	
	@Override
	public String toString() {
		return "id:" + id + ",name:" + name + ",age:" + age;
	};

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
