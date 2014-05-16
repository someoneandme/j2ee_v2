package spring_mvc3.web;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 演示输入纯文本
 */
@Controller
public class JsonController {

	@RequestMapping("/json")
	public void json(Writer writer) throws IOException{
		// 假设json是通过工具获得的jsonString
		String json = "{id:3}";
		writer.write(json);
	}
}
