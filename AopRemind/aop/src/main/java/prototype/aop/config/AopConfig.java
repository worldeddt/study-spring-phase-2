package prototype.aop.config;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AopConfig {

    @Around("execution(* prototype.aop.controller.*.*(..))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("controller aop start");
        joinPoint.proceed();
        log.info("controller aop end");
    }
}
