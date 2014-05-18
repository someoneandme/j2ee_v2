package spring_mvc3.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring_mvc3.web.form.Student;

/**
 * 2014年4月18日 13:01:06
 */
@Controller
public class VeloctiyController {

	@RequestMapping(value = "/velocity_index")
	public String index_vm(Model model) {
		model.addAttribute("userName", "nick");
		
		Student student = new Student();
		student.setId(3L);
		student.setName("NICK");
		
		model.addAttribute("student", student);
		
		return "index";
	}
}
