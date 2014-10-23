以前spring是靠quartz来配置定时任务。
但是quartz的定位很不清晰，产品感很弱。
仅仅在动态配置或db配置方面就很弱。

之前的项目quartz仅仅作为静态配置的任务。

java本身有Timer支持调度，spring现在自己也支持了。

@Scheduled   @Aysnc


【关于@Scheduled在web容器中执行了2次的问题】
http://stackoverflow.com/questions/14242310/java-spring-scheduled-tasks-executing-twice
http://stackoverflow.com/questions/3672289/spring-3-scheduled-task-running-3-times
只要使用@Service或@Component之类注解了类的，就会执行了2次
[原因]一般都是有org.springframework.web.context.ContextLoaderListener使得配置文件被加载了2次
所以临时解决方法就是去掉org.springframework.web.context.ContextLoaderListener这一块
其次业务还是要做到可重入比较好。

// TODO
需要进一步测试：这样是不是起了2个spring context，如果是，那就太糟糕了。

但是我测试了spring mvc3那个例子，发现每个bean只有一个实例，真奇怪。
