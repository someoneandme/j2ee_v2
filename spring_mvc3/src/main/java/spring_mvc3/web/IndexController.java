package spring_mvc3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2015年9月2日 10:01:12
 * 首页
 */
@Controller
public class IndexController {
	
	/**
	 * 【注意】
	 * 如果返回的不是String，那么Spring会拿@RequestMapping的值去找渲染模版
	 * 
	 * test前端传递过来的参数，这样的写法不需要要求该参数一定存在！只是没有@RequestParam清晰而已
	 */
	@RequestMapping("/")
	public String index(String test) {
		System.out.println("test:" + test);
		return "index";
	}
	
}
