这里是Spring最基本的特性、配置和使用。包括：

AOP: 切面注入，特别适合于对既有系统的改造。
利用java proxy代理或CGLIB代理，实现对已有类的透明代理，
在类方法的执行之前、执行之后、抛出异常等切入点执行代码。
如果一个类的class是com.sun.Proxy，那么是java自带的动态代理，要求接口实现。
如果一个类的class包含EnhancerByCGLIB$$这样的字样，那么是CGLIB代理实现的。

例子：
[http://www.mkyong.com/spring3/spring-aop-aspectj-annotation-example/]
