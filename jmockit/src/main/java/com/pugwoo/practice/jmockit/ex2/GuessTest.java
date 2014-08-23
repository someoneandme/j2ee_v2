package com.pugwoo.practice.jmockit.ex2;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

import org.junit.Test;

public class GuessTest {

	@Tested
	// 表明被修饰实例是将会被自动构建和注入的实例
	Guess guess = new Guess(3);

	@Injectable
	// 表明被修饰实例将会自动注入到@Tested修饰的实例中，并且会自动mock掉，除非在测试前被赋值
	GuessDAO guessDAO;

	/**
	 * 测试连续3次失败的情况
	 */
	@Test
	public void behaviorTest_fail3time() {
		new Expectations(Guess.class) { // Expectations中包含的内部类区块中，体现的是一个录制被测类的逻辑。
			{
				guess.tryIt(); // 期待调用Guess.tryIt()方法
				result = false; // mock掉返回值为false（表明猜不中）
				times = 3; // 期待以上过程重复3次

				// 期待调用guessDAO把猜失败的结果保存
				// 如果程序没有调到这个方法，同时第一个参数是false，则报错
				guessDAO.saveResult(false, anyInt);
			}
		};

		guess.doit(); // 录制完成后，进行实际的代码调用，也称回放(replay)
	}

//	/**
//	 * 两次失败，第三次猜数成功
//	 */
//	@Test
//	public void behaviorTest_sucecess() {
//
//		new Expectations(Guess.class) { // 构造函数可以传入Class或Instance实例
//			{
//				guess.tryIt();
//				result = false;
//				times = 2;
//				invoke(guess, "randomGuess", new Object[] {}); // invoke()能调用私有方法
//				result = (Integer) getField(guess, "number"); // getField()能操作私有成员
//				guessDAO.saveResult(true, (Integer) getField(guess, "number"));
//			}
//		};
//		guess.doit();
//	}

}
