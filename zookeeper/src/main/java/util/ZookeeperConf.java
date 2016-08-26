package util;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.alibaba.fastjson.JSON;

public class ZookeeperConf {

	private static final String zookeeperAddr = "127.0.0.1:2181";

	private static final int sessionTimeout = 10000;

	public static ZooKeeper getZooKeeper() {
		return getZooKeeper(null);
	}
	
	/**
	 * 获得zookeeper
	 * @param watcher
	 * @return
	 */
	public static ZooKeeper getZooKeeper(Watcher watcher) {
		try {
			if (watcher == null) {
				return new ZooKeeper(zookeeperAddr, sessionTimeout, new Watcher() {
					@Override
					public void process(WatchedEvent e) {
						System.out.println("zookeeper default event:" + JSON.toJSONString(e));
					}
				});
			} else {
				return new ZooKeeper(zookeeperAddr, sessionTimeout, watcher);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
