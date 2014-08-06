package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 2014年8月6日 09:08:29 ServletContextListener可以监听整个web生命周期的事件。
 * 
 * 它是对一个webapp而言的，不同的webapp有着不同的ServletContextListener
 */
public class ServletContextListenerImpl implements ServletContextListener {

	/**
	 * 当容器初始化时执行，一般spring使用这个来初始化spring容器。
	 * 在调用完该方法之后，容器再对Filter 初始化， 并且对那些在Web
	 * 应用启动时就需要被初始化的Servlet 进行初始化。
	 */
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized, context path:"
				+ sce.getServletContext().getContextPath());
	}

	/**
	 * 当Servlet 容器终止Web 应用时调用该方法。 
	 * 在调用该方法之前，容器会先销毁所有的Servlet 和Filter 过滤器。
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// ServletContext为整个webapp应用的上下文，在这个应用中都可以访问到
		System.out.println("contextDestroyed, context path:"
				+ sce.getServletContext().getContextPath());
	}

}
