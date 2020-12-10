package com.itlaoqi.redis.sentinel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FailOverSample {
    public static void main(String[] args) {
        //JedisSentinelPool Jedis的Sentinel连接池
        String nodename = "mymaster";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("192.168.132.128:26379");
        sentinels.add("192.168.132.128:26380");
        sentinels.add("192.168.132.128:26380");
        JedisSentinelPool sentinelPool = new JedisSentinelPool(nodename , sentinels);
        /*Jedis jedis = sentinelPool.getResource();//getResource用于从连接池里面获取jedis对象
        jedis.select(2);
        jedis.set("Test" , "abc");
        jedis.close();*/
        int count = 0;
        while (true) {
            Jedis jedis = null;
            try {
                jedis = sentinelPool.getResource();
                int idx = new Random().nextInt(100000);

                jedis.set("k-" + idx, "v-" + idx);
                if (count % 100 == 0) {
                    System.out.println("k-" + idx);
                }
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (jedis != null) {
                    jedis.close();
                }
            }


            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
