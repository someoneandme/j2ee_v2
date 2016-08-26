Zookeeper
============================================

官方网站[zookeeper.apache.org](http://zookeeper.apache.org/), 本文不涉及下载和启动。Zookeeper是Paxos算法的实现。

Zookeeper本身就是为注册中心、配置中心设计的，提供树形目录结构的存储方式，这点和unix文件结构是一样的，每个节点ZNode存放的数据一般都比较小，不超过1M。Zookeeper会保证写入的串行。Zookeeper提供的是最终一致性。

### 典型应用场景：

1. **DNS场景、Key-Value配置中心** Zookeeper的树形路径是全局唯一的，应用可以使用这个唯一不变的名称，而这个节点上存放的信息是可变的。当信息变化时，客户端通过Watcher可以马上感知到。

2. **组员管理、在线机器IP管理** Zookeeper建立一个唯一的路径目录，当客户端机器启动后，往这个路径目录写入一条临时性的带有机器唯一标识的节点，节点可以存放自定义的信息。当机器下线时，Zookeeper会把该临时性节点删除。

3. **互斥锁、读写锁** 保证多个线程，只有一个可以创建指定的临时节点，而创建成功的线程将执行任务。完成任务或超时之后，删除该节点。其他线程就可以继续抢占。还有另外一种是，创建一个临时的顺序结点(Ephemeral Sequential) `/locks/lock_${seq}`，序号小的线程拥有执行权。

4. **屏障、双屏障** 控制等等若干进程执行完之后，再做某件事。或者控制所有进程都准备好了才一起执行，全部执行完了再做某件事。

### 重要概念：

1. **ZNode** 分为Regular ZNode, 用户需要显式的创建、删除；Ephemeral ZNode，用户创建它之后，可以显式的删除，也可以在创建它的Session结束后，由ZooKeeper Server自动删除。另外节点有正常节点和id序列自增的节点，这点和MySQL的自增id是一样的道理。

2. **Session** 若干台Zookeeper Server对于客户端是无状态的，客户端通过心跳自动切换zookeeper server。也就是客户端需要配置多台服务器端的地址，而非服务器端自动切换。
 
3. **Watcher** 客户端可以在某个节点上注册一个Watcher,监听该节点的变化。ZooKeeper中的Watcher是一次性的，即触发一次就会被取消，如果想继续Watch的话，需要客户端重新设置Watcher。这个特性非常重要，解决了配置实时性的问题。

### Zookeeper与Redis的区别

Redis可以满足Zookeeper配置中心的需求，也可以有Watcher监听值的变化，也可以实现自增seq的特性。但Redis没有Zookeeper在客户端断线的情况下，自动删除掉的特性。但是Redis的性能要更好。如果可能，我会希望用Redis来代替Zookeeper。