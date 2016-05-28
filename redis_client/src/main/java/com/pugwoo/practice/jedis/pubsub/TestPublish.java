package com.pugwoo.practice.jedis.pubsub;

import java.util.Date;

import com.pugwoo.practice.jedis.RedisConnectionManager;

import redis.clients.jedis.Jedis;

/**
 * 测试发布主题:
 * 
 * 1. 发布者和接收者的共同主题是String channel，自定义名称
 * 
 * 缺点：
 * 1. 发送者和接收者必须同时在线，否则消息传递不到。也不会保存。
 * 2. 多个订阅者订阅同一个CHANNEL时，每个订阅者都可以收到所有的消息。没有办法做订阅者分组。
 * 
 * 基于这个缺点，redis的PubSub适合的场景：
 * 1) 对保存的数据可靠性要求不高，可接受数据丢失
 * 
 * 例如：聊天室，在线广播
 */
public class TestPublish {

	public static void main(String[] args) {
		
		Jedis jedis = RedisConnectionManager.getJedisConnection();
		
		/**
		 * 不停地发布消息
		 */
		while (true) {
			// 发布内容可以是string也可以是二进制，既然如此，那就推荐用二进制更通用吧
			String date = new Date().toString();
			jedis.publish(CHANNEL.HELLO, date.getBytes());
			System.out.println("publish data:" + date);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			// 注：这个CHANNEL没有在Redis Desktop Manager中显示
		}
		
	}
	
}
