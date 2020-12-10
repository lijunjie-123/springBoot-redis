import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

//启动时初始化Spring IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringJedisTestor {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testSetGet(){
        //opsForValue()是对String字符串操作的类
        //默认情况下,Spring-Data-Redis 会采用JDK 序列化的方式将所有key value进行二进制序列化
        //这会导致redis数据库的值可读性极差,通常我们需要用字符串或JSON的形式来保存对象
        redisTemplate.opsForValue().set("a" , "b1111");
        String b = (String)redisTemplate.opsForValue().get("a");
        System.out.println(b);
    }

    @Test
    public void testObjectSerializer(){
        User u1 = new User("u1" , "p1");
        User u2 = new User("u2" , "p2");
        redisTemplate.opsForValue().set("user:u1" , u1);
        redisTemplate.opsForValue().set("user:u2" , u2);
    }
    @Test
    public void testObjectDeserializer(){
        User u1 = (User)redisTemplate.opsForValue().get("user:u1");
        System.out.println(u1.getUsername());
    }

    @Test
    public void testHashSerializer() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        User u = new User("u3" , "p3");
        //关键是如何将JavaBean转为Map
        Map m1 = BeanUtils.describe(u);
        redisTemplate.opsForHash().putAll("user:m1" , m1);
    }

    @Test
    public void testHashDeserializer() throws InvocationTargetException, IllegalAccessException {
        Map m1 =  (Map)redisTemplate.opsForHash().entries("user:m1");
        User u1 = new User();
        BeanUtils.populate(u1 , m1);
        System.out.println(u1.getUsername());
    }

    @Test
    public void testList(){
        for(int i = 0 ; i < 10 ; i++){
            User u = new User("l" + i , "p" + i);
            redisTemplate.opsForList().rightPush("VipUserRank" , u);
        }

        List users = redisTemplate.opsForList().range("VipUserRank" ,1 , 5);
        System.out.println(users);
    }

    @Test
    //数据库层面的命令要是用execute接口实现
    public void testFlushDB(){
        redisTemplate.execute(new RedisCallback() {
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushDb();
                return null;
            }
        });
    }
}
