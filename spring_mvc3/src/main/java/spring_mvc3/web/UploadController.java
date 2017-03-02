package spring_mvc3.web;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String upload() {
		return "upload";
	}

	/**
	 * 这种上传对应于前端的格式大概是：
	 * 
	 * Content-Type:multipart/form-data; boundary=----WebKitFormBoundaryc8oWatQHK8I6D3pP
	 * 
	 * POST内容：
	 * 
------WebKitFormBoundaryc8oWatQHK8I6D3pP
Content-Disposition: form-data; name="myfile"; filename="Shadowsocks.exe"
Content-Type: application/x-msdownload


------WebKitFormBoundaryc8oWatQHK8I6D3pP--
	 * 
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("myfile") MultipartFile myfile, Model model)
			throws IOException {
		// 顯示文件內容
		StringBuilder sb = new StringBuilder();

		// 解決中文名稱亂碼問題
		// String name = new
		// String(myfile.getOriginalFilename().getBytes("ISO8859-1"),"UTF-8");
		if (!myfile.isEmpty()) {
			sb.append("文件名称: " + myfile.getOriginalFilename() + "<br/>");
			sb.append("文件类型:" + myfile.getContentType() + "<br/>");
			sb.append("form input name:" + myfile.getName() + "<br/>");
			sb.append("文件字节:" + myfile.getBytes().length);
		} else {
			sb.append("沒有选择文件");
		}
		model.addAttribute("result", sb.toString());
		return "upload_result";
	}
}
