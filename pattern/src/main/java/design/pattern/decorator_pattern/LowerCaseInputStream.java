package design.pattern.decorator_pattern;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LowerCaseInputStream extends FilterInputStream {

	protected LowerCaseInputStream(InputStream in) {
		super(in);
	}
	
	// 相当于拦截read方法做些处理，这个属于组合的方式
	@Override
	public int read() throws IOException {
		int c = super.read();
		return (c == -1) ? c : Character.toLowerCase((char)c);
	}
	
	// 还需要拦截read byte这个方法，这个有个问题，作为装饰者实现方来说，
	// 我怎么知道只需要实现read和read byte这两个方法就够了？
	@Override
	public int read(byte[] b, int offset, int len) throws IOException {
		int result = super.read(b, offset, len);
		// 注意这里result可能是-1的，但逻辑没有问题的
		for(int i = offset; i < offset + result; i++) {
			b[i] = (byte) Character.toLowerCase((char) b[i]);
		}
		return result;
	}
	
	// 单元测试
	public static void main(String[] args) throws IOException {
		String str = "Hello World";
		InputStream in = new ByteArrayInputStream(str.getBytes());
		
		// 包一下装饰者
		InputStream lowCaseIn = new LowerCaseInputStream(in);
		
		// 读取
		int c;
		while((c = lowCaseIn.read()) > 0) {
			System.out.print((char)c);
		}
		
		in.close();
		lowCaseIn.close();
	}

}
