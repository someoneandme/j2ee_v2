package spring_mvc3.web;

import java.io.IOException;

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
 * 
 * 2016年2月17日 19:59:22
 * 这种下载方式可能还有问题，因为byte[]全部存在内存中，如果下载文件大，会爆内存
 */
@Controller
public class DownloadController {

	@RequestMapping("/test_response_entity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session)
			throws IOException {
		byte[] body = "hello,world".getBytes();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=a.txt");

		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body,
				headers, HttpStatus.OK);
		return response;
	}
	
	// 另一种方式就是直接拿outputstream来写
}
