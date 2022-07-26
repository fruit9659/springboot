package cn.qifeng.merchant.config;

import cn.qifeng.common.cache.Cache;
import cn.qifeng.merchant.common.util.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
public class RedisConfig {

    @Bean
    public Cache cache(StringRedisTemplate stringRedisTemplate){
        return new RedisCache(stringRedisTemplate);
    }
}
