package spring_mvc3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 用于查看spring mvc内部的配置
 * 2015年9月1日 11:40:56
 */
@Controller
public class InnerDataController {

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping( value = "/endPoints", method = RequestMethod.GET )
	public String getEndPointsInView(Model model) {
	    model.addAttribute( "endPoints", requestMappingHandlerMapping.getHandlerMethods().keySet() );
	    return "admin/endPoints";
	}
	
}
