package com.pugwoo.practice.jedis.pubsub;

import com.pugwoo.practice.jedis.RedisConnectionManager;

import redis.clients.jedis.Jedis;

/**
 * 多个订阅者时，每条消息每个订阅者都收得到。但要求订阅者和发送者同时在线。
 * 
 * redis的原生命令支持*号模糊订阅一组CHANNEL：
 * http://redis.io/topics/pubsub
 * 
 * jedis支持一个Listener订阅多个CHANNEL
 */
public class TestSubscribeClient {

	public static void main(String[] args) {
		Jedis jedis = RedisConnectionManager.getJedisConnection();
		
		jedis.subscribe(new MyMessageListener(), CHANNEL.HELLO);
	}
	
}
