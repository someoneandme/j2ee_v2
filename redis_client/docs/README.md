## 关于Redis的Java客户端

目前redis官方的[Java客户端列表](http://redis.io/clients#java)中，推荐的是Jedis、Redisson和lettuce。

还有一个没有列在官方列表中的是Spring Data Redis。

经过试用之后，发现还是jedis比较直接简单，推荐使用。Spring Redis的设计有些复杂。

## 部分Redis完整的功能，移到woo-utils中

1) Redis Limit 限制单位时间内的访问频率

2) Redis Transation 基于Redis的分布式锁。

上述的小项目在本应用中关闭，会在woo-utils中维护。