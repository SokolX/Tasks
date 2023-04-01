package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {
    @Value("${info.app.administrator.mail}")
    private String adminMail;
    @Value("${info.app.administrator.name}")
    private String adminName;
}
