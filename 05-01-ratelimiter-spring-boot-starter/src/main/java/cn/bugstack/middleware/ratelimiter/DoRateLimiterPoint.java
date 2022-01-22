package cn.bugstack.middleware.ratelimiter;

import cn.bugstack.middleware.ratelimiter.annotation.DoRateLimiter;
import cn.bugstack.middleware.ratelimiter.value.IValveService;
import cn.bugstack.middleware.ratelimiter.value.impl.RateLimiterValve;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
@Aspect
@Component
public class DoRateLimiterPoint {

    @Pointcut("@annotation(cn.bugstack.middleware.ratelimiter.annotation.DoRateLimiter)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(doRateLimiter)")
    public Object doRouter(ProceedingJoinPoint jp, DoRateLimiter doRateLimiter) throws Throwable {
        IValveService valveService = new RateLimiterValve();
        return valveService.access(jp,getMethod(jp), doRateLimiter, jp.getArgs());
    }

    private Method getMethod(JoinPoint jp) throws Throwable{
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }
}
