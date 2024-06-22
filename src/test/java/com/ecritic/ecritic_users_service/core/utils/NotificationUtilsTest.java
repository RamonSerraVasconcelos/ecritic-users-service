package com.ecritic.ecritic_users_service.core.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationUtilsTest {

    @Test
    void givenTemplate_thenReplaceVariables() {
        String template = "Hello {to} my name is {name}!";

        Map<String, String> variables = new HashMap<>();
        variables.put("to", "Ramon");
        variables.put("name", "Ryan Gosling");

        String result = NotificationUtils.replaceVariables(template, variables);
        assertThat(result).isEqualTo("Hello Ramon my name is Ryan Gosling!");
    }
}