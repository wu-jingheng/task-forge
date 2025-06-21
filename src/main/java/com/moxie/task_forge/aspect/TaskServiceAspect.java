package com.moxie.task_forge.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class TaskServiceAspect {

    private final Logger log = LoggerFactory.getLogger(TaskServiceAspect.class);

    @Pointcut("execution(* com.moxie.task_forge.service.TaskService.*(..))")
    public void taskServiceMethods() {}

    @Before("taskServiceMethods()")
    public void beforeExecution(JoinPoint joinPoint) {
        log.info("[AOP] Before: {}.{} - Args: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @After("taskServiceMethods()")
    public void afterExecution(JoinPoint joinPoint) {
        log.info("[AOP] After: {}.{} - Args: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }
}
