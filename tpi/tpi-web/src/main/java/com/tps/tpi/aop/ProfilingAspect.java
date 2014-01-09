/*
 * Copyright (c) 2009. This beautifully written piece of code has been created by Bjorn Harvold. Please do not use my code without explicit permission or I just might have to come by your office and ruin your day.
 */

package com.tps.tpi.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProfilingAspect {
    private static final Logger log = LoggerFactory.getLogger(ProfilingAspect.class);

    @Pointcut(value = "SystemArchitecture.inAnnotationType()")
    public void timer() {
    }

    @Around(value = "timer()")
    public Object doTimer(final ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        long start = System.nanoTime();

        try {
            result = pjp.proceed();
        } finally {
            long complete = System.nanoTime();

            if (log.isDebugEnabled()) {
                log.debug("Method: " + pjp.getSignature().toShortString() + " lasted " + (complete-start) + " nanoseconds");
            }
        }

        return result;
    }

}