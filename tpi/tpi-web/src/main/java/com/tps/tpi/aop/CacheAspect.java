package com.tps.tpi.aop;

import com.tps.tpi.cache.CacheManager;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Bjorn Harvold
 * Date: Dec 18, 2009
 * Time: 10:22:29 PM
 * Responsibility: This aspect will handle caching all dtos that are generated from Dozer. When it
 * comes time to return those dtos to the user, they will be returned from cache instead of the db
 */
@Aspect
@Component
public class CacheAspect {
    private final CacheManager cacheManager;

    @Autowired
    public CacheAspect(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /*
    @Pointcut(value = "execution(@com.tps.tpi.cache.Cacheable * org.dozer.Mapper.map*(..))")
    public void cache(AbstractEntity entity, Class dto) {
    }

    @Around(value = "cache(entity, dto)")
    public AbstractDto doCache(AbstractEntity entity, final ProceedingJoinPoint pjp) {
        if (entity == null) {
            pjp.proceed();
        }
    }
    */
}
