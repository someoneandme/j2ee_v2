package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * 2014年8月6日 10:04:51 监听application范围（也就是ServletContext）的属性的添加删除。
 */
public class ServletContextAttributeListenerImpl implements
		ServletContextAttributeListener {

	public void attributeAdded(ServletContextAttributeEvent scab) {
		ServletContext application = scab.getServletContext();
		
		String name = scab.getName();
		Object value = scab.getValue();
		System.out.println("app范围内添加了名为" + name + "，值为" + value
				+ "的属性!");
	}

	public void attributeRemoved(ServletContextAttributeEvent scab) {
		ServletContext application = scab.getServletContext();
		
		String name = scab.getName();
		Object value = scab.getValue();
		System.out.println("app范围内删除了名为" + name + "，值为" + value
				+ "的属性!");
	}

	public void attributeReplaced(ServletContextAttributeEvent scab) {
		ServletContext application = scab.getServletContext();
		
		String name = scab.getName();
		Object value = scab.getValue();
		System.out.println("app范围内修改了名为" + name + "，值为" + value
				+ "的属性!");
	}

}
