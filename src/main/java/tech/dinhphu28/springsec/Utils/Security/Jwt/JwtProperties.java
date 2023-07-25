package tech.dinhphu28.springsec.Utils.Security.Jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    
    private String secretKey = "meocon";

    // validity in milliseconds
    private long validityInMs = 3600000;
}
