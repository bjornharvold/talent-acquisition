/*
 * Copyright (c) 2009. This beautifully written piece of code has been created by Bjorn Harvold. Please do not use my code without explicit permission or I just might have to come by your office and ruin your day.
 */

package com.tps.tpi.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * User: Bjorn Harvold
 * Date: Dec 27, 2009
 * Time: 12:05:16 PM
 * Responsibility:
 */
@Aspect
public class SystemArchitecture {
    /**
     * Any class within the dao package
     */
    @Pointcut(value = "within(com.tps.tpi.dao..*)")
    public void inDaoType() {
    }

    /**
     * Any class within the service package
     */
    @Pointcut(value = "within(com.tps.tpi.service..*)")
    public void inServiceType() {
    }

    /**
     * Any class within the web package
     */
    @Pointcut(value = "within(com.tps.tpi.web..*)")
    public void inWebType() {
    }

    /**
     * Pointcut for all entities in the system
     */
    @Pointcut(value = "execution(* (@javax.persistence.Entity *).*(..))")
    public void entityOp() {
    }

    /**
     * Pointcut for all repositories in the system
     */
    @Pointcut(value = "execution(* (@org.springframework.stereotype.Repository *).*(..))")
    public void repositoryOp() {
    }

    /**
     * Pointcut for all controllers in the system
     */
    @Pointcut(value = "execution(* (@org.springframework.stereotype.Controller *).*(..))")
    public void controllerOp() {
    }

    /**
     * Pointcut for all services in the system
     */
    @Pointcut(value = "execution(* (@org.springframework.stereotype.Service *).*(..))")
    public void serviceOp() {
    }

    /**
     * Any class within our main packages of dao, service and web
     */
    @Pointcut(value = "inDaoType() || inServiceType() || inWebType()")
    public void inPackageType() {
    }

    /**
     * Any class matching our main annotations such as @Entity, @Repository, @Service and @Controller
     */
    @Pointcut(value = "repositoryOp() || serviceOp() || controllerOp()")
    public void inAnnotationType() {
    }

}
