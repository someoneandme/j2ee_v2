package spring_mvc3.web;

import java.io.Writer;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 2015年8月12日 23:23:55
 * 
 * 需要配置：StringToDateConverter
 */
@Controller
public class ConverterController {
	
	@RequestMapping("/test_converter")
	public void test(Writer writer, 
			@RequestParam("date") Date date) throws Exception {
		if(date != null) {
			writer.write(date.toString());
		}
	}
	
}
