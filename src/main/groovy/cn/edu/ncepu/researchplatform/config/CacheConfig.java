package cn.edu.ncepu.researchplatform.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.isAllowNullValues();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(10, TimeUnit.SECONDS));
        return cacheManager;
    }

    @Bean
    public CacheManager rankCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(4 * 200)
                .expireAfterWrite(1, TimeUnit.DAYS));
        cacheManager.isAllowNullValues();
        return cacheManager;
    }

    @Bean
    public CacheManager captchaCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(4 * 200)
                .expireAfterWrite(3, TimeUnit.MINUTES));
        cacheManager.isAllowNullValues();
        return cacheManager;
    }
@Bean
    public Cache captcha() {
        return captchaCacheManager().getCache("captcha");
    }
}
