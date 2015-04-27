2011年4月15日 下午06:19:27

Spring MVC工作过程:

1) 用户发送URL request
2) 由Web.xml中的Spring Dispatcher Servlet接收
3) 转发到Controller，Controller处理后返回ModelAndView对象
4) Spring Dispatcher Servlet根据ModelAndView对象结合viewResolver渲染成HTML文件

步骤：
1) 配置web.xml，主要是dispatcherServlet配置和spring配置文件的位置【通用配置，需要设置xml位置】
2) 创建Controller，推荐使用annotation【通用配置，需要改下Controller的包位置】
       使用注解就不需要实现Controller接口，直接在需要映射的方法上注解就行

2013年3月18日 16:20:05
演示页面传递数组、大量form表单的例子

2014年11月1日 20:25:41
关于velocity使用layout的例子：
http://www.oschina.net/question/12_4580

2015年4月27日 11:30:09
除了@ResponseBody可以将返回作为response body用，
还有@RequestBody可以将post过来的所有内容作为输入信息用，这一点和微信的api很匹配