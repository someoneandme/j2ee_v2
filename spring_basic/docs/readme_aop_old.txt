2010年12月16日 下午09:01:02

AOP的支持需要aspectj.jar包,特别注意：JDK1.6版本要使用高版本的aspectj，否则报错。

AOP有两种配置方式：注解和xml配置文件。

要使用AOP的类必须实现“接口”！

下面以2个类来演示：
UserManager接口：提供服务的接口，未侵入AOP任何东西
UserManagerImpl类：实现UserManager接口，未侵入AOP任何东西
CheckAspect类：切面类，负责代理检查安全性

1）注解实现方式：
在CheckAspect1类中描述Pointcut和Advice（该类不涉及UserManager任何东西）
在applicationContext-1.xml配置文件中配置

2）配置文件方式
CheckAspect2中不需要设定任何与Aspect相关的东西
CheckAspect2还演示了获取参数和返回值的方法


附：AOP主要概念：	
Cross Cutting Concern
	是一种独立服务，它会遍布在系统的处理流程之中
Aspect	
	对横切性关注点的模块化
Advice
	对横切性关注点的具体实现
Pointcut
	它定义了Advice应用到哪些JoinPoint上，对Spring来说是方法调用
	了解表达式的基本语法：
	* 匹配返回值
	* 匹配包
	* 匹配方法
	* 匹配参数
JoinPoint
    Advice在应用程序上执行的点或时机，Spring只支持方法的JoinPoint，这个点也可以使属性修改，如：Aspecj可以支持
Weave
	将Advice应用到Target Object上的过程叫织入，Spring支持的是动态织入
Target Object
	Advice被应用的对象
Proxy
	Spring AOP默认使用JDK的动态代理，它的代理是运行时创建，也可以使用CGLIB代理
Introduction
	可以动态的为类添加方法