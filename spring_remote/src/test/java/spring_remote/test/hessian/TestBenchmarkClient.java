package spring_remote.test.hessian;

import spring_remote.api.service.IUserService;

/**
 * 2016年5月6日 22:49:25
 * 
 * 下面单线程压测了本机，4250次/秒，最简单的逻辑和参数，可以认为是hessian本事的消耗。
 * 多线程测试：2个线程 4500次/秒
 * 多线程测试，10个线程：8075次/秒
 */
public class TestBenchmarkClient {

	public static void main(String[] args) {
		final IUserService userService = RemoteService.get(IUserService.class);

		System.out.println("start at:" + System.currentTimeMillis());
		
		for(int thread = 0; thread < 2; thread++) {
			final int _thread = thread;
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < 100000; i++) {
						String result = userService.sayHello("nick");
						if(!"hello: nick".equals(result)) {
							System.err.println("error with result:" + result);
						}
					}
					System.out.println("thread" + _thread + " end at:" +
					    System.currentTimeMillis());
				}
			}).start();;
		}
	}
}
