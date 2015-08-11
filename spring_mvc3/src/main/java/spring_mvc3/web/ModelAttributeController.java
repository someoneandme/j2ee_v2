package spring_mvc3.web;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	 * @param model
	 */
	@ModelAttribute
	public void executeBeforeRequest(Model model,
			@RequestParam(value = "name", required = false) String name) {
		// 因为这个方法会在每次调用之前执行，所以这里实际上也可以只写一些查询数据库的操作
		// 例如可以根据id查询User对象等事情，非常实用
		model.addAttribute("processedName", name + "!");
	}
	
	/**
	 * 参数name要带上@ModelAttribute注解，实际上可以同名的，有注解配置就行
	 */
	@RequestMapping("/test_model_attribute")
	public void index(Writer writer,
		@ModelAttribute("processedName") String name) throws IOException {
		writer.write(name);
	}
	
}
