package listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听Session的创建和销毁.
 * 
 * 【注】并不是每个用户一访问页面就会有session的，
 * 而是显式地从request中获取session时，才会触发创建session。
 * 
 */
public class HttpSessionListenerImpl implements HttpSessionListener {

	/**
	 * session创建时
	 */
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("session created, id:" + se.getSession().getId());
	}

	/**
	 * session销毁时
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("session destroyed, id:" + se.getSession().getId());
	}

}
