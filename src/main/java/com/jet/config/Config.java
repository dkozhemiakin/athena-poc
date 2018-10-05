package com.jet.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.AmazonAthenaClient;
import com.jet.config.properties.AthenaProperties;
import com.jet.config.properties.AwsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AwsProperties.class, AthenaProperties.class})
@Slf4j
public class Config {

    @Bean
    public AWSCredentialsProvider getCredentialsProvider() {
        return new ProfileCredentialsProvider("eis-deliverydevqa");
    }

    @Bean
    public AmazonAthena amazonSqs(AWSCredentialsProvider credentialsProvider, AwsProperties awsProperties) {
        return AmazonAthenaClient
                .builder()
                .withRegion(awsProperties.getRegion())
                .withCredentials(credentialsProvider)
                .build();
    }

}
