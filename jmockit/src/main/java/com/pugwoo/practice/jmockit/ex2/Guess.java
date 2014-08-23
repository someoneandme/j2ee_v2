package com.pugwoo.practice.jmockit.ex2;

/**
 * 2014年8月23日 15:56:53 在n次机会随机猜骰子点数 ,结果保存到数据库中
 */
public class Guess {
	
	private int maxTryTime; // 最大重试次数
	private int tryTime = 0; // 当前重试次数
	private int number = (int) (Math.random() * 6); // 目标数字，对象构造的时候就已经随机生成
	private GuessDAO guessDAO; // 持久化依赖

	public Guess(int maxRetryTime) {
		this.maxTryTime = maxRetryTime;
	}

	public void doit() {
		while (tryTime++ < maxTryTime && !tryIt()) {
			// 到达最大尝试次数仍不成功则调用handle
			if (tryTime == maxTryTime) {
				failHandle();
			}
		}
	}

	// 最坏情况下调用maxRetryTime次tryIt(),猜中则保存信息
	public boolean tryIt() { 
		if (number == randomGuess()) {
			guessDAO.saveResult(true, number);
			return true;
		}
		return false;
	}

	public void failHandle() { // 失败处理，猜不中时调用
		guessDAO.saveResult(false, number);
	}

	private int randomGuess() { // 猜的随机过程
		return (int) (Math.random() * 6);
	}

	public void setGuessDAO(GuessDAO guessDAO) {
		this.guessDAO = guessDAO;
	}
}