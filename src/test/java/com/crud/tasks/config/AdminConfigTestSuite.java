package com.crud.tasks.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AdminConfigTestSuite {

    @Autowired
    private AdminConfig adminConfig;

    @Test
    void shouldExistingAdminAddressMail() {
        //given & when & then
        assertThat(adminConfig.getAdminMail()).isNotNull();
    }
}