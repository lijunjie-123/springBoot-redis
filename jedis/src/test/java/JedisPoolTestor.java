import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 连接池测试类
 */
public class JedisPoolTestor {
    @Test
    public void testJedisPool(){
        GenericObjectPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100); //设置连接池中最多允许放100个Jedis对象
        //我的建议maxidle与maxTotal一样
        config.setMaxIdle(100);//设置连接池中最大允许的空闲连接
        config.setMinIdle(10);//设置连接池中最小允许的连接数
        config.setTestOnBorrow(false); //借出连接的时候是否测试有效性,推荐false
        config.setTestOnReturn(false); //归还时是否测试,推荐false
        config.setTestOnCreate(true); //创建时是否测试有效
        config.setBlockWhenExhausted(true);//当连接池内jedis无可用资源时,是否等待资源 ,true
        config.setMaxWaitMillis(1000); //没有获取资源时最长等待1秒,1秒后还没有的话就报错
        //创建jedis,这句话运行后就自动根据上面的配置来初始化jedis资源了
        JedisPool pool = new JedisPool(config , "192.168.132.128" , 6666);
        Jedis jedis = null;
        try {
            jedis = pool.getResource(); //从连接池中"借出(borrow)"一个jedis对象
            jedis.auth("123456");
            jedis.set("abc" , "bbb");
            System.out.println(jedis.get("abc"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                jedis.close();//在使用连接池的时候,close()方法不再是关闭,而是归还(return)
            }
        }


    }
}
