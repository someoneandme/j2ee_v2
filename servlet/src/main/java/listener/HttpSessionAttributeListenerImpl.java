package listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * 当session的属性发生变化时调用
 */
public class HttpSessionAttributeListenerImpl implements
		HttpSessionAttributeListener {

	public void attributeAdded(HttpSessionBindingEvent se) {
		// 获取添加的属性名和属性值
		String name = se.getName();
		Object value = se.getValue();
		System.out
				.println("session范围内添加了名为" + name + "，值为" + value + "的属性!");
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		// 获取添加的属性名和属性值
		String name = se.getName();
		Object value = se.getValue();
		System.out
				.println("session范围内删除了名为" + name + "，值为" + value + "的属性!");
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		// 获取添加的属性名和属性值
		String name = se.getName();
		Object value = se.getValue();
		System.out
				.println("session范围内修改了名为" + name + "，值为" + value + "的属性!");
	}

}
