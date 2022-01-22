package cn.bugstack.middleware.hystrix.config;

import cn.bugstack.middleware.hystrix.DoHystrixPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLOutput;
import java.util.Random;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
@Configuration
public class WhiteListAutoConfigure {


    @Bean
    @ConditionalOnMissingBean
    public DoHystrixPoint doHystrixPoint(){
        return new DoHystrixPoint();
    }




}
