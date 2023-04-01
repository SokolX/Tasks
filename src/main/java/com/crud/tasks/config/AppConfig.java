package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppConfig {
    @Value("${info.app.name}")
    private String appName;
    @Value("${info.app.url}")
    private String appUrl;
    @Value("${info.app.redirect.text}")
    private String appRedirectText;
    @Value("${info.company.name}")
    private String companyName;
    @Value("${info.company.goal}")
    private String companyGoal;
    @Value("${info.company.goodbye_message}")
    private String goodByeMessage;
}
