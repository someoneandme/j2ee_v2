2014年8月13日 14:35:40

Spring Schema可以扩展xml schema，
使得xml定义bean的时候语义更加丰富，编译时也可以检查。

完成一个自定义配置一般需要以下步骤：

-) 设计配置属性和JavaBean
-) 编写XSD文件,这个文件用于编译期检查xml文件
-) 编写NamespaceHandler和BeanDefinitionParser完成解析工作
-) 编写spring.handlers和spring.schemas串联起所有部件
   spring.handlers是xmlns到Handler处理类的映射
   spring.schemas是xmlns.xsd到真实的xsd文件的位置
-) 在Bean文件中应用

参考教程：http://www.cnblogs.com/jifeng/archive/2011/09/14/2176599.html

其它教程：
http://mozhenghua.iteye.com/blog/1830842
http://mozhenghua.iteye.com/blog/1914155