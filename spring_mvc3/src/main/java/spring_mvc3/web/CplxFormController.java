package spring_mvc3.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring_mvc3.web.form.CplxForm;

/**
 * 演示页面传递大量数据例子
 */
@Controller
public class CplxFormController {

	@RequestMapping("/cplx_form")
	public String form(Model model) {
		return "form";
	}

	@RequestMapping("/cplx_post")
	public String post(CplxForm form, Model model) {
		model.addAttribute("name", form.getName());
		model.addAttribute("form", form);
		model.addAttribute("students", form.getStudents());
		model.addAttribute("students_num", form.getStudents() == null ? 0
				: form.getStudents().size());
		return "form_result";
	}
}
