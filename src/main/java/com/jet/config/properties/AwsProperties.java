package com.jet.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.aws")
@Data
public class AwsProperties {

    private String region;

}
