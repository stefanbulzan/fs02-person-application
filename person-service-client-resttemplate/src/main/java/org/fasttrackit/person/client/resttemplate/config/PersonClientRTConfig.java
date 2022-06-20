package org.fasttrackit.person.client.resttemplate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties("person-service.rt")
@EnableConfigurationProperties(value = PersonClientRTConfig.class)
public record PersonClientRTConfig(String location) {
}
