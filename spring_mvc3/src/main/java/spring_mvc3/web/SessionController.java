package spring_mvc3.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 2015年8月10日 23:16:32
 * 
 * @SessionAttributes 注解将model中对应的数据放到session中
 */
@Controller
@SessionAttributes({"login"}) // 注解之后，model中名称为login的数据将同时放到request域和session域里面
  // 此外，还可以根据值的class类型来设置
public class SessionController {

	@RequestMapping("/test_session")
	public void session(Model model) {
		model.addAttribute("login", "1");
	}
	
}
