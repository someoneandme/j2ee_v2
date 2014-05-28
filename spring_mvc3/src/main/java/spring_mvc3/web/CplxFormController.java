package spring_mvc3.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	/**
	 * 这个每次请求（无论这个controller里哪个请求）都会调用到
	 * 不过每次调用也不是就一定损耗性能，这种全表单提交，调用的次数并不多
	 * @param req
	 * @return
	 */
//	@ModelAttribute("cplxForm")
//	public void getUser(HttpServletRequest req) {
//		CplxForm form = new CplxForm();
//		form.setName(req.getParameter("name")); // 这里就可以自己指定form里的name了
//		// 其它的属性就自己填上，但是对于array数字的form就比较难手工处理了。
//		// return form;
	    // 2014年5月28日 18:43:38 这里改成controller的成员数据来保存!
//	}

	/**
	 * 说明一下：
	 * CplxForm form 如果bean属性名称和html form的名称不一致
	 * 可以先定义一个@ModelAttribute("cplxForm")
	 * <del>然后参数名写为@ModelAttribute("cplxForm") CplxForm form</del>
	 * (2014年5月28日)注: 不要用这种方法，不然form注入就被调用了3次!而且容易出现类型不匹配!
	 * 找到一种完美的解决方法：把@ModelAttribute("cplxForm")方法里的变量保存到类的成员变量，
	 * 然后@RequestMapping不要用CplxForm form来注入就可以~!
	 * @param form
	 * @param model
	 * @return
	 */
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
