package spring_mvc3.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2015年8月16日 01:34:10
 * 
 * 使用ResponseEntity实现下载功能，还是优先推荐使用这种方式
 */
@Controller
public class DownloadController {

	@RequestMapping("/test_response_entity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session)
			throws IOException {
		byte[] body = null;
		ServletContext servletContext = session.getServletContext();
		InputStream in = servletContext.getResourceAsStream("/index.jsp");
		body = new byte[in.available()];
		in.read(body);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=index.jsp");

		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body,
				headers, HttpStatus.OK);
		return response;
	}
}
