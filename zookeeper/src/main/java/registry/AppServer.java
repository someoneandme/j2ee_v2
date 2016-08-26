package registry;

import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import util.ZookeeperConf;

/**
 * http://coolxing.iteye.com/blog/1871520 
 * ## 使用zookeeper作为服务注册中心
 * 
 * 利用zookeeper的EPHEMERAL_SEQUENTIAL特性: 节点是自增id增加的，当客户端断线后，会自动删除。
 * 
 * 【重要】
 * 需要确认zookeeper中是否已经存在"/sgroup"节点了, 
 * 如果不存在, 则创建该节点. 
 * 如果存在, 最好先将其删除, 然后再重新创建.
 * 【重要】先执行命令：create /sgroup test
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
		ZooKeeper zk = ZookeeperConf.getZooKeeper();
		
		// 在"/sgroup"下创建子节点
		// 子节点的类型设置为EPHEMERAL_SEQUENTIAL, 表明这是一个临时节点, 且在子节点的名称后面加上一串数字后缀
		// 将server的地址数据关联到新创建的子节点上
		String createdPath = zk.create("/" + groupNode + "/" + subNode,
				address.getBytes("utf-8"), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println("create: " + createdPath);
	}

	public static void main(String[] args) throws Exception {		
		String ip = "127.0.0.1:" + new Random().nextInt(65500);

		AppServer as = new AppServer();
		as.registerZookeeper(ip);

		Thread.sleep(Long.MAX_VALUE);
	}
}