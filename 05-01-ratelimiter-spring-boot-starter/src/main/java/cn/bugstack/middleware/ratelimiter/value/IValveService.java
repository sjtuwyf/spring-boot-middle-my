package cn.bugstack.middleware.ratelimiter.value;

import cn.bugstack.middleware.ratelimiter.annotation.DoRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
public interface IValveService {
    Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter,Object[] args) throws Throwable;
}
