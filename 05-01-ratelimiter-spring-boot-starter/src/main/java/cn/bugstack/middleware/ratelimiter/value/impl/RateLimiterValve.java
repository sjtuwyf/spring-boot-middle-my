package cn.bugstack.middleware.ratelimiter.value.impl;

import cn.bugstack.middleware.ratelimiter.Constants;
import cn.bugstack.middleware.ratelimiter.annotation.DoRateLimiter;
import cn.bugstack.middleware.ratelimiter.value.IValveService;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
public class RateLimiterValve implements IValveService {
    @Override
    public Object access(ProceedingJoinPoint jp, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {
        if (0 == doRateLimiter.permitPerSecond()) {
            return jp.proceed();
        }
        String clazzName = jp.getTarget().getClass().getName();
        String methodName = method.getName();

        String key = clazzName + methodName;

        if (null== Constants.rateLimiterMap.get(key)) {
            Constants.rateLimiterMap.put(key, RateLimiter.create(doRateLimiter.permitPerSecond()));
        }
        RateLimiter rateLimiter = Constants.rateLimiterMap.get(key);
        if (rateLimiter.tryAcquire()) {
            return jp.proceed();
        }
        return JSON.parseObject(doRateLimiter.returnJson(),method.getReturnType());
    }
}
