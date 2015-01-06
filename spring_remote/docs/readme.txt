Spring Remote表示基于spring的远程调用

目前这里涉及的远程调用方式及说明：
1) RMI，java自带，spring封装得更加容易使用
   无需web容器，直接启动，程序见test/java
   可惜rmi只支持java，不支持其它语言
2) hessian，这里示例的是spring mvc+hessian
   这样hessian就不需要每个service都注册到web.xml了
   http://haohaoxuexi.iteye.com/blog/1869488

hessian不支持方法重载，即不支持相同方法名不同参数。
开启需要<property name="overloadEnabled" value="true" /> 

【注意，由于和spring整合时版本问题，hessian只能用3.x的最新版】
相关链接：
http://stackoverflow.com/questions/10553046/hessianconnectionexception-http-500-error-when-using-hessian-4-0-7-spring-3
【重要】用4.x的解决方法：把spring-remoting去掉即可！太好了！

在项目示例中，spring_remote.api包用于分发给其它应用，相当于协议文件。

2015年1月6日 10:00:07
hessian协议相关文档:http://wenku.baidu.com/view/3832199951e79b8968022641.html