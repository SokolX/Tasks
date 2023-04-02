package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message, String templatePath) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("is_friend", true);
        context.setVariable("message", message);
        context.setVariable("tasks_url", appConfig.getAppUrl());
        context.setVariable("button", appConfig.getAppRedirectText());
        context.setVariable("show_button", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("goodbye_message", appConfig.getGoodByeMessage());
        context.setVariable("company_name", appConfig.getCompanyName());
        context.setVariable("company_goal", appConfig.getCompanyGoal());
        context.setVariable("application_functionality", functionality);
        return templateEngine.process(templatePath, context);
    }
}
