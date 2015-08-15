package spring_mvc3.web.json_param_support;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;

/**
 * http://zjumty.iteye.com/blog/1927890
 * 些许改造 2015年8月15日 12:13:01
 */
public class JsonParamArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(JsonParam.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		// 获得@JsonParam注解的value值
		JsonParam jsonParam = parameter.getParameterAnnotation(JsonParam.class);
		String paramName = "";
		if(jsonParam != null) {
			paramName = jsonParam.value();
		}
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		
		String paramValue;
		if(paramName == null || paramName.isEmpty()) {
	        // 把reqeust的body读取到StringBuilder
	        BufferedReader reader = request.getReader();
	        StringBuilder sb = new StringBuilder();

	        char[] buf = new char[1024];
	        int rd;
	        while((rd = reader.read(buf)) != -1){
	            sb.append(buf, 0, rd);
	        }
	        paramValue = sb.toString();
		} else {
			paramValue = request.getParameter(paramName);
		}

		return JSON.parseObject(paramValue, parameter.getParameterType());
        
	}

}
