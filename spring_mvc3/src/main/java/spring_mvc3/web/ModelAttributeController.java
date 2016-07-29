package spring_mvc3.web;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring_mvc3.web.form.Student;

/**
 * 2015年8月11日 22:27:03
 * @ModelAttribute 
 */
@Controller
public class ModelAttributeController {

	/**
	 * 标记为@ModelAttribute的方法会在每次执行之前执行
	 * 
	 * 这个方法和@RequestParam一样可以获得请求的参数数据
	 * 
	 * @ModelAttribute 有两种方式放置值：
	 * 1. return一个对象，然后@ModelAttribute("xxx")加上名称xxx
	 * 2. 在注入的model中写入数据，推荐这种方式
	 * 
	 * 【重要】对于required是false，实际上写String name更简洁
	 * 
	 * @param model
	 */
	@ModelAttribute
	public void executeBeforeRequest(Model model,
			@RequestParam(value = "name", required = false) String name) {
		// 因为这个方法会在每次调用之前执行，所以这里实际上也可以只写一些查询数据库的操作
		// 例如可以根据id查询User对象等事情，非常实用
		model.addAttribute("processedName", name + "!");
		
		Student student = new Student();
		student.setId(123L);
		student.setName("nick");
		model.addAttribute("student", student);
	}
	
	/**
	 * 参数name要带上@ModelAttribute注解，实际上可以同名的，有注解配置就行
	 * 
	 * 如果参数是一个JavaBean，那么页面的参数还会覆盖这个javabean
	 * 这个功能很实用，例如先从db查询下数据，然后页面数据再覆盖db查出来的数据，再update到db
	 * 
	 * 注意，此时model中存放的数据，就是上面@ModelAttribute方法执行时放入的数据
	 * 
	 * @ModelAttribute 标注的注入会从model中查找
	 * 而processedName没有@ModelAttribute注解的，则从request域中获取
	 * [注意] modelAttribute和request中的processedName名称是一样的
	 * 
	 * 但是对于POJO，不需要@ModelAttribute也可以注入，不知道规则是什么
	 * 页面的request带进来的age属性会注入到student中
	 */
	@RequestMapping("/test_model_attribute")
	public void index(Writer writer, Model model,
		@ModelAttribute("processedName") String name,
		String processedName,
		Student student) throws IOException {
		writer.write("modelAttribute:" + name + 
				",processedName:" + processedName);
		writer.write("\n");
		writer.write("student:" + student);
	}
	
}
