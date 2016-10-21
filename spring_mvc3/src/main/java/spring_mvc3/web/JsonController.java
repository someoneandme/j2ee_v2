package spring_mvc3.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import spring_mvc3.web.form.Student;
import spring_mvc3.web.json_param_support.JsonParam;

/**
 * 演示输入纯文本
 * 
 * 直接使用@ResponseBody来输出数据，需要加入json解析工具依赖，推荐fastjson
 * 默认只要导入jackson的依赖，不需要额外配置就可以使用@ResponseBody的功能
 * 但因为fastjson性能更佳，为了用fastjson，还需要配置mvc:annotation-driven的mvc:message-converters
 */
@Controller
public class JsonController {

	@ResponseBody // 加这个注解
	@RequestMapping("/json")
	public Map<Object, Object> json() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", 3);
		map.put("name", "参数\"");
		map.put(123, "key is number");
		map.put("nullvalue", null);
		return map;
	}
	
	// 由于现在@ResponseBody 可能被fastjson处理，所以需要测试下byte[]返回值不会不被fastjson处理到
	// 经过测试，byte[]不会被fastjson处理
	@ResponseBody
	@RequestMapping("/json_byte")
	public byte[] testByteArr() {
		return "hello".getBytes();
	}
	
	// 经过测试，string也不会被fastjson处理，会直接按string的值输出到前端
	// 【注意】后来我发现spring会不会被fastjson处理这个情况，和fastjson的版本有关，1.2.6到1.2.10就不会，1.2.11新版就会
	// 这个东西也没有说合理不合理的问题，但我还是倾向不被json处理
	@ResponseBody
	@RequestMapping("/json_string")
	public String testString() {
		return "{name:'nick'}";
	}
	
	@ResponseBody
	@RequestMapping("/json_param")
	public Object jsonParam(@JsonParam("json") Student student) {
		System.out.println(student);
		return student;
	}
	
	//////////////////下面的这些方法都太老土，性能差，不安全，不推荐使用，仅适用于写些数据///////////////////////
	
	@RequestMapping("/json_old")
	public void json(Writer writer) throws IOException {
		// 假设json是通过工具获得的jsonString
		String json = "{id:3}";
		writer.write(json);
	}

	// 适用于二进制输出
	@RequestMapping("/json2")
	public void json2(OutputStream out) throws IOException {
		out.write("hello".getBytes());
	}

	@RequestMapping("/chinese_json")
	public void jsonChinese(Writer writer,
			HttpServletRequest req,
			HttpServletResponse resp,
			@RequestParam(value = "name", required = false) String name)
			throws IOException {
		
		// 设定输出编码，帮助浏览器识别，这里不写这一行也没问题，浏览器自行选择编码即可
		resp.setContentType("application/json;charset=UTF-8;");

		System.out.println("name:" + name);

		String json = "{name:\"" + name + "\"}";
		writer.write(json);
	}
}
