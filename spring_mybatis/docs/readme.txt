中文官方文档：http://mybatis.github.io/spring/zh/

教程：
1) http://www.sivalabs.in/2012/10/mybatis-tutorial-part1-crud-operations.html
2) http://www.sivalabs.in/2012/10/mybatis-tutorial-part-3-mapping.html
3) http://www.sivalabs.in/2012/10/mybatis-tutorial-part4-spring.html

注意：MySql的MYISAM引擎对rollback无效。

注意：先创建数据库，再建表，然后写入到jdbc.properties文件中。

=== 关于生成代码 ===
http://wiki.jikexueyuan.com/project/mybatis-in-action/code-generation-tool.html
有工具可以读数据库的表结构，然后生成mybatis的mapper xml文件，但这种方式我并不看好，因为它对修改mapper文件或新增表字段并不友好。