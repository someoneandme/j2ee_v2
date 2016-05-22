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
	 */

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
}
