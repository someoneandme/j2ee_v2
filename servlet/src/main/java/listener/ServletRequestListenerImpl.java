package listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 当request对象初始化后和销毁时调用，每次请求就会产生一个request对象
 */
public class ServletRequestListenerImpl implements ServletRequestListener {

	public void requestDestroyed(ServletRequestEvent sre) {
		HttpServletRequest request = (HttpServletRequest) sre
				.getServletRequest();
		System.out.println("----发向" + request.getRequestURI() + "请求被销毁----");
	}

	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest request = (HttpServletRequest) sre
				.getServletRequest();
		System.out.println("----发向" + request.getRequestURI() + "请求被初始化----");
	}

}
