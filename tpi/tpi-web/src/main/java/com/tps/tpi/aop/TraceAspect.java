/*
 * Copyright (c) 2009. This beautifully written piece of code has been created by Bjorn Harvold. Please do not use my code without explicit permission or I just might have to come by your office and ruin your day.
 */

package com.tps.tpi.aop;

import org.apache.log4j.NDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Dec 18, 2009
 * Time: 10:22:29 PM
 * Responsibility: This is a super detailed trace aspect that will log every method call
 */
@Aspect
@Component
public class TraceAspect {
    private static final Logger log = LoggerFactory.getLogger(TraceAspect.class);

    @Pointcut(value = "SystemArchitecture.inAnnotationType()")
    public void traced() {
    }

    @Before(value = "traced()")
    public void traceBefore(JoinPoint jp) throws Throwable {
        if (log.isTraceEnabled()) {
            log.trace("Calling method: " + constructTraceStatement(jp));
        }
    }

    /*
    @After(value = "traced()")
    public void traceAfter(JoinPoint jp) throws Throwable {
        if (log.isTraceEnabled()) {
            log.trace("Exiting method: " + constructTraceStatement(jp) );
        }
    }
    */

    @AfterReturning(value = "traced()", returning = "result")
    public void traceAfterReturning(JoinPoint jp, Object result) {
        if (log.isTraceEnabled()) {
            log.trace("Method: " + constructTraceStatement(jp) + "\nReturned: " + constructReturnStatement(result));
        }
    }

    private String constructReturnStatement(Object result) {
        StringBuilder sb = new StringBuilder();

        if (result != null) {
            if (result instanceof List) {
                List list = (List) result;
                sb.append("List size: ");
                sb.append(list.size());
                sb.append(" {\n");

                for (Object o : list) {
                    sb.append(o);
                    sb.append("\n");
                }
                sb.append("}");
            } else if (result instanceof Map) {
                Map map = (Map) result;
                sb.append("Map size: ");
                sb.append(map.size());
                sb.append(" {\n");

                for (Object o : map.keySet()) {
                    sb.append("Key: ");
                    sb.append(o);
                    sb.append(" - Value: ");
                    sb.append(map.get(o));
                }

                sb.append("}");
            } else {
                sb.append(result);
            }
        } else {
            sb.append("null");
        }

        return sb.toString();
    }

    private String constructTraceStatement(JoinPoint jp) {
        JoinPoint.StaticPart staticJp = jp.getStaticPart();
        MethodSignature signature = (MethodSignature) staticJp.getSignature();

        StringBuilder sb = new StringBuilder("[");
        sb.append(signature.getDeclaringType().getSimpleName());
        sb.append(".");
        sb.append(signature.getName());
        sb.append("(");
        sb.append(getParameters(jp.getArgs(), signature));
        sb.append("):");
        sb.append(signature.getReturnType().getSimpleName());
        sb.append("] starting at line: ");
        sb.append(staticJp.getSourceLocation().getLine());

        return sb.toString();
    }

    private String getParameters(Object[] args, MethodSignature signature) {
        StringBuilder sb = new StringBuilder();
        if (args != null) {

            for (int i = 0; i < args.length; i++) {
                String name = signature.getParameterNames()[i];
                sb.append(name);
                sb.append(":");
                sb.append(args[i]);

                if (i + 1 < signature.getParameterNames().length) {
                    sb.append(", ");
                }
            }
        }

        return sb.toString();
    }
}