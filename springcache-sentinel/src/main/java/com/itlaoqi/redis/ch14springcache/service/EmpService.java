package com.itlaoqi.redis.ch14springcache.service;

import com.itlaoqi.redis.ch14springcache.entity.Emp;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmpService {
    //对于默认情况下, Redis对象的序列化使用的是JDK序列化.必须要求实体类实现Serili..接口
    //Cacheable会将方法的返回值序列化后存储到redis中,key就是参数执行的字符串
    //Cacheable的用途就是在执行方法前检查对应的key是否存在,存在则直接从redis中取出不执行方法中的代码
    //没有对应的key则执行方法代码,并将返回值序列化保存到redis中
    //condition代表条件成立的时候才执行缓存的数据 , 反之有一个unless ,代表条件不成立的时候才获取缓存
    @Cacheable(value = "emp" , key = "#empId" , condition = "#empId != 1000")
    public Emp findById(Integer empId) {
        System.out.println("执行了FindById方法:EmpId:" + empId);
        return new Emp(empId , "itlaoqi"  , new Date()  , 1000f ,"RESEARCH");
    }
    //冒号分割
    @Cacheable(value = "emp:rank:salary")
    public List<Emp> getEmpRank() {
        List list = new ArrayList();
        for(int i = 0 ; i < 10 ; i++) {
            list.add(new Emp(i , "emp" +  i , new Date() , 5000 + i * 100f , "RESEARCH"));
        }
        return list;
    }

    //@CachePut 作用是不管redis是否存在key, 都对数据进行更新
    @CachePut(value="emp" , key = "#emp.empno")
    public Emp create(Emp emp){
        System.out.println("正在创建" + emp.getEmpno() + "员工信息");
        return emp;
    }

    //@CachePut 作用是不管redis是否存在key, 都对数据进行更新
    //Update也是用CachePut
    @CachePut(value="emp" , key = "#emp.empno")
    public Emp update(Emp emp){
        System.out.println("正在更新" + emp.getEmpno() + "员工信息");
        return emp;
    }

    @CacheEvict(value="emp" , key = "#empno")
    public void delete(Integer empno){
        System.out.println("删除" + empno + "员工信息");
    }

}
