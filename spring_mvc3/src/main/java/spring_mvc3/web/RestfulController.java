package spring_mvc3.web;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Restful演示
 * @author Pugwoo
 *
 */
@Controller
public class RestfulController {

	/**
	 * 在RequestMapping中，变量用{}括起来
	 * 然后在处理方法中用@PathVariable获取，名称要一致
	 * @PathVariable 里面也可以加上value="xxx"，名称可以不一致
	 * 
	 * @param writer
	 * @throws IOException
	 */
	@RequestMapping("/{name}/page/{p}")
	public void handleRequest(Writer writer, @PathVariable String name,
			@PathVariable Integer p) throws IOException{
		writer.write("you are reading " + name +"'s page " + p);
	}
	
}
