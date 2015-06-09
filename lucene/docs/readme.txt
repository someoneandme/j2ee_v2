理解核心类：
IndexWriter  可以看成是对索引进行写操作的对象
Directory  代表一个Lucene索引的位置   子类：RAMDirectory  FSDirectory
Analyzer 分词、提取关键词、去除不需要的信息
常用的有StandardAnalyzer分析器,StopAnalyzer分析器,WhitespaceAnalyzer分析器等
Document 代表字段的集合，可以想象成一块数据
Field  每个Document含有一个或多个字段，抽象为Field
IndexSearch 相对于IndexWriter，这个用来搜索索引
Query 查询语句，有模糊查询、语义查询、短语查询、组合查询等
QueryParser: 是一个解析用户输入的工具，可以通过扫描用户输入的字符串，生成Query对象。 
Hits:在搜索完成之后，需要把搜索结果返回并显示给用户，只有这样才算是完成搜索的目的。
在lucene中，搜索的结果的集合是用Hits类的实例来表示的。 

附带：能抓取网页的工具：Nutch Grub Heritrix  ApacheDroidsProjecet Aperture

以三本书，每本书由书名、目录和正文三部分构成
操作步骤：
1)创建一个Analyzer对象，用于分词，可用new StandardAnalyzer()
创建一个Directory对象，用于存放Index文件
用刚才创建的Analyzer对象和Directory对象创建一个IndexWriter对象

2)对于每一本书，各创建一个Document对象，如doc1,doc2,doc3; 用new Document()
对于每本书，即每个doc，创建三个Field对象，分别存放书名、目录和正文
new Field("fieldname", text, Field.Store.YES,Field.Index.ANALYZED));  
其中text是String，如果text是来自于文件txt等，则得先把txt文件读出来成为String类型。
然后将三个field对象加入到doc中，每个doc都是这么做的

3)将三个Document对象加入到IndexWriter对象中
关闭IndexWriter

//以上就完成了索引部分

4)和第一步一样创建一个Directory对象，指明Index文件在哪里
然后通过这个Directory对象创建一个IndexSearcher对象

5)通过第一步创建的Analyzer对象和Field名称，创建一个QueryParser对象，用来对查询语句进行分词
通过向QueryParser传入要查询的语句，返回Query对象

6)通过IndexSearcher对象的search方法，传入Query对象，获得查询结果Hits对象
Hits对象保存了查询结果，通过Hits对象可以获得查询结果的所有内容

http://today.java.net/pub/a/today/2003/07/30/LuceneIntro.html