package com.pugwoo.practice.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 2016年5月28日 11:10:20
 * @author pugwoo
 *
 * Jedis实例不是线程安全的，所以new Jedis()只能给当前线程用。
 * 但是不断地new Jeis()是不合适的，推荐的做法就是用JedisPool。
 */
public class RedisConnectionManager {

	/**
	 * 单例的JedisPool，实际项目中可以配置在string，也可以是懒加载
	 */
	private static final JedisPool pool;
	static {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(128);
		poolConfig.setMaxIdle(64);
		poolConfig.setMaxWaitMillis(1000L);
		poolConfig.setTestOnBorrow(false);
		poolConfig.setTestOnReturn(false);
		
		if(Config.pwd == null || Config.pwd.trim().isEmpty()) {
			// 0是connectionTimeout，还可以指定用0~15哪个redis库
			pool = new JedisPool(poolConfig, Config.host, Config.port, 0);
		} else {
			// 0是connectionTimeout，还可以指定用0~15哪个redis库
			pool = new JedisPool(poolConfig, Config.host, Config.port, 0, Config.pwd);
		}
	}
	
	/**
	 * 拿Jedis连接，用完Jedis之后【必须】close jedis
	 */
	public static Jedis getJedisConnection() {
		return pool.getResource();
	}
	
}
