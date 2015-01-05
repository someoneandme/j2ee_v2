package test.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 2015年1月5日 17:13:11
 * 打印inputstream的二进制内容
 */
public class PrintInputStream {

	public static void print(InputStream in) throws IOException {
		int BYTE_PER_LINE = 8;
		byte[] bytes = new byte[BYTE_PER_LINE];
		int readBytes = 0;
		while((readBytes = in.read(bytes)) >= 0) {
			for(int i = 0; i < readBytes; i++) {
				System.out.printf("%02X ", bytes[i]);
			}
			for(int i = readBytes; i < BYTE_PER_LINE; i++) {
				System.out.print("   ");
			}
			System.out.println(new String(bytes, 0, readBytes));
		}
	}
	
	public static void main(String[] args) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream("hello,world".getBytes());
		print(in);
	}
	
}
