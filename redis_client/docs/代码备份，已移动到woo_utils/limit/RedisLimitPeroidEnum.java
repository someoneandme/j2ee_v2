package com.pugwoo.redis.limit;

/**
 * @author nick
 */
public enum RedisLimitPeroidEnum {

	LIMIT_PEROID_MINUTE("LIMIT_PEROID_MINUTE", "每分钟"),
	LIMIT_PEROID_HOUR("LIMIT_PEROID_HOUR", "每小时"),
	LIMIT_PEROID_DAY("LIMIT_PEROID_DAY", "每自然日"),
	LIMIT_PEROID_WEEK_START_SUNDAY("LIMIT_PEROID_WEEK_START_SUNDAY", "每周(从周日开始)"),
	LIMIT_PEROID_WEEK_START_MONDAY("LIMIT_PEROID_WEEK", "每周(从周一开始)"),
	LIMIT_PEROID_MONTH("LIMIT_PEROID_MONTH", "每自然月"),
	LIMIT_PEROID_YEAR("LIMIT_PEROID_YEAR", "每年"),
	LIMIT_PEROID_PERMANENT("LIMIT_PEROID_PERMANENT", "永久");
	
	private String code;
	
	private String name;
	
	private RedisLimitPeroidEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static RedisLimitPeroidEnum getByCode(String code) {
		for(RedisLimitPeroidEnum e : RedisLimitPeroidEnum.values()) {
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
	
}
