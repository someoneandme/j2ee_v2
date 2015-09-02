package spring_mvc3.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 用于查看spring mvc内部的配置
 * 2015年9月1日 11:40:56
 */
@Controller
public class InnerDataController {

	/**
	 * spring3.1版本后的RequestMappingHandlerMapping带有请求映射的所有信息
	 * getHandlerMethods 查看RequestMapping的方法  (getUrlMap是getHandlerMethods的简化信息)
	 * getMappedInterceptors 查看匹配到生效的拦截器
	 */
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping( value = "/endPoints", method = RequestMethod.GET )
	public String getEndPointsInView(Model model) {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		// 可以理解为，RequestMappingInfo对象唯一描述了一个请求到哪个方法，主要靠url、get/post方式和其它一些字段的匹配
		// 而HandlerMethod则对应于映射到的方法, 通过debug可以看到有个InvocableHandlerMethod的实现，靠getBridgedMethod()来调用目标方法
		// 【现在的关键是】SpringMVC是怎样找到这个HandlerMethod的？
		// 是在DispatcherServlet的getHandler方法决定的，这个方法是protected方法
		
	    model.addAttribute("endPoints", handlerMethods.keySet());
	    return "admin/endPoints";
	}
	
}
