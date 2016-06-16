package com.pugwoo.redis.limit;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pugwoo.practice.jedis.RedisConnectionManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * 使用redis控制全局的操作次数限制，命名空间统一管理在RedisLimitNamespaceEnum
 * 
 * 【重要】注：RedisLimit并不保证全局的串行，因为redis和mysql的事务没有办法很好地合在一起，所以不搞太复杂。
 * 高并发的串行保证请使用RedisTransation
 * 
 * 注：redis的incr或decr在redis删除某个expire key时，会出现多个线程拿到相同值的情况，所以incr或decr来做这个功能并不可靠
 * 
 * @author nick
 */
public class RedisLimit {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisLimit.class);

	private static String getKey(RedisLimitNamespaceEnum namespace, String key) {
		return namespace.getCode() + "_" + key;
	}
	
	/**
	 * 查询key的redis限制剩余次数
	 * @param limitEnum
	 * @param key
	 * @return -1是系统异常
	 */
	public static long getLimitCount(RedisLimitNamespaceEnum limitEnum, String key) {
		if(limitEnum == null || key == null) {
			LOGGER.error("limitEnum or key is null, limitEnum:{}, key:{}", limitEnum, key);
			return 0;
		}
		
		Jedis jedis = null;
		try {
			jedis = RedisConnectionManager.getJedisConnection();
			key = getKey(limitEnum, key);
			String count = jedis.get(key);
			if(count == null) {
				return limitEnum.getLimitCount();
			} else {
				long limitCount = new Integer(count);
				if(limitCount > limitEnum.getLimitCount()) {
					limitCount = limitEnum.getLimitCount();
				}
				return limitCount;
			}
		} catch (Exception e) {
			LOGGER.error("getLimitCount error, namespace:{}, key:{}", limitEnum, key, e);
			return -1;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	/**
	 * 判断是否还有限制次数
	 * @param limitEnum
	 * @param key
	 * @return
	 */
	public static boolean hasLimitCount(RedisLimitNamespaceEnum limitEnum, String key) {
		return getLimitCount(limitEnum, key) > 0;
	}
	
	/**
	 * 使用了一次限制。一般来说，业务都是在处理成功后才扣减使用是否成功的限制，
	 * 如果使用失败了，如果业务支持事务回滚，那么可以回滚掉，此时可以不用RedisTransation做全局限制。
	 * 
	 * @param limitEnum
	 * @param key
	 * @return 返回是当前周期内第几个使用配额的，如果返回-1，表示使用配额失败
	 */
	public static long useLimitCount(RedisLimitNamespaceEnum limitEnum, String key) {
		if(limitEnum == null || key == null) {
			LOGGER.error("limitEnum or key is null, limitEnum:{}, key:{}", limitEnum, key);
			return -1;
		}
		
		Jedis jedis = null;
		try {
			jedis = RedisConnectionManager.getJedisConnection();
			key = getKey(limitEnum, key);
					
			for(int i = 0; i < 1000; i++) { // 重试次数，限制重试次数上限
				String oldValue = jedis.get(key);
				long newValue = 0;
				if(oldValue == null) {
					newValue = limitEnum.getLimitCount() - 1;
				} else {
					long old = new Integer(oldValue);
					if(old > limitEnum.getLimitCount()) {
						old = limitEnum.getLimitCount();
					}
					newValue = old - 1;
				}
				if(newValue < 0) { // 已经没有使用限额
					return -1;
				}
				
				long expireSeconds = jedis.ttl(key);
				long restSeconds = getRestSeconds(limitEnum.getPeroid());
				Integer setRest = null;
				// 为了避免跨周期设置问题，只能将ttl的值变小，不能变大； -1和-2（key不存在）时可以设置
				if(expireSeconds < 0 || expireSeconds >= 0 && restSeconds <= expireSeconds) {
					setRest = (int) restSeconds;
				}
				
				if(compareAndSet(jedis, key, newValue + "", oldValue, setRest)) {
					return limitEnum.getLimitCount() - newValue;
				} else {
					continue;
				}
			}

			LOGGER.error("useLimitCount after 1000 times try fail, namespace:{}, key:{}", limitEnum, key);
			return -1; // 最终失败
		} catch (Exception e) {
			LOGGER.error("getLimitCount error, namespace:{}, key:{}", limitEnum, key, e);
			return -1;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	/**
	 * CAS，成功返回true，失败返回false
	 * @param expireSeconds 超时时间，如果是null，则不设置
	 */
	private static boolean compareAndSet(Jedis jedis, String key, String value, String oldValue,
			Integer expireSeconds) {
		try {
			jedis.watch(key);
			String readOldValue = jedis.get(key);
			if(Objects.equals(readOldValue, oldValue)) {
				Transaction tx = jedis.multi();
				Response<String> result = null;
				if(expireSeconds != null) {
					result = tx.setex(key, expireSeconds, value);
				} else {
					result = tx.set(key, value);
				}

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
			LOGGER.error("compareAndSet error,key:{}, value:{}, oldValue:{}", key, value, oldValue);
			return false;
		}
	}
	
	/**
	 * 获得到周期剩余的时间
	 * @param peroidEnum
	 * @return 默认就是永久 -1
	 */
	private static long getRestSeconds(RedisLimitPeroidEnum peroidEnum) {
		if(peroidEnum == null) {
			return -1;
		}
		if(peroidEnum == RedisLimitPeroidEnum.LIMIT_PEROID_MINUTE) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 1);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
		}
		if(peroidEnum == RedisLimitPeroidEnum.LIMIT_PEROID_HOUR) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, 1);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
		}
		if(peroidEnum == RedisLimitPeroidEnum.LIMIT_PEROID_DAY) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
		}
		if(peroidEnum == RedisLimitPeroidEnum.LIMIT_PEROID_WEEK_START_SUNDAY) {
			return secondsToNextWeek(Calendar.SUNDAY);
		}
		if(peroidEnum == RedisLimitPeroidEnum.LIMIT_PEROID_WEEK_START_MONDAY) {
			return secondsToNextWeek(Calendar.MONDAY);
		}
		if(peroidEnum == RedisLimitPeroidEnum.LIMIT_PEROID_MONTH) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
		}
		if(peroidEnum == RedisLimitPeroidEnum.LIMIT_PEROID_YEAR) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
		}
		if(peroidEnum == RedisLimitPeroidEnum.LIMIT_PEROID_PERMANENT) {
			return -1;
		}
		
		return -1;
	}
	
	/**
	 * 传入的是Calendar.SUNDAY  Calendar.MONDAY
	 * @param i
	 * @return
	 */
	private static long secondsToNextWeek(int i) {
		Calendar cal = Calendar.getInstance();
		do {
			cal.add(Calendar.DATE, 1);
		} while(cal.get(Calendar.DAY_OF_WEEK) != i);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
	}
		
	public static void main(String[] args) {
		final Vector<Long> vector = new Vector<Long>();
		for(int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					long count = 0;
					do {
						count = useLimitCount(RedisLimitNamespaceEnum.LIMIT_MAKER_WITHDRAW, 13 + "");
						if(count > 0) {
							System.out.println(count + ":" + new Date());
						}
						vector.add(1L);
					} while(true);
				}
			});
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("final:" + vector.size());
	}
	
}
