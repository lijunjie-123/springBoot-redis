package com.itlaoqi.redis.ch14springcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@SpringBootApplication
@EnableCaching //启用SpringCache缓存 , SpringCache会根据底层引用的Jar包决定缓存的类型
public class Ch14SpringcacheApplication {

	@Bean
	public RedisCacheConfiguration redisCacheConfiguration(){
		//加载redis缓存的默认配置
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
		configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		return configuration;
	}

	public static void main(String[] args) {
		SpringApplication.run(Ch14SpringcacheApplication.class, args);
	}
}
