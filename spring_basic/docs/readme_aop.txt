2016年1月5日 17:42:30

AOP的使用有2种方式：
====1) 注解的方式 ====
* 配置到xml去扫描哪个包之后，xml文件就不需要再动了
* 需要AOP的接口或类不需要有任何AOP的代码或注解
* Aspect的实现类，通过各种注解，在同一个类里实现了整个Aspect，不需要额外配置xml

====2) 使用ProxyFactoryBean创建==== (http://uule.iteye.com/blog/869309)
* 只是spring最基本的方式，最简单的使用方式，但每个要注入的bean都需要单独配置一次（可配置多个advice）
* 项目中常用的是自动代理，详见3)

====3） 使用AbstractAutoProxyCreator的子类实现自动代理====
* spring自带有BeanNameAutoProxyCreator,可以按照spring的bean id来指定切面
* Druid中带有一个BeanTypeAutoProxyCreator，可以按照类的名称来指定切面，实际中，很实用

**** 2)和3) 中的通知类型接口
Around => org.aopalliance.intercept.MethodInterceptor
Before => org.springframework.aop.BeforeAdvice
After  => org.springframework.aop.AfterReturningAdvice
Throws => org.springframework.aop.ThrowsAdvice
