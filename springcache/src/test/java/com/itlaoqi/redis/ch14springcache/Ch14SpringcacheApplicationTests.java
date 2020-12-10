package com.itlaoqi.redis.ch14springcache;

import com.itlaoqi.redis.ch14springcache.entity.Emp;
import com.itlaoqi.redis.ch14springcache.service.EmpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest //SpringBootTest的作用就是在Junit启动的时候自动初始化SpringBoot的IOC容器
public class Ch14SpringcacheApplicationTests {

	@Resource
	private EmpService empService ;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testFindById(){
		empService.findById(1000);
		empService.findById(1000);
		empService.findById(1000);
		empService.findById(1000);
		empService.findById(1000);
		Emp emp = empService.findById(1000);
        System.out.println(emp.getName());
	}

    @Test
    public void testEmpRank() {
        List<Emp> list = empService.getEmpRank();
        System.out.println(list.get(list.size() - 1).getName());
	}
	@Test
    public void testCreate(){
	    empService.create(new Emp(1002 , "emp" + new Date().getTime() , new Date() , 1234f , "MARKET"));
    }

    @Test
    public void testUpdate(){
        empService.update(new Emp(1002 , "u-emp" + new Date().getTime() , new Date() , 1234f , "MARKET"));
    }
    @Test
    public void testDelete(){
	    empService.delete(1002);
    }
}
