package spring_mvc3.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import spring_mvc3.web.form.ValidateStudent;

/**
 * 2014年8月5日 10:30:44
 */
@Controller
public class ValidateController {

	/**
	 * 经过测试，好像对直接参数的验证没有生效，下面的认证没有生效：
	 * @RequestParam(value = "email", required = false) 
	   @NotEmpty @Email String email
	 * 
	 * @param model
	 * @param message
	 * @return
	 */
	@RequestMapping("/validate_param")
	public String validateParam(Model model,
			@Valid ValidateStudent validateStudent,
			BindingResult result) {
		
		if(result.hasErrors()) {
			model.addAttribute("message", "error:" + result.getAllErrors());
		} else {
			model.addAttribute("message", "student name:" + validateStudent.getName()
					+ ", and email :" + validateStudent.getEmail());
		}
		
		return "hello_world";
	}
	
}
