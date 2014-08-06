[Servlet 工作原理解析]
https://www.ibm.com/developerworks/cn/java/j-lo-servlet/

执行顺序：
-> listener 此时会准备ServletContext和RequestContext和初始化servlet（如果不是一开始就初始）
-> filter 
-> servlet

按照范围大小排序的各种context上下文：
ServletContext 代表一个webapp的上下文, 
           在Web 应用的生命周期中，ServletContext 对象最早被创建，最晚被销毁。
           每个ServletContext都会单独起一个ClassLoader并加载webapp的jar包，也就是每个webapp相对独立。

RequestContext是每个请求就会创建一个请求的上下文。
