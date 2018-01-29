package com.ng.member.config.property;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author niuguang
 * @date 18-1-17
 */
@Component
@Data
public class JwtProperty {
    /**
     * hmac sha512 key
     */
    private String secret = "6N0CNndW";

    /**
     * token expiration,unit: minute
     */
    private Long expiration = 600L;

    /**
     * custom http auth header name
     */
    private String header = "Authorization";

    /**
     * custom token pass in url
     */
    private String queryParam = "token";
}
