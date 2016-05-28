package com.pugwoo.practice.jedis;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 2014年5月21日 20:47:39
 * http://www.blogways.net/blog/2013/06/02/jedis-demo.html
 */
public class BasicUse {

	/**
	 * 基本的get set del
	 */
	@Test
	public void testBasic() {
		long start = System.currentTimeMillis();
		Jedis jedis = RedisConnectionManager.getJedisConnection();
		long end = System.currentTimeMillis();
		System.out.println("get connection cost:" + (end - start) + "ms");
		
		start = System.currentTimeMillis();
		jedis.set("name", "nick");
		System.out.println("name:" + jedis.get("name"));

		jedis.del("name");
		System.out.println("name:" + jedis.get("name"));
		
		jedis.close();
		end = System.currentTimeMillis();
		System.out.println("add/del cost:" + (end - start) + "ms");
	}
	
	/**
	 * 事务，原子性执行
	 * 
	 * Redis中定义的事务(http://redis.io/topics/transactions)，
	 * 并不是关系数据库中严格意义上的事务。
	 * 当Redis事务中的某个操作执行失败，或者用DISCARD取消事务时候，Redis并不执行“事务回滚”
	 */
	@Test
	public void testTx() {
		Jedis jedis = RedisConnectionManager.getJedisConnection();
		Transaction tx = jedis.multi();
		
		// 原子性执行下面内容
		tx.set("name", "nick");
		tx.set("age", String.valueOf(27));
		
		List<Object> results = tx.exec();
		for(Object obj : results) {
			System.out.println(obj);
		}
		
		// tx.discard(); 【注意】如果发现某个命令执行失败，需要手动discard回滚
		
		jedis.close();
	}

}
