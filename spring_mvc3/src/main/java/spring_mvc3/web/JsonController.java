package spring_mvc3.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 演示输入纯文本
 */
@Controller
public class JsonController {

	@RequestMapping("/json")
	public void json(Writer writer) throws IOException {
		// 假设json是通过工具获得的jsonString
		String json = "{id:3}";
		writer.write(json);
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
