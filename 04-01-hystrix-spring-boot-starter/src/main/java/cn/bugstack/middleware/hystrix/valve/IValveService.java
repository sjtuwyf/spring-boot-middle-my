package cn.bugstack.middleware.hystrix.valve;

import cn.bugstack.middleware.hystrix.annotation.DoHystrix;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
public interface IValveService {

    Object access(ProceedingJoinPoint jp, Method method, DoHystrix doHystrix,Object[] args) throws Throwable;

}
