package spring_mvc3.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class HelloController {

	@RequestMapping
	public String welcome(Model model) {
		model.addAttribute("message", "Hello World!");
		return "hello";
	}
	
}
