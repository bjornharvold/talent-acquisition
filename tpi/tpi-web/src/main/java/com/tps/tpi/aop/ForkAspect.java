/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold
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
 * Date: Aug 26, 2009
 * Time: 4:48:54 PM
 * Responsibility:
 */
@Aspect
@Component
public class ForkAspect {
    private static final Logger log = LoggerFactory.getLogger(ForkAspect.class);

    @Pointcut(value = "execution(* org.springframework.mail.javamail.JavaMailSenderImpl.send(..))")
    public void fork() {}

    @Around(value = "fork()")
    public void runFork(final ProceedingJoinPoint pjp) {
        new Thread(new Runnable() {
            public void run() {
                log.info("Forking method execution: " + pjp);
                try {
                    pjp.proceed();
                } catch (Throwable t) {
                    // All we can do is log the error.  
                    log.error(t.getMessage(), t);
                }
            }
        }).start();
    }
}
