package com.pugwoo;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.pugwoo.entity.Result;

/**
 * 2012年11月10日 下午04:24:20
 */
public class HessianClientTest {
	public static void main(String[] args) {
		String url = "http://localhost:8080/hessian/hessian/math";
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true);// 【重要】这样才支持重载接口（当然不重载也支持）
		MathService math = null;
		try {
			math = (MathService) factory.create(MathService.class, url);
		} catch (MalformedURLException e) {
			System.out.println("occur exception: " + e);
		}
		Result result = math.add(3, 2);
		System.out.println("3 + 2 = " + result.getResult());
	}
}