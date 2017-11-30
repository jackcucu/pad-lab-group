package md.jack.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport
{
    @Bean
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory cf)
    {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(cf);

        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(final RedisTemplate redisTemplate)
    {
        final RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        cacheManager.setDefaultExpiration(300);

        return cacheManager;
    }
}
