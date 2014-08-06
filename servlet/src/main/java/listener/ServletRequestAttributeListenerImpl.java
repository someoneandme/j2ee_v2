package listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

/**
 * 监听Servlet Request属性的添加删除的事件
 */
public class ServletRequestAttributeListenerImpl implements
		ServletRequestAttributeListener {

	public void attributeAdded(ServletRequestAttributeEvent srae) {
		ServletRequest request = srae.getServletRequest();
		// 获取添加的属性名和属性值
		String name = srae.getName();
		Object value = srae.getValue();
		System.out
				.println("req范围内添加了名为" + name + "，值为" + value + "的属性!");
	}

	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		ServletRequest request = srae.getServletRequest();
		// 获取添加的属性名和属性值
		String name = srae.getName();
		Object value = srae.getValue();
		System.out
				.println("req范围内删除了名为" + name + "，值为" + value + "的属性!");
	}

	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		ServletRequest request = srae.getServletRequest();
		// 获取添加的属性名和属性值
		String name = srae.getName();
		Object value = srae.getValue();
		System.out
				.println("req范围内修改了名为" + name + "，值为" + value + "的属性!");
	}

}
