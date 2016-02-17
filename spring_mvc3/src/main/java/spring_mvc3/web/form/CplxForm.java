package spring_mvc3.web.form;

import java.util.List;

/**
 * 2013年3月18日 16:30:45
 * 
 * 2015年4月27日 11:31:46
 * 这个类是为页面参数的，这里有一个缺点是：
 * 无法像@RequestParam一样指定页面参数到变量的映射
 * 
 *  @ModelAttribute 也无法注解到set方法来指定
 *  
 * 2016年2月17日 08:32:28
 * 重新思考上，这个form实际上只存在于web层，所以没有办法指定form name到bean的映射，其实也是合理的
 * 
 */
public class CplxForm {

	private String name;

	private List<Student> students; // 除了list，还有map的绑定方式，页面：name['xx']='yy'

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
