2011年4月15日 下午06:19:27

Spring MVC工作过程:

1) 用户发送URL request
2) 由Web.xml中的Spring Dispatcher Servlet接收
3) 转发到Controller，Controller处理后返回ModelAndView对象
4) Spring Dispatcher Servlet根据ModelAndView对象结合viewResolver渲染成HTML文件


该项目还演示了在Web容器(如Tomcat)中直接载入Spring配置文件,即把Spring作为tomcat的IoC容器


步骤：
1) 配置web.xml，主要是dispatcherServlet配置和spring配置文件的位置【通用配置，需要设置xml位置】
2) 创建Controller，推荐使用annotation【通用配置，需要改下Controller的包位置】
       使用注解就不需要实现Controller接口，直接在需要映射的方法上注解就行
