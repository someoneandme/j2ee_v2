package spring_mvc3.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		return "velocity_index";
	}
	
	@RequestMapping(value = "/velocity_condition")
	public String condition(Model model) {
		
		model.addAttribute("userName", "nick");
		
		Student student = new Student();
		student.setId(3L);
		student.setName("NICK");
		
		model.addAttribute("student", student);
		
		return "velocity_condition";
	}
	
	@RequestMapping(value = "/velocity_loop")
	public String loop(Model model) {
		
		List<String> list = new ArrayList<String>();
		list.add("haha");
		list.add("you_df");
		list.add("us");
		model.addAttribute("list", list);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("eleven", 11);
		map.put("three", 3);
		model.addAttribute("map", map);
		
		return "velocity_loop";
	}
}
