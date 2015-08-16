package spring_mvc3.web;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {

	@RequestMapping("upload")
	public ModelAndView uploadFile(@RequestParam("myfile") MultipartFile myfile)
			throws IOException {
		ModelAndView modelAndView = new ModelAndView("result");

		// 顯示文件內容
		StringBuilder sb = new StringBuilder();

		// 解決中文名稱亂碼問題
		// String name = new
		// String(myfile.getOriginalFilename().getBytes("ISO8859-1"),"UTF-8");
		if (!myfile.isEmpty()) {
			sb.append("文件名稱: " + myfile.getOriginalFilename() + "<br/>");

			String content = new String(myfile.getBytes());
			content = content.replace("\n", "<br/>");
			sb.append(content);
		} else {
			sb.append("沒有選擇文件");
		}
		modelAndView.addObject("result", sb.toString());
		return modelAndView;
	}
}
