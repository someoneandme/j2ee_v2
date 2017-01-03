package com.pugwoo.redis.limit;

/**
 * 
 * @author nick
 * 
 * 规范：所有的key都以LIMIT开头
 */
public enum RedisLimitNamespaceEnum {

	/**创客提现次数，每个自然月2笔*/
	LIMIT_MAKER_WITHDRAW("LIMIT_MAKER_WITHDRAW", "控制提现次数", RedisLimitPeroidEnum.LIMIT_PEROID_MONTH, 2);
	
	private String code;
	
	private String name;
	
	// 限制周期
	// 对于周期的变化，原周期不会变化，必须等待上一个周期结束之后下一个周期才生效；这种情况，建议换个RedisLimitNamespaceEnum搞
	private RedisLimitPeroidEnum peroid;
	
	// 限制次数
	// 对于限制次数的修改，如果提高，那么原来的次数还是那么多；
	// 如果降低，那么已有的所有高于新标准的次数都会被限制到修改后的次数
	private long limitCount; 
	
	private RedisLimitNamespaceEnum(String code, String name, RedisLimitPeroidEnum peroid, long limitCount) {
		this.code = code;
		this.name = name;
		this.peroid = peroid;
		this.limitCount = limitCount;
	}
	
	public static RedisLimitNamespaceEnum getByCode(String code) {
		for(RedisLimitNamespaceEnum e : RedisLimitNamespaceEnum.values()) {
			if(code == null && code == e.getCode() || code.equals(e.getCode())) {
				return e;
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public RedisLimitPeroidEnum getPeroid() {
		return peroid;
	}

	public void setPeroid(RedisLimitPeroidEnum peroid) {
		this.peroid = peroid;
	}

	public long getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(long limitCount) {
		this.limitCount = limitCount;
	}
	
}
