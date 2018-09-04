package com.neo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:platform-dev.properties")
@ConfigurationProperties(prefix = "jwt.token")
@Data
public class JwtTokenProperties {

    private String secret;

    private String audience;

    private String issuer;

    private String userid;

}
