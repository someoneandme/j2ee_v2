这里是Spring最基本的特性、配置和使用。包括：

IOC: Spring的核心是bean容器，管理了bean的创建和销毁、bean之间的依赖关系。

AOP: 切面注入，特别适合于对既有系统的改造。
利用java proxy代理或CGLIB代理，实现对已有类的透明代理，
在类方法的执行之前、执行之后、抛出异常等切入点执行代码。
如果一个类的class是com.sun.Proxy，那么是java自带的动态代理，要求接口实现。
如果一个类的class包含EnhancerByCGLIB$$这样的字样，那么是CGLIB代理实现的。

如果配置是<aop:aspectj-autoproxy/>，那么spring采用java自带的代理。
如果配置是<aop:aspectj-autoproxy proxy-target-class="true"/>，
那么则使用CGLIB，此时这个代理类完全可以当作被代理的对象来用。

例子：
[http://www.mkyong.com/spring3/spring-aop-aspectj-annotation-example/]


【发现一个重要问题】
如果我把LoggingAspect移动到例如pakage aaaa下时，
就会出现java.lang.IllegalArgumentException: warning no match for this type name: CustomerBo
这就要求LoggingAspect必须和CustomerBO放在同一个包下!!!