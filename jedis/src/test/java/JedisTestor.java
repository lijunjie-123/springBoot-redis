import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

public class JedisTestor {
    @Test
    public void testJedis() throws Exception{
        //创建一个Redis通道
        Jedis jedis = new Jedis("192.168.132.128", 6666, 1000);
        try {
            jedis.auth("123456");//设置登录密码
            jedis.select(3);//选择第四个数据库,数据库下标从0开始
            jedis.flushDB();//清空第四个数据库
            jedis.set("hello", "world"); //jedis.xxx方法名字就是命令
            System.out.println( jedis.get("hello"));
            jedis.mset(new String[]{"a", "1", "b", "2", "c" ,"3"});
            List<String> strs =  jedis.mget(new String[]{"a", "c"});
            System.out.println(strs);
            System.out.println(jedis.incr("c"));
            System.out.println(jedis.del("b"));;
        } catch (Exception e) {
            throw e;
        }finally {
            jedis.close();//释放链接
        }
    }

    @Test
    public void testHash() throws Exception {
        Jedis jedis = new Jedis("192.168.132.128", 6666, 1000);
        try {
            jedis.auth("123456");//设置登录密码
            jedis.select(3);//选择第四个数据库,数据库下标从0开始
            jedis.flushDB();//清空第四个数据库
            jedis.hset("user:1:info", "name", "齐毅");
            jedis.hset("user:1:info", "age", "35");
            jedis.hset("user:1:info", "height", "180");
            String name = jedis.hget("user:1:info", "name");
            System.out.println(name);
            Map user1 = jedis.hgetAll("user:1:info");
            System.out.println(user1);
        } catch (Exception e) {
            throw e;
        }finally {
            jedis.close();//释放链接
        }
    }

}
