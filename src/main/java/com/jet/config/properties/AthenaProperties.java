package com.jet.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.aws.athena")
@Data
public class AthenaProperties {

    private String database;
    private String outputBucket;
    private int sleepMs;

}
