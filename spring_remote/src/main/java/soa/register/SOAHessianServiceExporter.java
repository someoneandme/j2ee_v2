package soa.register;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.context.ServletConfigAware;

/**
 * 用于自动向配置中心注册当前提供的服务的信息，最主要的就是连接地址:
 * http://localhost:8080/hessianweb/_remote/helloServiceExporter
 * 它是动态的，根据web容器不同而不同
 * 
 * @author pugwoo
 */
public class SOAHessianServiceExporter extends HessianServiceExporter implements
		 ServletConfigAware{
	
	private String beanName; // 就是url，由注解HessianServiceScanner注入
	
	/**
	 * 获取当前容器的ipv4的访问ip:port
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getEndPoints() throws Exception {
		List<String> endPoints = new ArrayList<String>();
		Pattern ipv4Pattern = Pattern.compile("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$");
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objs = mbs.queryNames(new ObjectName(
				"*:type=Connector,*"), Query.match(Query.attr("protocol"),
				Query.value("HTTP/1.1")));
		String hostname = InetAddress.getLocalHost().getHostName();
		InetAddress[] addresses = InetAddress.getAllByName(hostname);
		for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
			ObjectName obj = i.next();
			String scheme = mbs.getAttribute(obj, "scheme").toString();
			String port = obj.getKeyProperty("port");
			for (InetAddress addr : addresses) {
				String host = addr.getHostAddress();
				if(host != null && ipv4Pattern.matcher(host).find()) {
					String ep = scheme + "://" + host + ":" + port;
					endPoints.add(ep);
				}
			}
		}
		return endPoints;
	}

	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		List<String> urls = new ArrayList<String>();
		ServletContext servletContext = servletConfig.getServletContext();
		try {
			List<String> endPoints = getEndPoints();
			String contextPath = servletContext.getContextPath();
			String servletName = servletConfig.getServletName();
			File webXml = new File(servletContext.getRealPath("/WEB-INF/web.xml"));
			
			List<String> urlMappings = getServletUrlMapping(webXml, servletName);
			if(urlMappings.isEmpty()) {
				throw new Exception("servlet url mapping not found,servlet:"
						+ servletName);
			}
			
			// 只取出第一个，并去掉*
			String urlMapping = urlMappings.get(0).trim().replace("*", "");
			
			for(String endPoint : endPoints) {
				urls.add(endPoint + contextPath + urlMapping + beanName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// report exception
		}
		
		for(String url : urls) {
			System.out.println(url); // TODO 上报到配置中心
			// TODO 这里或可以做成一个心跳模式的上报，以免配置中心失败或抽风
		}
	}
	
	/**
	 * 读取web.xml来获取到servletName对应的url-mapping的原始值，可能有多个或0个
	 * 
	 * @param servletName
	 * @return
	 * @throws FileNotFoundException 
	 */
	private List<String> getServletUrlMapping(File webXml, String servletName)
			throws IOException {
		List<String> urlMappings = new ArrayList<String>();
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(webXml));
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line); // 已经去掉回车符
		}
		br.close();
		
		String xml = sb.toString();
		
		// 删除注释
		String regexPattern = "<!--.[^-]*(?=-->)-->";
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(xml);
		xml = matcher.replaceAll("");
		
		// 通过正则表达式找到servletName对应url mapping
		regexPattern = "<servlet-name>\\s*" + servletName 
				+ "\\s*</servlet-name>\\s*<url-pattern>(.*?)</url-pattern>";
		pattern = Pattern.compile(regexPattern);
		matcher = pattern.matcher(xml);
		while(matcher.find()) {
			String str = matcher.group(1);
			if(str != null) {
				urlMappings.add(str);
			}
		}
		
		return urlMappings;
	}
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
}
