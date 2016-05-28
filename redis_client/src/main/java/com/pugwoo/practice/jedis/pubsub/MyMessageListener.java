package com.pugwoo.practice.jedis.pubsub;

import redis.clients.jedis.BinaryJedisPubSub;

/**
 * 监听
 */
public class MyMessageListener extends BinaryJedisPubSub {

	@Override
	public void onMessage(byte[] channel, byte[] message) {
		System.out.println(
				"  <<< 订阅(SUBSCRIBE)< Channel:" + new String(channel) 
				+ " >接收到的Message:" + new String(message));

		// if (message.equalsIgnoreCase("quit")) {
		// this.unsubscribe(channel); // 主动不订阅，但这种方式用得很少
		// }
	}

}
