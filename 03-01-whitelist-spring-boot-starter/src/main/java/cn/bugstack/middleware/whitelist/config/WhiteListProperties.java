package cn.bugstack.middleware.whitelist.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ssqswyf
 * @date 2022/1/22
 */
@ConfigurationProperties("bugstack.whitelist")
@Data
public class WhiteListProperties {

    private String users;

}
