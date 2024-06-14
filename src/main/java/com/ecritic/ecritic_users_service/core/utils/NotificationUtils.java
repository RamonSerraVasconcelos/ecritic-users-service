package com.ecritic.ecritic_users_service.core.utils;

import java.util.Map;

public class NotificationUtils {

    public static String replaceVariables(String template, Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            return template;
        }

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return template;
    }
}
