package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", appConfig.getAppUrl());
        context.setVariable("button", appConfig.getAppRedirectText());
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", appConfig.getGoodByeMessage());
        context.setVariable("company_name", appConfig.getCompanyName());
        context.setVariable("company_goal", appConfig.getCompanyGoal());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
