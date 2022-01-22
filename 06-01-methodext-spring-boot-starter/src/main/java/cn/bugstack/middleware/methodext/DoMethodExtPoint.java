package cn.bugstack.middleware.methodext;

import cn.bugstack.middleware.methodext.annotation.DoMethodExt;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
@Aspect
@Component
public class DoMethodExtPoint {

    private Logger logger = LoggerFactory.getLogger(DoMethodExtPoint.class);

    @Pointcut("@annotation(cn.bugstack.middleware.methodext.annotation.DoMethodExt)")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        // 获取内容
        Method method = getMethod(jp);
        DoMethodExt doMethodExt = method.getAnnotation(DoMethodExt.class);

        // 获取拦截方法
        String methodName = doMethodExt.method();
        Method methodExt = getClass(jp).getMethod(methodName, method.getParameterTypes());

        Class<?> returnType = methodExt.getReturnType();

        // 判断返回类型
        if (!returnType.getName().equals("boolean")) {
            throw new RuntimeException("annotation @DoMethodExt set method：" + methodName + " returnType is not boolean");
        }

        boolean invoke = (boolean) methodExt.invoke(jp.getThis(), jp.getArgs());

        return invoke ? jp.proceed() : JSON.parseObject(doMethodExt.returnJson(), method.getReturnType());
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp) throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }

}
