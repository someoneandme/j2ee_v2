package com.pugwoo;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.pugwoo.MathService;

/**
 * 2012年11月10日 下午04:24:20
 */
public class HessianClientBenchmark {
	public static void main(String[] args) {
		String url = "http://localhost:8080/hessian/hessian/math";
		HessianProxyFactory factory = new HessianProxyFactory();
		MathService math = null;
		try {
			math = (MathService) factory.create(MathService.class, url);
		} catch (MalformedURLException e) {
			System.out.println("occur exception: " + e);
		}
		
		int errcount = 0;
		long start = System.currentTimeMillis();
		// 当循环次数达到几千时，无论是新建一个MathService还是共用
		// 都几乎调用失败，不知道是hessian的问题，还是tomcat的问题，还是windows上跑的socket连接数问题
		for(int i = 0; i < 100000; i++) {
			try {
//				math = (MathService) factory.create(MathService.class, url);
				math.add(i, i);
			} catch (Exception e) {
			    errcount++;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
		System.out.println("errcount:" + errcount);
	}
}