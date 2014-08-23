package com.pugwoo.practice.jmockit.ex2;

/**
 * 2014年8月23日 15:56:35
 * 没有实现,仅演示依赖
 */
public class GuessDAO {

	public void saveResult(boolean b, int number) {
		System.out.println("succ:" + b + "target number:" + number);
	}
	
}
