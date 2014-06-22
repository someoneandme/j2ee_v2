package com.pugwoo.impl;

import com.caucho.hessian.server.HessianServlet;
import com.pugwoo.MathService;
import com.pugwoo.entity.Result;

/**
 * 2012年11月10日 下午04:24:21
 */
@SuppressWarnings("serial")
public class HessianMathService extends HessianServlet implements MathService {

	public Result add(int a, int b) {
		Result result = new Result();
		result.setResult(a + b);
		return result;
	}
}