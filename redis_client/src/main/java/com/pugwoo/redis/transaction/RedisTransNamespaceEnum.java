package com.pugwoo.redis.transaction;

/**
 * 
 * @author nick
 * 
 * 规范：所有的key都以TX开头
 */
public enum RedisTransNamespaceEnum {

	TX_MAKER_ORDER("TX_MAKER_ORDER", "订单处理namespace"),
	TX_MAKER_WITHDRAW("TX_MAKER_WITHDRAW", "提现namespace");
	
	private String code;
	
	private String name;
	
	private RedisTransNamespaceEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static RedisTransNamespaceEnum getByCode(String code) {
		for(RedisTransNamespaceEnum e : RedisTransNamespaceEnum.values()) {
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
