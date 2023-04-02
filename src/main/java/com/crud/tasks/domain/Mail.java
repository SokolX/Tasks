package com.crud.tasks.domain;

import lombok.Builder;

@Builder
public record Mail(String mailTo, String subject, String message, String toCc, String templatePath) {}
