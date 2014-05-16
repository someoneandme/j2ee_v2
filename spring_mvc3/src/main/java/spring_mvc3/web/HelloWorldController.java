/**
 * 2011年4月15日 下午06:12:21
 */
package spring_mvc3.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 使用注解，无需实现Controller接口
 * 关于注解RequestMapping的参数：默认是直接写一个String，代表是URL路径
 * 详细配置：value="URL路径", params = "必须有该参数",
 *    method = 指定请求方式，如RequestMethod.GET, headers未看
 * 
 * 【重要】关于方法的参数：
 * 如果是常用的HttpServletRequest HttpServletResponse HttpSession会自动注入【不需要按顺序，需要什么写什么】
 * 其它类型的参数会由Spring依据类型自动注入
 * 
 * 【如果有数据需要jsp显示，推荐返回ModelAndView，以免和request冲突】
 * 执行subController后，FontController会将ModelAndView的值放入request中
 */
@Controller
// 【注解这是个Controller】
@RequestMapping("hello")
// 【根目录，默认是/】
public class HelloWorldController {

	/**
	 * 每一个方法都可以映射为URL； 方法可以有两个参数request和response
	 * 【推荐】使用这种统一的方式
	 */
	@RequestMapping("hello_world")
	public String handleRequest(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// model将会结合viewResolver配置的prefix和suffix合成一个页面的路径
		model.addAttribute("message", "hello_world MVC!");
		return "hello_world";
	}

	/**
	 * 也可以是简单的返回String对象，此时string值将作为viewResolver的name；
	 * 参数可有可无，看是否需要获得request对象
	 * 【如果有数据，不推荐这种方式】
	 */
	@RequestMapping("easy_hello_world")
	public String easyRequest(HttpServletRequest request) {
		request.setAttribute("message", "easy_hello_world MVC!");
		return "hello_world";
	}

	/**
	 * 通过参数调用方法
	 * 当参数中有egg=egg则调用此方法
	 * 如果要求所有egg的参数都调用，则用params="egg"
	 */
	@RequestMapping(value = "hello_world", params = "egg=egg")
	public String eastEgg(Model model) {
		model.addAttribute("message", "you get an east egg.");
		return "hello_world";
	}
	
	/**
	 * 转向：返回"redirect:URL"
	 * 属于【浏览器端跳转】
	 * @return
	 */
	@RequestMapping("redirect_to_helloworld")
	public String redirectRequest() {
		return "redirect:hello_world";
	}
	
	/**
	 * 服务器端跳转，浏览器url没有变化
	 * @return
	 */
	@RequestMapping("forward_to_listcars")
	public ModelAndView forwardRequest(){
		return new ModelAndView("forward:/list_cars");
	}

}
