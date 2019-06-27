package com.whut.dataQuery.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接配置
 * @author 杨赟
 *
 */
public final class RedisConfig {

    private static JedisPool jedisPool = null;

    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(16);
            config.setMaxIdle(16);
            config.setMaxWaitMillis(1000);
            config.setTestOnBorrow(true);
            jedisPool = new JedisPool(config, "59.69.101.206", 46080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}