package com.activedge.scrum.report.config.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "file", ignoreUnknownFields = false)
public class FileProperties {
    private String staticData;
    private String output;
}
