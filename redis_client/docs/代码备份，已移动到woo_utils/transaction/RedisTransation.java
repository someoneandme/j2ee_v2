package com.pugwoo.redis.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pugwoo.practice.jedis.RedisConnectionManager;

import redis.clients.jedis.Jedis;

/**
 * @author nick
 * redis事务的一致性保证
 */
public class RedisTransation {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisTransation.class);
	
	private static String getKey(RedisTransNamespaceEnum namespace, String key) {
		return namespace.getCode() + "_" + key;
	}

	/**
	 * 获得一个名称为key的锁，redis保证同一时刻只有一个client可以获得锁。
	 * 
	 * @param namespace 命名空间，每个应用独立的空间
	 * @param key 业务key，redis将保证同一个namespace同一个key只有一个client可以拿到锁
	 * @param maxTransactionSeconds 单位秒，必须>0,拿到锁之后,预计多久可以完成这个事务，如果超过这个时间还没有归还锁，那么事务将失败
	 * @return
	 */
	public static boolean requireLock(RedisTransNamespaceEnum namespace,
			String key, int maxTransactionSeconds) {
		if(namespace == null || key == null || key.isEmpty() || maxTransactionSeconds <= 0) {
			LOGGER.error("requireLock with error params: namespace:{},key:{},maxTransactionSeconds:{}",
					namespace, key, maxTransactionSeconds, new Exception());
			return false;
		}
		
		Jedis jedis = null;
		try {
			jedis = RedisConnectionManager.getJedisConnection();
			key = getKey(namespace, key);
			String result = jedis.set(key, "1", "NX", "PX", maxTransactionSeconds * 1000);
			if(result == null) {
				return false;
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("requireLock error, namespace:{}, key:{}", namespace, key, e);
			return false;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	/**
	 * 如果事务已经完成，则归还锁
	 * @param namespace
	 * @param key
	 */
	public static void releaseLock(RedisTransNamespaceEnum namespace,
			String key) {
		if(namespace == null || key == null || key.isEmpty()) {
			LOGGER.error("requireLock with error params: namespace:{},key:{}",
					namespace, key, new Exception());
			return;
		}
		
		Jedis jedis = null;
		try {
			jedis = RedisConnectionManager.getJedisConnection();
			key = getKey(namespace, key);
			jedis.del(key);
		} catch (Exception e) {
			LOGGER.error("requireLock error, namespace:{}, key:{}", namespace, key, e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
}
