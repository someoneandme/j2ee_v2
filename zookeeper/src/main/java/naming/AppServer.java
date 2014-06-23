package naming;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * http://coolxing.iteye.com/blog/1871520 
 * 使用zookeeper作为服务注册中心
 * 利用zookeeper的EPHEMERAL_SEQUENTIAL特性
 * 
 * 【重要】
 * 需要确认zookeeper中是否已经存在"/sgroup"节点了, 
 * 如果不存在, 则创建该节点. 
 * 如果存在, 最好先将其删除, 然后再重新创建.
 * create /sgroup test
 */
public class AppServer {

	private String groupNode = "sgroup";
	private String subNode = "sub";

	/**
	 * 将server的address注册到zookeeper
	 * 
	 * @param address
	 *            server的ip地址
	 */
	public void registerZookeeper(String address) throws Exception {
		ZooKeeper zk = new ZooKeeper(Config.zookeeperAddr, 10000,
				new Watcher() {
					public void process(WatchedEvent event) {
						// 不做处理
					}
				});
		
		// 在"/sgroup"下创建子节点
		// 子节点的类型设置为EPHEMERAL_SEQUENTIAL, 表明这是一个临时节点, 且在子节点的名称后面加上一串数字后缀
		// 将server的地址数据关联到新创建的子节点上
		String createdPath = zk.create("/" + groupNode + "/" + subNode,
				address.getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println("create: " + createdPath);
	}

	/**
	 * server的工作逻辑写在这个方法中 此处不做任何处理, 只让server sleep
	 */
	public void handle() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {
		// 在参数中指定server的地址
		if (args.length == 0) {
			System.err.println("The first argument must be server address");
			System.exit(1);
		}

		AppServer as = new AppServer();
		as.registerZookeeper(args[0]);

		as.handle();
	}
}