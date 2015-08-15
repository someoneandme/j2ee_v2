package spring_mvc3.web;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import spring_mvc3.web.form.Student;

/**
 * 2015年8月15日 09:58:25
 */
@Controller
public class TestInitBinder {
	
	/**
	 * initBinder会在入参时执行，没有返回参数
	 * 实际上还可以指定特别对哪个入参POJO进行检查
	 * 
	 * 注意：这个过滤对@RequestParam("age")无效
	 * 
	 * @param binder
	 */
	@InitBinder
	public void myInitBinder(WebDataBinder binder) {
		binder.setDisallowedFields("age"); // 设置不要age参数
	}

	@RequestMapping("/test_init_binder")
	public void testInitBinder(Writer writer,
			Student student) throws IOException {
		writer.write("name=" + student.getName()
				+ ",age=" + student.getAge());
	}
	
}
