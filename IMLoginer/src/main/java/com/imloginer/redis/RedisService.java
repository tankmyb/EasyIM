package com.imloginer.redis;

import redis.clients.jedis.Jedis;

public class RedisService {

	public void addLoginInfo(String no,String address){
		final Jedis jedis = JedisPoolUtils.getJedis();
		jedis.set(no, address);
		JedisPoolUtils.returnRes(jedis);
	}
	public String getAddress(String no){
		final Jedis jedis = JedisPoolUtils.getJedis();
		String address = jedis.get(no);
		JedisPoolUtils.returnRes(jedis);
		return address;
	}
}
