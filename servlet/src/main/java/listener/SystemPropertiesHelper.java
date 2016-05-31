package listener;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * 适合在tomcat起来的时候设置一些系统变量
 */
public class SystemPropertiesHelper implements javax.servlet.ServletContextListener {
	
	private ServletContext context = null;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		Enumeration<String> params = context.getInitParameterNames();

		String prefix = "SystemProperty:";
		
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			String value = context.getInitParameter(param);
			if (param != null && param.startsWith(prefix)) {
				System.setProperty(param.substring(prefix.length()), value);
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}