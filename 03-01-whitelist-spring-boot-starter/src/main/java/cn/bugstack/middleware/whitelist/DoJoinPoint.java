package cn.bugstack.middleware.whitelist;

import cn.bugstack.middleware.whitelist.annotation.DoWhiteList;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
@Aspect
@Slf4j
public class DoJoinPoint {

    @Resource
    private String whiteListConfig;

    @Pointcut("@annotation(cn.bugstack.middleware.whitelist.annotation.DoWhiteList)")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        // 获取内容
        Method method = getMethod(jp);
        DoWhiteList whiteList = method.getAnnotation(DoWhiteList.class);

        // 获取字段值
        String keyValue = getFieldValue(whiteList.key(), jp.getArgs());
        log.info("middleware whitelist handler method：{} value：{}", method.getName(), keyValue);
        if (null == keyValue || "".equals(keyValue)) {
            return jp.proceed();
        }

        String[] split = whiteListConfig.split(",");

        // 白名单过滤
        for (String str : split) {
            if (keyValue.equals(str)) {
                return jp.proceed();
            }
        }

        // 拦截
        return returnObject(whiteList, method);


    }

    private Method getMethod(JoinPoint jp) throws Throwable {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    // 返回对象
    private Object returnObject(DoWhiteList whiteList, Method method) throws InstantiationException, IllegalAccessException {
        Class<?> returnType = method.getReturnType();
        String returnJson = whiteList.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    // 获取属性值
    private String getFieldValue(String field, Object[] args) {
        String fieldValue = null;
        for (Object arg : args) {
            try {
                if (null == fieldValue || "".equals(fieldValue)) {
                    fieldValue = BeanUtils.getProperty(arg, field);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return fieldValue;
    }


}
