package com.pugwoo.practice.jedis;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * 测试redis的乐观锁
 * 
 * 关于redis的事务：http://redis.io/topics/transactions
 * 
 */
public class TestTx {

	/**
	 * 创建时的CAS，这个特性非常好用
	 */
	@Test
	public void testCreateCAS() {
		Jedis jedis = RedisConnectionManager.getJedisConnection();
		
		// NX 当key不存在时才设置，XX当key存在时才设置，这个特性很不错，NX或XX必须设定
		// PX 是超时时间单位为毫秒
		String result = jedis.set("name", "nick", "NX", "PX", 30 * 1000); // 如果成功返回OK，失败返回null
		System.out.println("set result:" + result);
		
		// 还有一个setnx也有这个功能
		
		jedis.close();
	}
	
	/**
	 * 测试原子自增自减
	 */
	@Test
	public void testAtomIncr() {
		Jedis jedis = RedisConnectionManager.getJedisConnection();
		
		jedis.set("money", "111");
		jedis.incr("money"); // money + 1，这个会返回自增完之后的值
		jedis.incrBy("money", 12); // money + 12，这个会返回自增完之后的值
		
		jedis.close();
	}
	
	/**
	 * 事务，原子性执行
	 * 
	 * Redis中定义的事务(http://redis.io/topics/transactions)，
	 * 并不是关系数据库中严格意义上的事务。
	 * 【重要】当Redis事务中的某个操作执行失败，或者用DISCARD取消事务时候，Redis并不执行“事务回滚”
	 */
	@Test
	public void testTx() {
		Jedis jedis = RedisConnectionManager.getJedisConnection();
		// 初始化数据
		jedis.set("money", "100");
		
		jedis.watch("money"); // WATCH   WATCH【必须】要配合MULTI Transaction来做
		
		Long money = 0L;
		String moneyStr = jedis.get("money");
		if(moneyStr != null) {
			money = new Long(moneyStr);
		}
		
		Transaction tx = jedis.multi(); // MULTI
		
		// 测试在这个期间，money值被其它地方修改了
		new Thread(new Runnable() {
			@Override
			public void run() {
				Jedis jedis = RedisConnectionManager.getJedisConnection();
				jedis.set("money", "88"); // 另外一个线程修改了
			}
		}).start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		
		// 如果有足够的钱，把钱减少5块
		Response<String> resultSub = null;
		if(money >= 5) {
			money -= 5;
			resultSub = tx.set("money", money.toString()); // 这里必须用tx，不能在用jedis这个
			System.out.println("change money ok, after:" + money);
			tx.set("payresult", "true"); // 如果money没有修改成功，这里是不会写入到payresult里面的
		} else {
			System.out.println("money is not enough");
		}
		
		List<Object> results = tx.exec();
		if(results == null) {
			// 当监听的key被修改了，执行的results就是null
			System.out.println("results is null");
		} else {
			for(Object obj : results) {
				System.out.println(obj);
			}
			try {
				tx.close();
			} catch (IOException e) {
			}
			System.out.println("resultSub:" + resultSub != null ? resultSub.get() : "-null");
		}
				
		jedis.close();  // 必须
	}
	
	@Test
	public void testCAS() {
		System.out.println(compareAndSet("cas", "hello2", "hello"));
		System.out.println(compareAndSet("cas", "hello", "hello2"));
	}
	
	/**
	 * 封装一个redis CAS
	 * @param key
	 * @param value
	 * @param oldValue 当拿出来的值是null或者是oldValue时，才允许修改
	 * @return
	 */
	private boolean compareAndSet(String key, String value, String oldValue) {
		Jedis jedis = RedisConnectionManager.getJedisConnection();
		
		try {
			jedis.watch(key);
			String readOldValue = jedis.get(key);
			if(readOldValue == null || readOldValue.equals(oldValue)) {
				Transaction tx = jedis.multi(); // MULTI
				Response<String> result = tx.set(key, value);
				List<Object> results = tx.exec();
				if(results == null || result == null || result.get() == null) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			// LOGGER
			return false;
		} finally {
			jedis.close();
		}
	}
}
