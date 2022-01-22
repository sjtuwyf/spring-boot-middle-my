package cn.bugstack.middleware.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
public class Constants {

    public static Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();
}
