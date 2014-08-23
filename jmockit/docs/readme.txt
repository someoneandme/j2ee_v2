【eclipse的jre必须是jdk】

入门例子：http://regbin.iteye.com/blog/1153615
Expectations(.class){}这种方式只会模拟区域中包含的方法，这个类的其它方法将按照正常的业务逻辑运行。

Expectations块里声明的mock方法，是一定要被执行的，如果没有被执行，会认为整个测试case不通过；
NonStrictExpectations就没有这个限制。NonStrictExpectations的角色和Expectations一致。

复杂一点的例子：http://blog.csdn.net/ultrani/article/details/8993364
（这篇博客的例子跑不起来，可能版本比较老，所以修改了一下）

==简单的道理==
mock就是把依赖的方法的返回值设定为自己想要的，行为上属于AOP，拦截指定的方法并返回我想返回的值。
