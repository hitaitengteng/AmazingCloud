package com.whut.preprocessor.config;


import redis.clients.jedis.Jedis;

public class RedisConfig {

    //主库,用于写或者读
    public static Jedis masterRedis = new Jedis("59.69.101.206",46079);
//    //从库,只用于读数据
//    public static Jedis slaveRedis = new Jedis("59.69.101.206",46080);

}